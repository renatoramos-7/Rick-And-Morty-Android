# Rick and Morty Android

[Português (Brasil)](./README.pt-BR.md) | **English**

A modular Android application built with Kotlin that consumes the Rick and Morty API and showcases a production-oriented architecture with MVVM, repository abstraction, dependency injection, pagination, and local caching.

This project was designed as a clean portfolio sample focused on code organization, maintainability, and scalability.

## Overview

The app delivers a simple browsing experience for Rick and Morty content through feature modules and a shared core layer.

### Main highlights

- Modularized multi-module Android project
- MVVM presentation layer with clear separation of responsibilities
- Repository pattern coordinating remote and local data sources
- RxJava-based asynchronous flows
- Paging integration for large API lists
- Dagger 2 dependency injection across modules
- Retrofit + Moshi for networking and serialization
- PaperDB-based local fallback cache
- View Binding and Navigation Component integration
- Java 17 toolchain and modern Android Gradle setup

## Screenshots

<p align="center">
  <img src="./design/Screenshot1.jpg" width="220" alt="Characters screen" />
  <img src="./design/Screenshot2.jpg" width="220" alt="Episodes screen" />
  <img src="./design/Screenshot3.jpg" width="220" alt="List details and navigation flow" />
</p>

## Architecture

The codebase is organized around feature modules and a reusable core layer. Navigation starts in the `app` module, the `home` module coordinates the main shell and bottom navigation, and each feature owns its presentation flow.

```mermaid
flowchart TD
    A[app] --> B[home]
    A --> C[di]
    A --> D[core]

    B --> E[characters]
    B --> F[episodes]
    B --> D

    C --> B
    C --> D
    C --> E
    C --> F

    E --> D
    F --> D

    subgraph Core Layer
        D1[Base UI classes]
        D2[Use cases]
        D3[Repositories]
        D4[Retrofit APIs]
        D5[PaperDB providers]
        D6[DTOs and ViewObjects]
    end

    D --> D1
    D --> D2
    D --> D3
    D --> D4
    D --> D5
    D --> D6
```

### Data flow

```mermaid
flowchart LR
    UI[Fragment / Activity] --> VM[ViewModel]
    VM --> DS[Paging DataSource]
    DS --> UC[UseCase]
    UC --> RP[Repository]
    RP --> API[Retrofit API]
    RP --> CACHE[PaperDB Cache]
    API --> RP
    CACHE --> RP
    RP --> VM
    VM --> UI
```

### Module structure

- `app`: Application entry point, manifest merge, and top-level Android configuration.
- `core`: Shared domain, data, UI base classes, utilities, DTOs, Retrofit services, repositories, and cache providers.
- `di`: Dagger setup, application graph, modules, and ViewModel factory wiring.
- `home`: Main host activity, toolbar, bottom navigation, and navigation graph integration.
- `characters`: Characters feature UI, paging datasource, adapter, and ViewModel.
- `episodes`: Episodes feature UI, paging datasource, adapter, and ViewModel.
- `buildSrc`: Centralized dependency and Android configuration management.

## Tech stack

- Kotlin
- Android View Binding
- MVVM
- Repository Pattern
- Dagger 2
- RxJava 2 / RxAndroid 2
- Retrofit 2
- Moshi
- OkHttp
- AndroidX Navigation Component
- AndroidX Paging
- Glide
- PaperDB
- JUnit and Mockito

## Why this project stands out

This repository is intentionally structured to demonstrate how an Android app can scale beyond a single-module codebase. The focus is not only on fetching API data, but on presenting:

- reusable architecture boundaries
- feature isolation
- dependency graph organization
- fallback caching strategy
- recruiter-friendly code readability

## Getting started

### Requirements

- Android Studio with Gradle support
- JDK 17
- Android SDK configured locally

### Run locally

1. Clone the repository.
2. Open the project root in Android Studio.
3. Sync Gradle dependencies.
4. Run the `app` configuration on an emulator or device.

## Notes

- The app currently focuses on the `characters` and `episodes` flows.
- Project configuration is centralized in `buildSrc`.
- The repository includes modular boundaries that make future expansion straightforward.

## Author

**Renato Ramos**

## License

This project is licensed under the MIT License. See [LICENSE](./LICENSE) for details.
