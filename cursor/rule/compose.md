# Widgets Rules (STRICT)

## Overview
- Project has 2 widget layers:

1. Core Widgets:
   presentation/widgets/Widgets
2. AI Generated Widgets:
   presentation/widgets/WidgetsAI

---

## Priority Rules (VERY IMPORTANT)

1. ALWAYS check existing Widgets first:
   presentation/widgets/Widgets

2. If component EXISTS:
   → MUST reuse
   → DO NOT rewrite
   → DO NOT duplicate

3. If component DOES NOT exist:
   → CREATE new in:
     presentation/widgets/WidgetsAI

---

## Strict Restrictions

- ❌ DO NOT modify any file in:
  presentation/widgets/Widgets

- ❌ DO NOT duplicate existing widget

- ❌ DO NOT create widget outside WidgetsAI

---

## Usage Rules

- Import from Widgets if available
- Otherwise import from WidgetsAI

---

## Creation Rules (WidgetsAI)

When creating new component:

- Location:
  presentation/widgets/WidgetsAI

- Naming:
  AppXxx (same style as existing Widgets)

- Must follow:
  - Compose rules
  - Project style
  - Stateless design

---

## Example Flow

Case 1: Need Text Input

→ Check:
presentation/widgets/Widgets/AppInputText

→ Exists:
→ Use AppInputText ✅

---

Case 2: Need Date Picker

→ Check Widgets → NOT FOUND

→ Create:
presentation/widgets/WidgetsAI/AppDatePicker.kt ✅

---

## Component Design Rules

- Stateless Composable
- Receive data via params
- No business logic inside

---

## Forbidden

- ❌ Modify Widgets/*
- ❌ Copy Widgets to new file
- ❌ Create duplicate component
- ❌ Use default Compose if Widget exists


## ScopeProvider Usage

> ❗ This rule is mandatory for all new screens.

### Rule
Any developer who creates a new screen must:
1. Add a corresponding scope in `Scopes`
2. Wrap the screen with `ScopeProvider` using that scope

---

### Example (Introduce Screen)

#### Step 1 – Add scope
```kotlin
enum class Scopes : IChars {
    App,
    Account,
    Introduce, // new scope
    ...
}

## UI-only Screens

- If the screen contains only UI and no business logic:
  - Do NOT create ViewModel
  - Do NOT create Repository
  - Do NOT create UseCase

- Keep the composable stateless
- Pass data via parameters
- Handle only UI state (e.g. pagerState, remember)

- Use simple event callbacks instead of ViewModel

## Color
- Use colors only from presentation/theme/Color.kt.
- If a color is missing, add it to Color.kt with proper semantic naming format.
- Never use hardcoded colors in Composables (e.g., Color(0xFF...), Color.Red, etc.)
- Color names must follow the format: <ColorName><HexValue>
- `ColorName` is a plain color family already used in `Color.kt` (Gray, Blue, Pink, Dark, Red, …), not marketing or Tailwind token names (avoid Slate, PrimaryHex, OverlayHex for tint keys—use Pink… / Gray… / Dark… as appropriate).
- Hex suffix must match the literal `Color(0x…)` value (opaque: 6 RGB digits after `0xFF`; translucent: include alpha in the suffix so the name maps one-to-one to the stored ARGB).
- Common colors (e.g., White, Black) must not include hex in the name
example:  val Blue757682: Color = Color(0xFF757682)
example:  val GrayF1F5F9: Color = Color(0xFFF1F5F9)
example:  val Pink26F425F4: Color = Color(0x26F425F4)
