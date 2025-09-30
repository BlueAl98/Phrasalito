# 📚 Phrasalito

![Build](https://img.shields.io/github/actions/workflow/status/BlueAl98/Phrasalito/gradle.yml?branch=master)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9-blueviolet)
![Android](https://img.shields.io/badge/Android-APP-green)
![License](https://img.shields.io/github/license/BlueAl98/Phrasalito)

**Phrasalito** is an Android application written in **Kotlin** that helps users learn and practice **English phrasal verbs** with quizzes, flashcards, and spaced repetition.  
The project follows **Clean Architecture**, is organized in a **multi-module Gradle setup**, and integrates **testing** and **CI/CD pipelines**.

---

## 🔍 Why this project?

English learners often struggle with **phrasal verbs** because their meaning is not always literal.  
Phrasalito solves this by offering:

- **Interactive quizzes** to test knowledge  
- **Flashcards** for memorization and spaced repetition  
- **Detailed definitions and examples** for each verb  
- A **scalable architecture** that allows new features to be added with minimal coupling  

---

## 🏗️ Architecture Overview

The project is built around **Clean Architecture + MVVM**, enforcing separation of concerns:

```
┌───────────────────────┐
│       App (UI)        │  → Activities, Fragments, ViewModels
└───────────┬───────────┘
            │
┌───────────▼───────────┐
│    Presentation       │  → State models, adapters, user interaction
└───────────┬───────────┘
            │
┌───────────▼───────────┐
│       Domain           │  → Entities, Use Cases, Interfaces
└───────────┬───────────┘
            │
┌───────────▼───────────┐
│        Data            │  → Repository impl, data mappers, local/remote
└───────────────────────┘
```

### Layers

- **Domain**  
  - Business rules  
  - Entities (`PhrasalVerb`, `Quiz`)  
  - Use cases (`GetQuizUseCase`, `SubmitAnswerUseCase`)  

- **Data**  
  - Repository implementations  
  - Local data sources (Room, shared prefs, in-memory, etc.)  
  - Remote data sources (API clients)  
  - DTOs and mappers  

- **Presentation**  
  - ViewModels (MVVM)  
  - State handling with Kotlin `Flow` / `StateFlow`  
  - UI models and adapters  

- **App**  
  - Android entry point  
  - Navigation  
  - Dependency Injection configuration (Hilt or manual DI)  

---

## 📂 Project Structure

```
Phrasalito/
│── app/                       # Android app module (Activities, Fragments, navigation)
│   ├── src/main/java/.../ui   # UI components
│   ├── src/main/res           # Resources (layouts, drawables, strings)
│
│── phrasalito/                # Core modules
│   ├── domain/                # Business logic
│   │   ├── entities/          # Domain models (PhrasalVerb, Quiz, Answer)
│   │   ├── usecases/          # Use Cases (GetQuizUseCase, SubmitAnswerUseCase, etc.)
│   │   └── repositories/      # Repository interfaces
│   │
│   ├── data/                  # Data layer
│   │   ├── repositories/      # Repository implementations
│   │   ├── datasources/       # Local/remote sources
│   │   ├── mappers/           # DTO ↔ Domain mappers
│   │   └── models/            # Data Transfer Objects
│   │
│   └── presentation/          # Presentation layer
│       ├── viewmodels/        # ViewModels
│       ├── state/             # UI state classes
│       └── adapters/          # RecyclerView/List adapters
│
│── build.gradle.kts           # Root Gradle config
│── settings.gradle.kts        # Multi-module setup
│── .github/workflows/         # GitHub Actions (CI/CD)
│
└── docs/                      # (Optional) Documentation, diagrams, screenshots
```

---

## 🛠️ Tech Stack

- **Language:** Kotlin  
- **Architecture:** Clean Architecture, MVVM, Multi-module  
- **Async:** Coroutines, Flow, StateFlow  
- **Persistence:** Room / SharedPreferences (if used)  
- **Dependency Injection:** Hilt (or manual DI setup in `di/`)  
- **UI:** Jetpack (ViewModel, LiveData/Flow, RecyclerView, Navigation Component)  
- **Testing:**  
  - JUnit, MockK for unit tests  
  - AndroidX Test for instrumentation tests  
- **Build:** Gradle Kotlin DSL  
- **CI/CD:** GitHub Actions (build & test on push/PR)  

---

## 🚀 Getting Started

### Requirements
- Android Studio Ladybug+  
- JDK 17+  
- Gradle 8+  

### Clone & Build
```bash
git clone https://github.com/BlueAl98/Phrasalito.git
cd Phrasalito
./gradlew clean build
```

### Run
Open the project in Android Studio → choose emulator/physical device → press ▶️.

---

## ✅ Testing

The project includes **unit and instrumentation tests**:

```bash
./gradlew test
./gradlew connectedAndroidTest
```

Test coverage includes:

- Domain use cases  
- Repository contracts  
- ViewModel logic  

---

## ⚙️ Continuous Integration

Workflows in `.github/workflows/` handle:

- ✅ Build on every push/PR  
- ✅ Run unit + instrumentation tests  
- ✅ Verify Gradle + Kotlin DSL integrity  

---

## 🔮 Future Improvements

- Add spaced repetition algorithm (e.g. SM-2)  
- Cloud sync for user progress  
- More quizzes and phrasal verb categories  
- Dark theme and accessibility improvements  
- i18n for Spanish/other learners  

---

## 📸 Screenshots (to add)

You can add app previews here once available:
//pending

```
![Home Screen](docs/screenshots/home.png)
![Quiz](docs/screenshots/quiz.png)
![Flashcards](docs/screenshots/flashcards.png)
```

