# Diagrama de Classes

## Representação em PlantUML

```plantuml
@startuml TwitterAPI_ClassDiagram

package "com.example.twitterapi" {
 
 package "model" {
     class Tweet {
         - id: String
         - content: String
         - authorHandle: String
         - timestamp: ZonedDateTime
         + Tweet()
         + Tweet(id: String, content: String, authorHandle: String, timestamp: ZonedDateTime)
         + getId(): String
         + setId(id: String): void
         + getContent(): String
         + setContent(content: String): void
         + getAuthorHandle(): String
         + setAuthorHandle(authorHandle: String): void
         + getTimestamp(): ZonedDateTime
         + setTimestamp(timestamp: ZonedDateTime): void
     }
 }
 
 package "dto" {
     class CreateTweetRequest {
         - content: String
         - authorHandle: String
         + CreateTweetRequest()
         + CreateTweetRequest(content: String, authorHandle: String)
         + getContent(): String
         + setContent(content: String): void
         + getAuthorHandle(): String
         + setAuthorHandle(authorHandle: String): void
     }
 }
 
 package "controller" {
     class TweetController {
         - tweetService: TweetService
         + createTweet(request: CreateTweetRequest): ResponseEntity<Tweet>
         + getAllTweets(): ResponseEntity<List<Tweet>>
         + getTweetsByAuthorHandle(authorHandle: String): ResponseEntity<List<Tweet>>
         + deleteTweet(id: String): ResponseEntity<Void>
     }
 }
 
 package "service" {
     class TweetService {
         - MAX_CONTENT_LENGTH: int {static final}
         - AUTHOR_HANDLE_PATTERN: Pattern {static final}
         - tweetRepository: TweetRepository
         + createTweet(request: CreateTweetRequest): Tweet
         + getAllTweets(): List<Tweet>
         + getTweetsByAuthorHandle(authorHandle: String): List<Tweet>
         + deleteTweet(id: String): void
         - validateTweetRequest(request: CreateTweetRequest): void
         - validateAuthorHandle(authorHandle: String): void
     }
 }
 
 package "repository" {
     class TweetRepository {
         - tweets: Map<String, Tweet>
         + save(tweet: Tweet): Tweet
         + findById(id: String): Optional<Tweet>
         + findAll(): List<Tweet>
         + findByAuthorHandle(authorHandle: String): List<Tweet>
         + deleteById(id: String): boolean
         + existsByAuthorHandle(authorHandle: String): boolean
     }
 }
 
 package "exception" {
     class InvalidTweetException {
         + InvalidTweetException(message: String)
     }
     
     class TweetNotFoundException {
         + TweetNotFoundException(message: String)
     }
     
     class AuthorNotFoundException {
         + AuthorNotFoundException(message: String)
     }
     
     class InvalidAuthorHandleException {
         + InvalidAuthorHandleException(message: String)
     }
     
     class RuntimeException {
     }
 }
}

' Relacionamentos
TweetController --> TweetService : uses
TweetService --> TweetRepository : uses
TweetService --> Tweet : creates
TweetController --> CreateTweetRequest : receives
TweetController --> Tweet : returns
TweetRepository --> Tweet : stores

' Exceções
InvalidTweetException --|> RuntimeException
TweetNotFoundException --|> RuntimeException
AuthorNotFoundException --|> RuntimeException
InvalidAuthorHandleException --|> RuntimeException

TweetService --> InvalidTweetException : throws
TweetService --> TweetNotFoundException : throws
TweetService --> AuthorNotFoundException : throws
TweetService --> InvalidAuthorHandleException : throws

' Anotações Spring
note right of TweetController : @RestController\n@RequestMapping("/tweets")
note right of TweetService : @Service
note right of TweetRepository : @Repository

@enduml