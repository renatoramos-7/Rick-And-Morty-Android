# Rick and Morty Android

**Português (Brasil)** | [English](./README.md)

Aplicativo Android modularizado em Kotlin que consome a API de Rick and Morty e demonstra uma arquitetura orientada a produção com MVVM, repositórios, injeção de dependência, paginação e cache local.

Este projeto foi pensado como uma vitrine técnica para portfólio, com foco em organização de código, escalabilidade e manutenção.

## Visão geral

O app entrega uma experiência de navegação simples pelo universo de Rick and Morty usando módulos de feature e uma camada compartilhada de core.

### Principais destaques

- Projeto Android modularizado em múltiplos módulos
- Camada de apresentação em MVVM com separação clara de responsabilidades
- Repository Pattern coordenando fontes de dados remotas e locais
- Fluxos assíncronos com RxJava
- Integração com Paging para listas grandes da API
- Injeção de dependência com Dagger 2 entre os módulos
- Retrofit + Moshi para rede e serialização
- Cache local com fallback usando PaperDB
- Integração com View Binding e Navigation Component
- Toolchain em Java 17 e setup moderno com Android Gradle

## Screenshots

<p align="center">
  <img src="./design/Screenshot1.jpg" width="220" alt="Tela de personagens" />
  <img src="./design/Screenshot2.jpg" width="220" alt="Tela de episódios" />
  <img src="./design/Screenshot3.jpg" width="220" alt="Fluxo de listas e navegação" />
</p>

## Arquitetura

O código foi organizado em módulos de feature e uma camada `core` reutilizável. A navegação começa no módulo `app`, o módulo `home` coordena o shell principal e a bottom navigation, e cada feature é responsável pelo seu próprio fluxo de apresentação.

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

    subgraph Camada Core
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

### Fluxo de dados

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

### Estrutura dos módulos

- `app`: ponto de entrada da aplicação, merge de manifestos e configuração Android de topo.
- `core`: domínio compartilhado, camada de dados, classes base de UI, utilitários, DTOs, serviços Retrofit, repositórios e providers de cache.
- `di`: configuração do Dagger, grafo da aplicação, módulos de injeção e fábrica de ViewModels.
- `home`: activity principal, toolbar, bottom navigation e integração do grafo de navegação.
- `characters`: UI da feature de personagens, datasource de paginação, adapter e ViewModel.
- `episodes`: UI da feature de episódios, datasource de paginação, adapter e ViewModel.
- `buildSrc`: centralização de dependências e configurações Android.

## Stack técnica

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
- JUnit e Mockito

## Por que este projeto chama atenção

Este repositório foi estruturado para demonstrar como um app Android pode evoluir além de uma base monolítica. O foco não é apenas consumir uma API, mas também evidenciar:

- limites arquiteturais reutilizáveis
- isolamento de features
- organização do grafo de dependências
- estratégia de fallback com cache local
- legibilidade de código para avaliação técnica

## Como executar

### Requisitos

- Android Studio com suporte a Gradle
- JDK 17
- Android SDK configurado localmente

### Rodando localmente

1. Clone o repositório.
2. Abra a raiz do projeto no Android Studio.
3. Faça o sync das dependências Gradle.
4. Execute a configuração `app` em um emulador ou dispositivo.

## Observações

- O app atualmente está focado nos fluxos de `characters` e `episodes`.
- A configuração do projeto está centralizada em `buildSrc`.
- A separação em módulos facilita a expansão futura da aplicação.

## Autor

**Renato Ramos**

## Licença

Este projeto está licenciado sob a MIT License. Veja [LICENSE](./LICENSE) para mais detalhes.
