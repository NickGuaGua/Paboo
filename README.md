# Paboo

<img src="/preview/paboo.gif" align="right" width="320"/>

## Tech stack & Open-source libraries
- Minimum SDK level 23
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- Jetpack
    - ViewModel: UI state holder. Allows data to survive configuration changes such as screen rotations.
    - Room: Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
    - Paging and RemoteMediator: 
- Architecture
    - MVI Architecture (View - Intent - ViewModel - Model)
    - Clean Architecture
    - Repository Pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs.
- [Timber](https://github.com/JakeWharton/timber) - A logger with a small, extensible API.

## Open API

<img src="/preview/google-news-api.png" align="right" width="21%"/>

Paboo using the [GoogleNewsAPI](https://newsapi.org/s/google-news-api) for constructing RESTful API.<br>