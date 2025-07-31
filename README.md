Projeto StarWars

Este repositório contém o código-fonte de uma aplicação Android desenvolvida em Kotlin, focada em explorar e interagir com dados do universo Star Wars. O projeto demonstra uma arquitetura moderna, utilizando as melhores práticas de desenvolvimento Android para criar uma experiência de utilizador fluida e responsiva.

Funcionalidades

Esta aplicação oferece as seguintes funcionalidades principais:

•
Pesquisa de Personagens: Permite aos utilizadores pesquisar personagens do universo Star Wars.

•
Filtragem Avançada: Implementa filtros para refinar os resultados da pesquisa, facilitando a localização de personagens específicos.

•
Comparação de Personagens: Oferece a capacidade de comparar diferentes personagens, destacando suas características.

•
Interface Responsiva: Desenvolvida com Jetpack Compose para garantir uma experiência de utilizador consistente e adaptável a diferentes tamanhos de ecrã.

•
Integração com API Externa: Consome dados de uma API do Star Wars para fornecer informações atualizadas e abrangentes.

Tecnologias Utilizadas

O projeto é construído com um conjunto robusto de tecnologias e bibliotecas, garantindo escalabilidade, manutenibilidade e desempenho:

•
Kotlin: A linguagem de programação principal, conhecida por sua concisão e segurança.

•
Android SDK: O kit de desenvolvimento de software para a plataforma Android.

•
Jetpack Compose: O toolkit moderno do Android para construir interfaces de utilizador nativas. Permite uma abordagem declarativa para o design da UI.

•
Koin: Um framework leve de injeção de dependências para Kotlin, que simplifica a gestão de dependências e promove um código mais testável e modular.

•
Retrofit: Uma biblioteca de cliente HTTP segura para Android e Java, utilizada para fazer requisições à API do Star Wars e mapear as respostas para objetos Kotlin.

•
Navigation Component: Parte da Android Jetpack, esta biblioteca ajuda a gerir a navegação dentro da aplicação, desde transições simples de botões até padrões de UI mais complexos como gavetas de navegação e guias.

Estrutura do Projeto

A estrutura do projeto é organizada de forma modular e intuitiva, seguindo as diretrizes de uma arquitetura limpa para separar as preocupações e facilitar o desenvolvimento e a manutenção:

```

StarWars/
├── app/                  \# Main Android application module
│   ├── src/
│   │   ├── androidTest/      \# Instrumentation tests for UI and integration
│   │   ├── main/             \# Main application source code
│   │   │   ├── java/com/example/starwars/
│   │   │   │   ├── components/    \# Reusable UI components (e.g., BottomSheet, TopBar)
│   │   │   │   ├── core/          \# Base classes and UI configurations (e.g., themes, colors)
│   │   │   │   ├── koin/          \# Dependency injection configurations (modules, repositories)
│   │   │   │   ├── navigation/    \# Route definitions and navigation logic
│   │   │   │   ├── networking/    \# Network layer (instances, data models, requests, ViewModels)
│   │   │   │   ├── screens/       \# Implementations of different application screens
│   │   │   │   └── utils/         \# Utility functions and classes
│   │   │   └── AndroidManifest.xml \# Android application manifest
│   │   └── test/             \# Unit tests
│   ├── build.gradle.kts      \# Gradle build script for the 'app' module
│   └── proguard-rules.pro    \# ProGuard rules for code optimization and obfuscation
├── gradle/                   \# Gradle Wrapper configuration files
├── .gitignore                \# Files and directories to be ignored by Git
├── build.gradle.kts          \# Gradle build script for the root project
├── gradle.properties         \# Global Gradle properties
├── gradlew                   \# Gradle execution script for Unix/Linux systems
├── gradlew.bat               \# Gradle execution script for Windows systems
└── settings.gradle.kts       \# Gradle module settings

```

Arquitetura

O projeto adota uma arquitetura limpa e modular, baseada nos seguintes princípios:

•
MVVM (Model-View-ViewModel): Separa a lógica de negócio da interface do utilizador, facilitando a testabilidade e a manutenção. Os ViewModels gerenciam o estado da UI e interagem com a camada de dados.

•
Padrão Repositório: Abstrai a fonte de dados, permitindo que a aplicação interaja com dados de diferentes origens (API, base de dados local) de forma transparente.

•
Injeção de Dependências (Koin): Facilita a gestão de dependências entre os componentes, promovendo a modularidade e a testabilidade do código.

•
Modularidade: O código é organizado em módulos lógicos (components, core, koin, navigation, networking, screens, utils), o que melhora a organização, a reutilização e a escalabilidade.
