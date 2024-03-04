# Web Application: News Service
## Technologies Used
* • Spring Boot 3.x
* • Java 17
* • Gradle
* • PostgreSQL
* • Liquibase
* • Redis (optional, for caching)
* • Spring Security
* • Spring Cloud Feign Client
* • Spring Cloud Config (optional)
* • Testcontainers
* • WireMock
* • Docker

## Services
* news: Manages news and comments.
* users: Manages users.
* cache-starter:Custom Spring Boot starter for caching the service layer.
* exception-handling-starter: Custom Spring Boot starter for handling exceptions.
* logging-starter: Custom Spring Boot starter for logging.

## Microservices Architecture
* The Users Service manages user-related information and roles stored in a PostgreSQL database.
* Communication between the News Service and Users Service is done via REST using Spring Cloud Feign Client.

## Description
This service provides REST APIs for managing news articles and comments.

## Database Initialization with Liquibase
When starting the service, scripts are rolled onto the working database 
(the necessary tables are generated from one file and the tables are filled with data from another file)

## Entities
### NewsEntity
- id 
- header 
- content
- publishedBy 
- comments 

### CommentEntity
- id
- header
- content
- publishedBy
- news

## REST API 

### News Management 8080

### News API
* GET /api/v1/news: Retrieve a paginated list of news articles.
* GET /api/v1/news/{newsId}: Retrieve a specific news article by ID.
* GET /api/v1/news/{newsId}/comments: Retrieve paginated comments for a specific news article.
* POST /api/v1/news: Create a new news article.
* PUT /api/v1/news/{newsId}: Update an existing news article.
* DELETE /api/v1/news/{newsId}: Delete a news article.

### Comments API
* GET /api/v1/comments: Retrieve a paginated list of comments.
*  GET  /api/v1/comments/{commentId}: Retrieve a specific comment by ID.
*  POST /api/v1/comments: Add a new comment .
*  PUT /api/v1/comments{commentId}: Update an existing comment.
*  DELETE /api/v1/comments{commentId}: Delete a comment.

### User Management (via Users Service) - 8081 
* APIs for user registration and role-based access control are provided by the Users Service.
* POST /api/v1/auth/sign-in: Register a user
* POST /api/v1/auth/sign-up: Authenticate a user
* GET /api/v1/users: Get the currently authenticated user.


## Configuration File: application.yml
All configurations are externalized to the application.yml file. Spring profiles are utilized for different environments (e.g., test & prod).

#### Configuration Management with Spring Cloud Config: 
This service utilizes Spring Cloud Config as a configuration management solution.The Spring Cloud Config server is hosted on port 8888 and stores the production (prod) configuration.

## Deployment
* Dockerization is supported with a Dockerfile provided for the Spring Boot application.
* docker-compose.yml is available to orchestrate the deployment of both the PostgreSQL database and the application in containers.

## Security
* Spring Security is implemented with role-based access control.
* Users can register with roles such as admin, journalist, or subscriber.
* Admins have full CRUD access, journalists can only modify their own articles, and subscribers can only modify their own comments.
* Unregistered users have read-only access

## Custom Spring Boot Starters Configuration
### Cache Starter
```
Prefix: cache.type
Type: java.lang.String
Description: The type of cache used.
Default Value: "LRU"
```

```
Prefix: cache.size
Type: java.lang.Integer
Description: The size of the used cache.
Default Value: 16
```
### Exception Handling Starter
```
Prefix: spring.exception-handling.enabled
Type: java.lang.Boolean
Description: Enable exception handling.
Default Value: true
```
### Logging Starter
```
Prefix: spring.logging.enabled
Type: java.lang.Boolean
Description: Enables custom logging.
Default Value: true
```

### In order to build the jar you should run the following command:
```
./gradlew clean build -x test
```

### In order to run application you should run the following command(before this you should build the jar):
```
docker-compose up
```
### To run tests:
```
./gradlew test
```