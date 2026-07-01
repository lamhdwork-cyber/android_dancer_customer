# Data Access

## Network

### API Interface

Define API methods using `ApiAsync<T>` (never `Call<T>` or `suspend`):

```kotlin
// data/remote/api/NotificationApi.kt
interface NotificationApi {

    @GET("notifications")
    fun fetchByPage(
        @Query("page") page: Int,
        @Query("limit") perPage: Int = AppConfig.PER_PAGE
    ): ApiAsync<ApiResponsePaging<NotificationDTO>>

    @PATCH("notifications/read-all")
    fun readAll(): ApiAsync<Any>

    @PATCH("notifications/read/{id}")
    fun readById(@Path("id") notificationId: String): ApiAsync<Any>
}
```

Register as singleton in `dataModule`:
```kotlin
single { provideApi<NotificationApi>(get()) }
```

**Response wrappers:**
- `ApiAsync<ApiResponsePaging<T>>` — for paginated list responses; access items via `.awaitNullable()?.data`
- `ApiAsync<T>` — for single object responses
- `ApiAsync<Any>` — for responses where the body is not needed

### Calling API in Repository

Use `.await()` or `.awaitNullable()` — never call the API outside a repository:

```kotlin
// .await() — response must be non-null; throws ServerResponseNullException if null
val result = userApi.signIn(body).await()

// .awaitNullable() — response can be null; returns null instead of throwing
val data = notificationApi.fetchByPage(page).awaitNullable()?.data
```

### Error Handling

All exceptions are caught automatically by `launch(loading, error)` in the ViewModel. Do not try-catch inside repositories.

HTTP status → exception mapping:

| Status | Exception |
|---|---|
| 401 | `ExpiredTokenException` |
| 429 | `ParameterInvalidException` |
| 400–499 | `ApiRequestException` |
| 500+ | `InternalServerException` |
| No connection | `ConnectException` |

### Interceptors

Added automatically to every request — no manual setup per API call:

- **`TokenInterceptor`** — adds `Authorization: Bearer {token}`; skip with `@NoTokenRequired`
- **`LanguageInterceptor`** — adds `content-language` header; skip with `@NoLanguageRequired`

### Timeouts

Connect / Read / Write: **60s** each.

---

## Local Storage (DataStore)

### Setup

Each LocalSource creates its own `GsonDataStoreCaching` instance:

```kotlin
class UserLocalSource(context: Context, ...) {
    private val caching = GsonDataStoreCaching(context)
}
```

All LocalSources share the same underlying DataStore (process-level singleton).

### Property Types

Declare as class-level properties — they are handles, not values:

```kotlin
val apiToken    = caching.string("token:api", "")
val chatRoomId  = caching.int("chat:room:id", 0)
val isWelcome   = caching.boolean("app:language:welcome", true)
private val user = caching.reference<UserDTO>(UserDTO::class.java.name)
```

| Method | Type | Default |
|---|---|---|
| `caching.string(key, def)` | `PreferenceProperty<String>` | `""` |
| `caching.int(key, def)` | `PreferenceProperty<Int>` | `0` |
| `caching.long(key, def)` | `PreferenceProperty<Long>` | `0` |
| `caching.boolean(key, def)` | `PreferenceProperty<Boolean>` | `false` |
| `caching.reference<T>(key)` | `PreferenceProperty<T?>` | `null` |

### PreferenceProperty<T>

```kotlin
interface PreferenceProperty<T> {
    suspend fun get(): T       // read — must be in coroutine
    suspend fun set(value: T)  // write — must be in coroutine
    fun asFlow(): Flow<T>      // observe reactively
}
```

Usage:

```kotlin
// Standard — use get()/set() for normal read/write
suspend fun isLogin() = user.get() != null
suspend fun getToken(): String = apiToken.get()

suspend fun logout() {
    user.set(null)
    apiToken.set("")
}

// Only use asFlow() when the caller needs to observe changes reactively (live)
fun getUserLive(): Flow<UserDTO?> = user.asFlow()
```

### Key Naming

Colon-separated namespacing:

```
"token:api"
"token:refresh"
"token:push"
"auth:account"
"auth:password"
"chat:room:id"
"app:language:welcome"
```

### Koin Registration

```kotlin
single { UserLocalSource(get(), get(), get()) }
single { LanguageLocalSource(get()) }
```
