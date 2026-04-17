# Architecture

## Overview
- Architecture: MVVM
- Layers: Data + Presentation (No separate Domain layer)
- Pattern: Repository → ViewModel → Compose UI
- Language: Kotlin
- UI: Jetpack Compose
- DI: Koin

---

## Data Flow
- UI observes StateFlow from ViewModel
- ViewModel calls Repository (suspend operator fun invoke)
- Repository calls API
- Result is exposed via StateFlow

Flow:
UI → ViewModel → Repository → API → Repository → ViewModel → UI

---

## Repository Rules
- Repository must be simple
- Use `operator fun invoke(...)`
- No business logic inside repository
- Only call API and return result

Example:
```kotlin
class FetchSettingRepo(
    private val configApi: ConfigApi,
    private val configFactory: ConfigFactory,
) {
    val result = MutableStateFlow<ISetting?>(null)

    suspend operator fun invoke() {
        result.emit(configFactory.createSetting(configApi.settings().await()))
    }

}

## UI Model + Factory Rule

> ❗ This rule is mandatory for all new features.

### Responsibility
Any developer who creates a new feature must:
1. Define UI model as interface in `domain.model.ui`
2. Create a Factory to map DTO → UI model
3. Ensure Repository returns ONLY UI model (interface)

---

### Required Flow

API (DTO) → Factory → UI Model (interface) → ViewModel → UI

---

### Implementation Steps

#### Step 1 – Define UI model
```kotlin
interface ISetting {
    val address: String
    val phone: String
    val phoneDisplay: String
    val email: String
}