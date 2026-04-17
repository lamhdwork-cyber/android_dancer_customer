# Project Structure Rules

## Screen Location

- All screens must be placed in:
  presentation/screen/

- Each screen must have its own folder

---

## Folder Naming

- Folder name = lowercase, no prefix

Example:
IntroduceScreen → introduce

---


Example:
IntroduceScreen.kt

---

## Full Example

presentation/
└── screen/
    └── introduce/
        ├── AppIntroduceScreen.kt
        └── IntroduceVM.kt

---

## ViewModel Naming

- Format: <FeatureName>VM

Example:
IntroduceVM