# spring-security-jwt
> Spring Boot Role based JWT implementation

## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies)
* [API List](#api-list)
* [Setup](#setup)
## General Information
- In this project you can basically register the system, login the system, refresh your token or validate your token.
The system work with 2 token type, access_token and refresh_token.

  
## Technologies
- Spring Boot - version 2.7.5
- Spring Security - version 5.7.4
- Java - version 8
- h2database - version 2.1.214
- Lombok - version 1.18.24

## API list
**There are 4 API endpoints;**

>[AuthController](src/main/java/tr/com/nebildev/springsecurityjwt/controller/AuthController.java) /api/v1/auth

- POST /login -> public API for all users
  - Login logic is in CustomAuthenticationFilter
- POST /register -> public API for all users
- POST /refresh-token -> if your token expired, you can refresh your token by this API
- POST /validate-token -> you can check is your token valid or not

>[UserController](src/main/java/tr/com/nebildev/springsecurityjwt/controller/UserController.java)  /api/v1/users

- GET  -> if the given user has ADMIN role, list all users
- POST  -> if the given user has ADMIN role, create a user
- POST /{username}/roles -> if the given user has ADMIN role, add a role to a user

>[RoleController](src/main/java/tr/com/nebildev/springsecurityjwt/controller/RoleController.java)  /api/v1/roles

- POST -> if the given user has ADMIN role, create a role
- GET  -> if the given user has ADMIN role, list all roles

>[ResourceController](src/main/java/tr/com/nebildev/springsecurityjwt/controller/ResourceController.java)  /api/v1/resources 

- GET -> public API

## Setup

- $ mvn clean package
- docker build -t spring-security-jwt.jar .

After building finished, you can run the image

- docker run --name spring-security-jwt -p 8081:8081 spring-security-jwt.jar