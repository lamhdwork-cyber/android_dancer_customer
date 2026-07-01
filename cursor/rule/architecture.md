# Architecture

## Overview

- Pattern: MVVM + Clean Architecture
- Language: Kotlin
- UI: Jetpack Compose
- DI: Koin
- Async: Coroutines + Flow / StateFlow
- Persistence: DataStore (via support-persistent)
- Networking: Retrofit + custom `Async<T>` CallAdapter (via support-core)

---

## Layers

```
domain/       Contracts only — interfaces, usecases, no implementation
data/         Implementation — API, local storage, repo impls, factories
presentation/ UI — ViewModels, Screens, Widgets
```

Domain has zero dependencies on data or presentation.  
Data depends on domain (implements interfaces).  
Presentation depends on domain (uses interfaces via DI).

---

## Standard Feature Flow (Reference: Notification)

The Notification feature is the canonical implementation. All new features must follow the same structure.

```
NotificationDTO          data/model/response/       API response object
NotificationFactory      data/factory/              DTO → INotification
NotificationApi          data/remote/api/           Retrofit interface
NotificationRepoImpl     data/repo/                 implements NotificationRepo
NotificationRepo         domain/repo/               interface (contract)
INotification            domain/model/notification/ interface (domain model)
NotificationUseCase      domain/usecase/            depends on NotificationRepo interface
NotificationVM           presentation/viewmodel/    depends on NotificationUseCase
NotificationScreen       presentation/screen/       collects StateFlow from NotificationVM
```

---

## Domain Layer Rules

### Model — interface only

```kotlin
// domain/model/notification/INotification.kt
interface INotification {
    val id: String get() = ""
    val title: String get() = ""
    val hasUnRead: Boolean get() = true
    val datetime: String get() = ""
    val contents: String get() = ""
}
```

- `domain/model/` contains **interfaces only** — no data classes, no forms, no enums with logic
- Properties have default values so anonymous objects only override what they need

### Repository — interface only

```kotlin
// domain/repo/NotificationRepo.kt
interface NotificationRepo {
    suspend fun fetchByPage(page: Int): List<INotification>
    suspend fun readById(id: String)
    suspend fun readAll()
}
```

- `domain/repo/` contains **interfaces only** — no implementation
- All methods are `suspend`
- Returns domain model interfaces — never DTOs

### UseCase

```kotlin
// domain/usecase/NotificationUseCase.kt
class NotificationUseCase(private val notificationRepo: NotificationRepo) {
    suspend operator fun invoke(page: Int): List<INotification> {
        return withIO { notificationRepo.fetchByPage(page) }
    }
    suspend fun readAll() = withIO { notificationRepo.readAll() }
    suspend fun readById(id: String) = withIO { notificationRepo.readById(id) }
}
```

- Depends on repo **interface** only, never on `*RepoImpl`
- `operator fun invoke(...)` for primary action; named methods for secondary actions
- Wraps calls with `withIO { }` for IO thread switching — `withIO` is from `support-core`, equivalent to `withContext(Dispatchers.IO)`

---

## Data Layer Rules

### Factory

```kotlin
// data/factory/NotificationFactory.kt
class NotificationFactory(private val textFormatter: TextFormatter) {
    fun createList(its: List<NotificationDTO>?): List<INotification> {
        return its?.map(::create) ?: listOf()
    }
    private fun create(it: NotificationDTO): INotification {
        return object : INotification {
            override val id: String get() = it.id.safe()
            override val title: String get() = it.title.safe()
            override val hasUnRead: Boolean get() = it.isRead == false
            override val datetime: String get() = textFormatter.formatNotificationDateTime(it.createdAt)
        }
    }
}
```

- Maps DTOs to domain interfaces using anonymous objects
- Uses `.safe()` extension for null-safe field access — returns empty string/default when null (`it.id.safe()` = `it.id ?: ""`)
- Registered as `single {}` in Koin (stateless, reused everywhere)

### Repository Implementation

```kotlin
// data/repo/NotificationRepoImpl.kt
class NotificationRepoImpl(
    private val notificationApi: NotificationApi,
    private val notificationFactory: NotificationFactory
) : NotificationRepo {
    override suspend fun fetchByPage(page: Int): List<INotification> {
        return notificationFactory.createList(
            notificationApi.fetchByPage(page).awaitNullable()?.data
        )
    }
    override suspend fun readAll() { notificationApi.readAll().awaitNullable() }
    override suspend fun readById(id: String) { notificationApi.readById(id).awaitNullable() }
}
```

- Named with `Impl` suffix, lives in `data/repo/`
- Depends on: API interface + Factory
- Returns domain model interfaces, never DTOs
- Koin: `single<NotificationRepo> { NotificationRepoImpl(get(), get()) }`

---

## Presentation Layer Rules

### ViewModel

```kotlin
// presentation/viewmodel/NotificationVM.kt
class NotificationVM(
    private val notificationUseCase: NotificationUseCase
) : AppViewModel() {
    private val _items = MutableStateFlow<List<INotification>>(emptyList())
    val items: StateFlow<List<INotification>> = _items

    fun onFetch() = launch(loading, error) {
        _items.value = notificationUseCase(page)
    }
    fun readAll() = launch(loading, error) {
        notificationUseCase.readAll()
    }
}
```

- Extends `AppViewModel` (provides `userLive`, `loading`, `error`, `launch`)
- Depends on UseCase only — **never on Repo directly**
- State held in `StateFlow`; UI observes via `collectAsState()`
- All coroutines via `launch(loading, error) { }` — auto-handles loading and error events
- **Repos must not be defined inside ViewModel files** — belong in `data/repo/`

### AppViewModel Base

```
BaseViewModel (support-ui)
  └── AppViewModel (app)
        ├── userLive: StateFlow<IUser?>      reactive current user (user + language combined)
        ├── getCurrentUser(block)            one-shot callback
        ├── loading: LoadingFlow
        ├── error: ErrorFlow
        └── launch(loading, error) { ... }
```

---

## DI Rules (Koin)

```kotlin
// Domain repo interface → data impl binding
single<NotificationRepo> { NotificationRepoImpl(get(), get()) }

// Factories — singleton, stateless
single { NotificationFactory(get()) }

// UseCases — factory (fresh per injection)
factory { NotificationUseCase(get()) }

// ViewModels
viewModelOf(::NotificationVM)
```

| Registration | Used for |
|---|---|
| `single<Interface> { Impl(...) }` | Domain repo → implementation binding |
| `single { }` | Factories, LocalSources, APIs, infrastructure |
| `factory { }` | UseCases, operational repos, short-lived objects |
| `viewModelOf(::XxxVM)` | ViewModels with auto-wired constructor params |

### Which module to register in

| What | Module |
|---|---|
| Repo impls, LocalSources, APIs, interceptors, operational repos | `dataModule` |
| UseCases, Factories, Formatters | `domainModule` |
| ViewModels, AppNavigator, AppKeyboard, AppNotifications | `presentationModule` |
| Conversation-specific classes | `conversation` module |

```kotlin
// dataModule — repo impl, operational repo, local source
single<NotificationRepo> { NotificationRepoImpl(get(), get()) }
factory { FetchAllBannerRepo(get(), get()) }
single { UserLocalSource(get(), get(), get()) }

// domainModule — usecase, factory
factory { NotificationUseCase(get()) }
single { NotificationFactory(get()) }

// presentationModule — viewmodel
viewModelOf(::NotificationVM)
```

---

## Common Patterns

### Pagination

Standard pattern used across list screens (reference: `NotificationVM`):

```kotlin
class NotificationVM(private val useCase: NotificationUseCase) : AppViewModel() {
    private val _items = MutableStateFlow<List<INotification>>(emptyList())
    val items: StateFlow<List<INotification>> = _items

    val customLoading: LoadingEvent = LoadingFlow()   // loading for page 2+
    val isRefreshLoading: LoadingEvent = LoadingFlow() // loading for page 1

    private var page = 1
    private var hasMoreData = true

    fun onFetch() {
        if (isRefreshLoading.isLoading().value || customLoading.isLoading().value
            || !hasMoreData || userLive.value == null) return
        launch(if (page == 1) isRefreshLoading else customLoading, error) {
            val rs = useCase(page)
            if (rs.isEmpty()) hasMoreData = false
            else {
                if (rs.size < AppConfig.PER_PAGE) hasMoreData = false
                _items.value = _items.value + rs.filterNot { new -> _items.value.any { it.id == new.id } }
                page++
            }
        }
    }

    fun onRefresh() {
        page = 1
        hasMoreData = true
        _items.value = emptyList()
        onFetch()
    }
}
```

- Two separate `LoadingEvent`s: one for first page (pull-to-refresh), one for next pages (load more)
- Guard at top of `onFetch()` prevents duplicate calls
- Dedup items by id when appending to avoid duplicates on refresh overlap

---

### Navigation

`AppNavigator` is a Koin singleton injected into screens via `koinInject()`. Never use `NavController` directly in screens.

```kotlin
// In screen
val appNavigator: AppNavigator = koinInject()

// Go back
appNavigator.back()

// Navigate to a screen
appNavigator.navigateDetailCase(bookingId)
appNavigator.navigateDetailDancer(dancerId)
```

Routes are type-safe `@Serializable` data classes/objects in `AppRoutes.kt`:

```kotlin
@Serializable data class DetailCase(val bookingId: String)
@Serializable data object ChangePassword
```

To add a new screen:
1. Add a `@Serializable` route in `AppRoutes.kt`
2. Add a `navigateXxx(...)` function in `AppNavigator`
3. Register the route in `NavigationProvider.kt`

---

### Loading & Error in UI

`AppViewModel` exposes `loading` and `error` events. Screens observe them via the base Activity/`WindowStatusOwner` — no manual wiring needed per screen.

For screens needing custom loading behavior (e.g., separate refresh vs load-more):

```kotlin
// VM — declare extra LoadingEvent
val customLoading: LoadingEvent = LoadingFlow()

// Screen — observe custom loading
val isLoading by viewModel.customLoading.isLoading().collectAsState()
```

---

### Accessing Current User

```kotlin
// Reactive — recompose when user or language changes
val user by viewModel.userLive.collectAsState()

// One-shot callback — use when you only need the value once
viewModel.getCurrentUser { userDto ->
    // userDto: UserDTO?
}
```

`userLive` emits `IUser?` (domain model). `getCurrentUser` gives `UserDTO?` (raw data).  
Use `userLive` for UI display, `getCurrentUser` for logic that needs raw data.

---

## Rules Summary

| Rule | |
|---|---|
| `domain/model/` | Interfaces only |
| `domain/repo/` | Interfaces only |
| `data/repo/` | `*RepoImpl` implementing domain interfaces |
| VM depends on | UseCase — never Repo directly |
| UseCase depends on | Repo interface — never `*RepoImpl` |
| Repos in `presentation/` | ❌ Forbidden |
