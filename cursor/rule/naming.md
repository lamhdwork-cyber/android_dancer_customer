# Naming Conventions

## Files

| Type | Pattern | Example |
|---|---|---|
| Screen | `XxxScreen.kt` | `ContactUsScreen.kt` |
| ViewModel | `XxxVM.kt` | `ContactUsVM.kt` |
| Repository interface | `XxxRepo.kt` | `NotificationRepo.kt` |
| Repository implementation | `XxxRepoImpl.kt` | `NotificationRepoImpl.kt` |
| Factory | `XxxFactory.kt` | `NotificationFactory.kt` |
| UseCase | `XxxCase.kt` | `FetchDancerDetailCase.kt` |
| API interface | `XxxApi.kt` | `NotificationApi.kt` |
| DTO | `XxxDTO.kt` | `NotificationDTO.kt` |
| Widget file | `XxxWidgets.kt` | `BookingWidgets.kt` |
| Form | `XxxForm.kt` or class inside model file | `BookingForm`, `SignInForm` |

---

## Classes & Interfaces

| Type | Pattern | Example |
|---|---|---|
| ViewModel | `XxxVM` | `ContactUsVM` |
| Repository interface | `XxxRepo` | `NotificationRepo` |
| Repository implementation | `XxxRepoImpl` | `NotificationRepoImpl` |
| Factory | `XxxFactory` | `NotificationFactory` |
| UseCase | `XxxCase` | `FetchDancerDetailCase` |
| API interface | `XxxApi` | `NotificationApi` |
| DTO | `XxxDTO` | `NotificationDTO` |
| Domain model interface | `IXxx` | `INotification`, `IUser`, `ISetting` |
| Form / input model | `XxxForm` | `SignInForm`, `BookingForm` |

---

## Repository Naming

Two patterns coexist depending on complexity:

**Interface-backed repo** (for features with domain contracts):
```kotlin
// domain/repo/
interface NotificationRepo { ... }

// data/repo/
class NotificationRepoImpl(...) : NotificationRepo { ... }
```

**Single-purpose operational repo** (no domain interface needed):
```kotlin
// data/repo/
class FetchAllBannerRepo(...) {
    suspend operator fun invoke(): List<IBanner> { ... }
}
```

Use `XxxRepoImpl` only when a domain interface (`XxxRepo`) exists.

---

## UseCase Naming

Prefer `XxxCase` (shorter, consistent with majority of codebase):
```
FetchClubCase
FetchDancerDetailCase
FetchRoomsByClubCase
```

`XxxUseCase` is acceptable for broader orchestration:
```
NotificationUseCase   ← multiple actions (fetch, readAll, readById)
```

---

## Function Naming in ViewModels

| Pattern | When to use | Example |
|---|---|---|
| `onFetch()` | Trigger a paginated or list fetch | `onFetch()`, `onRefresh()` |
| `onXxx()` | UI event handler / user action | `onChangeLanguage()`, `onChangeUser()` |
| `fetchXxx()` | Direct fetch, typically called in init | `fetchIntroduce()` |
| `updateXxx()` | Update a form field value | `updateAccount()`, `updatePassword()` |
| `setXxx()` | Set a single state value | `setLanguage()`, `setData()` |
| `saveXxx()` | Persist/submit a value | `saveLanguage()` |
| `submit()` | Generic form submission | `submit()` |
| action name | Domain-specific action | `signIn()`, `logout()`, `readAll()` |

---

## State Management

The project does **not** use separate `XxxState` classes.  
State is managed directly via `StateFlow` properties in the ViewModel:

```kotlin
private val _items = MutableStateFlow<List<INotification>>(emptyList())
val items: StateFlow<List<INotification>> = _items
```

`XxxForm` classes are used only for grouping input/form data, not UI state.
