# Spring Boot Customer Management with JWT Implementation

## TODO:

Add docker-compose

## Features

1. login as a user with role Admin
2. see the list of users with role Customer
3. create new user with role Customer
4. delete exists user with role Customer
5. update exists user with role Customer

## Technology

* Spring Boot 3.0
* Spring Security
* JSON Web Tokens (JWT)
* PostgresQL + Flyway + JOOQ
* Maven

## Requirements

Need to have postgresQL running locally and **ready database created**.

## Local Testing Guide

Build

```mvn clean install```

Run locally

```mvn spring-boot:run```

## Some curl commands can be used to test

Register:

```shell
curl --location 'http://localhost:8080/api/v1/auth/register' \
--header 'Content-Type: application/json' \
--data-raw '{
  "firstname": "nguyen",
  "lastname": "toan",
  "email": "nguyen.toan@gmail.com",
  "password": "1234",
  "role": "ADMIN"
}'
```

Response can be look like:

```json
{"access_token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuZ3V5ZW4udG9hbkBnbWFpbC5jb20iLCJpYXQiOjE2ODUzOTA3NjksImV4cCI6MTY4NTQ3NzE2OX0.p4iPm_Em_EhzdG09QszIlGhpYwl_ExDHUJdzXCatbvU","refresh_token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuZ3V5ZW4udG9hbkBnbWFpbC5jb20iLCJpYXQiOjE2ODUzOTA3NjksImV4cCI6MTY4NTk5NTU2OX0.Yj1U-bdvRG6rNACGaPP0SSiHMbykx3CpfXF6HBaNSa0"}
```

Get All customer (use with returned access_token):

```shell
curl --location 'http://localhost:8080/api/v1/admin' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuZ3V5ZW4udG9hbkBnbWFpbC5jb20iLCJpYXQiOjE2ODUzOTA3NjksImV4cCI6MTY4NTQ3NzE2OX0.p4iPm_Em_EhzdG09QszIlGhpYwl_ExDHUJdzXCatbvU'
```


