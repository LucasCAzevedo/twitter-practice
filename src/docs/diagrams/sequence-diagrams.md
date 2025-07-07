```markdown
# Diagramas de Sequência

## 1. Criar Tweet - Fluxo Principal

```plantuml
@startuml CreateTweet_Success

actor Client
participant TweetController
participant TweetService
participant TweetRepository
entity Tweet

Client -> TweetController: POST /tweets\n{content, authorHandle}
activate TweetController

TweetController -> TweetService: createTweet(request)
activate TweetService

TweetService -> TweetService: validateTweetRequest(request)
note right: Valida conteúdo\ne authorHandle

TweetService -> Tweet: new Tweet(id, content, authorHandle, timestamp)
activate Tweet
Tweet --> TweetService: tweet
deactivate Tweet

TweetService -> TweetRepository: save(tweet)
activate TweetRepository
TweetRepository --> TweetService: tweet
deactivate TweetRepository

TweetService --> TweetController: tweet
deactivate TweetService

TweetController --> Client: 201 Created\n{tweet}
deactivate TweetController

@enduml
2. Criar Tweet - Fluxo de Erro (Validação)
@startuml CreateTweet_ValidationError

actor Client
participant TweetController
participant TweetService

Client -> TweetController: POST /tweets\n{invalid data}
activate TweetController

TweetController -> TweetService: createTweet(request)
activate TweetService

TweetService -> TweetService: validateTweetRequest(request)
note right: Falha na validação

TweetService --> TweetController: InvalidTweetException
deactivate TweetService

TweetController --> Client: 422 Unprocessable Entity
deactivate TweetController

@enduml
3. Listar Todos os Tweets
@startuml GetAllTweets

actor Client
participant TweetController
participant TweetService
participant TweetRepository

Client -> TweetController: GET /tweets
activate TweetController

TweetController -> TweetService: getAllTweets()
activate TweetService

TweetService -> TweetRepository: findAll()
activate TweetRepository
TweetRepository --> TweetService: List<Tweet> (ordenado)
deactivate TweetRepository

TweetService --> TweetController: List<Tweet>
deactivate TweetService

TweetController --> Client: 200 OK\n[tweets]
deactivate TweetController

@enduml
4. Listar Tweets por Autor - Sucesso
@startuml GetTweetsByAuthor_Success

actor Client
participant TweetController
participant TweetService
participant TweetRepository

Client -> TweetController: GET /tweets/@username
activate TweetController

TweetController -> TweetService: getTweetsByAuthorHandle("@username")
activate TweetService

TweetService -> TweetService: validateAuthorHandle("@username")
note right: Valida formato\ndo handle

TweetService -> TweetRepository: findByAuthorHandle("@username")
activate TweetRepository
TweetRepository --> TweetService: List<Tweet> (não vazia)
deactivate TweetRepository

TweetService --> TweetController: List<Tweet>
deactivate TweetService

TweetController --> Client: 200 OK\n[tweets]
deactivate TweetController

@enduml
5. Listar Tweets por Autor - Não Encontrado
@startuml GetTweetsByAuthor_NotFound

actor Client
participant TweetController
participant TweetService
participant TweetRepository

Client -> TweetController: GET /tweets/@nonexistent
activate TweetController

TweetController -> TweetService: getTweetsByAuthorHandle("@nonexistent")
activate TweetService

TweetService -> TweetService: validateAuthorHandle("@nonexistent")

TweetService -> TweetRepository: findByAuthorHandle("@nonexistent")
activate TweetRepository
TweetRepository --> TweetService: List<Tweet> (vazia)
deactivate TweetRepository

TweetService --> TweetController: AuthorNotFoundException
deactivate TweetService

TweetController --> Client: 404 Not Found
deactivate TweetController

@enduml
6. Deletar Tweet - Sucesso
@startuml DeleteTweet_Success

actor Client
participant TweetController
participant TweetService
participant TweetRepository

Client -> TweetController: DELETE /tweets/{id}
activate TweetController

TweetController -> TweetService: deleteTweet(id)
activate TweetService

TweetService -> TweetRepository: deleteById(id)
activate TweetRepository
TweetRepository --> TweetService: true
deactivate TweetRepository

TweetService --> TweetController: void
deactivate TweetService

TweetController --> Client: 204 No Content
deactivate TweetController

@enduml
7. Deletar Tweet - Não Encontrado
@startuml DeleteTweet_NotFound

actor Client
participant TweetController
participant TweetService
participant TweetRepository

Client -> TweetController: DELETE /tweets/{invalid-id}
activate TweetController

TweetController -> TweetService: deleteTweet(invalid-id)
activate TweetService

TweetService -> TweetRepository: deleteById(invalid-id)
activate TweetRepository
TweetRepository --> TweetService: false
deactivate TweetRepository

TweetService --> TweetController: TweetNotFoundException
deactivate TweetService

TweetController --> Client: 404 Not Found
deactivate TweetController

@enduml