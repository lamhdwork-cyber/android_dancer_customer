# Project Structure & UI Conventions

## Module Structure

```
app/                  Main application — all business logic
support-core/         Networking (Retrofit, Async<T>, interceptors), coroutine helpers, events
support-persistent/   DataStore + SharedPreferences caching abstractions
support-ui/           Base classes (BaseViewModel, BaseComponentAct), Compose extensions, widgets
```

---

## App Package Structure

```
com.kantek.dancer.booking/

data/
  local/                    DataStore-backed local sources
    UserLocalSource.kt
    LanguageLocalSource.kt
  remote/
    api/                    Retrofit API interfaces (UserApi, DancerApi, NotificationApi, ...)
    socket/                 WebSocket clients (ChatSocketClient)
  model/
    response/               DTOs from API (UserDTO, NotificationDTO, ...)
    form/                   Input/form data classes
    entity/                 Local entity classes
    firebase/               Firebase-specific models
  factory/                  DTO → domain model mappers (*Factory)
  repo/                     Concrete repository implementations (*RepoImpl)
    conversation/           Chat-specific repos
  formatter/                Text and time formatting utilities
  extension/                Data-layer Kotlin extensions
  event/                    App-level event classes

domain/
  model/                    Interface-based domain models — interfaces only
    notification/           INotification
    user/                   IUser, IAccount
    booking/                IBooking, IBookingDetail, BookingActionsBar
    conversation/           IMessage, IConversation
    config/                 ISetting
    introduce/              IIntroduce
    media/                  IMedia
    review/                 IReview
    search/                 ISearch
    faqs/                   IFAQsThread
  repo/                     Repository contracts — interfaces only
  usecase/                  Business logic (NotificationUseCase, FetchDancerDetailCase, ...)
  provider/                 CurrentUserRoleProvider interface

presentation/
  viewmodel/                AppViewModel base + screen-specific VMs (*VM)
  screen/                   Composable screens, one folder per feature
    account/
    auth/
      forgot/
      otp/
    booking/
    browser/
    club/
    conversation/
    dancer/
    faqs/
    home/
    introduce/
    language/
    media/
    notification/
    review/
  navigation/               NavHost + route definitions (AppRoutes.kt, AppNavigator.kt)
  widget/                   Reusable Compose components, grouped by feature
    CommonWidgets.kt
    BookingWidgets.kt
    AccountWidgets.kt
    NavigationWidgets.kt
    TopBarWidgets.kt
    ...
  theme/                    Color.kt, typography, AppTheme
  helper/                   Login helpers, misc utilities
  provider/                 Compose-layer providers
  extensions/               Presentation-layer Kotlin extensions
  firebase/                 Firebase messaging setup
  model/                    Presentation-only models

app/                        Application class, AppModule (Koin), AppConfig, AppScopes
```

---

## Screen Folder Convention

Each screen lives in its own folder under `presentation/screen/`:

```
presentation/screen/
  notification/
    NotificationScreen.kt
    NotificationVM.kt        (if scoped to this screen only)
```

- Folder name: lowercase, no prefix (`notification`, not `NotificationScreen`)
- Screen file: `<Feature>Screen.kt`
- ViewModel: `<Feature>VM.kt` — in the same folder if screen-specific, in `presentation/viewmodel/` if shared across screens

---

## Widget Usage

Priority order when you need a UI component:

1. **Check `support-ui` first** — `AppButton`, `AppInputText`, `AppWebView`, `AppNextButton`, etc.
2. **Check `presentation/widget/`** — `CommonWidgets.kt`, `BookingWidgets.kt`, `AccountWidgets.kt`, etc.
3. **If not found in either** — add a new function to the appropriate `*Widgets.kt` file, or create `<Feature>Widgets.kt` if no matching file exists

Rules for new widget functions:
- Stateless: receive data via parameters, no business logic inside
- Name by feature context, consistent with existing names in the file

---

## Injecting in Screens

```kotlin
// ViewModel — default parameter pattern
fun NotificationScreen(viewModel: NotificationVM = koinViewModel()) { ... }

// Non-VM dependency (navigator, provider, etc.)
val appNavigator: AppNavigator = koinInject()
val roleProvider: CurrentUserRoleProvider = koinInject()
```

---

## ScopeProvider

Every screen must be wrapped with `ScopeProvider`:

```kotlin
ScopeProvider {
    // screen content
}
```

- Default (no param) is correct for most screens — each gets an auto-generated scope
- Pass explicit scope only when multiple screens share the same scope:

```kotlin
ScopeProvider(scopeName = AppScopes.Account) { ... }
```

---

## UI-only Screens

If a screen has no business logic:
- Do NOT create a ViewModel or Repository
- Keep the composable stateless — pass data via parameters
- Handle only local UI state (`remember`, `pagerState`, etc.)

---

## Color

- Use colors from `presentation/theme/Color.kt` only
- Never use `Color.White`, `Color.Black`, `Color.Gray`, `Color(0xFF...)` directly in Composables — always import from `Color.kt`

Naming convention in `Color.kt`:
```kotlin
val Blue757682: Color = Color(0xFF757682)    // <ColorName><HexValue>
val GrayF1F5F9: Color = Color(0xFFF1F5F9)
val Pink26F425F4: Color = Color(0x26F425F4)  // alpha prefix in name when translucent
val White: Color = Color(0xFFFFFFFF)          // common colors omit hex
val Black: Color = Color(0xFF000000)
```
