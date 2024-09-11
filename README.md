# DUX Software

## Overview

This project is a Spring Boot 3 application built with Java 17 that provides a RESTful API for managing football teams. It includes functionality for querying, creating, updating, and deleting football teams. Additionally, it features JWT-based authentication and is dockerized for deployment.

## Features

- **List all teams**: `GET /teams`
- **Get a team by ID**: `GET /teams/{id}`
- **Search teams by name**: `GET /teams/search?name={value}`
- **Create a new team**: `POST /teams`
- **Update a team**: `PUT /teams/{id}`
- **Delete a team**: `DELETE /teams/{id}`
- **Authenticate**: `POST /auth/login`

## Getting Started

### Prerequisites

- Java 17
- Maven or Gradle (for building the project)
- Docker (for containerization)

### Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/pulidodev/dux-software.git
   cd dux-software
   ```

2. **Build the project**

   Using Maven:

   ```bash
   ./mvnw clean install
   ```

   Or using Gradle:

   ```bash
   ./gradlew build
   ```

3. **Set up the database**

   The application uses an in-memory H2 database, which will be automatically initialized with the provided SQL script.

4. **Run the application**

   Using Maven:

   ```bash
   ./mvnw spring-boot:run
   ```

   Or using Gradle:

   ```bash
   ./gradlew bootRun
   ```

5. **Access the API**

   The application will be available at `http://localhost:8080`. You can use tools like Postman or cURL to interact with the API.

### Authentication

- **Login**

  Send a POST request to `/auth/login` with the following body:

  ```json
  {
    "username": "test",
    "password": "12345"
  }
  ```

  On successful authentication, you will receive a JSON response containing a JWT token:

  ```json
  {
    "token": "<TOKEN>"
  }
  ```

  Use this token to access protected endpoints.

### Endpoints

- **Get all teams**

  ```http
  GET /teams
  ```

- **Get a team by ID**

  ```http
  GET /teams/{id}
  ```

- **Search teams by name**

  ```http
  GET /teams/search?name={value}
  ```

- **Create a new team**

  ```http
  POST /teams
  ```

  **Request Body:**

  ```json
  {
    "name": "New Team FC",
    "league": "New League",
    "country": "New Country"
  }
  ```

- **Update a team**

  ```http
  PUT /teams/{id}
  ```

  **Request Body:**

  ```json
  {
    "name": "Updated Name",
    "league": "Updated League",
    "country": "Updated Country"
  }
  ```

- **Delete a team**

  ```http
  DELETE /teams/{id}
  ```

### Error Handling

- **404 Not Found**

  ```json
  {
    "message": "Team not found",
    "code": 404
  }
  ```

- **400 Bad Request**

  ```json
  {
    "message": "Invalid team data",
    "code": 400
  }
  ```

- **401 Unauthorized**

  ```json
  {
    "message": "Invalid username or password",
    "code": 401
  }
  ```

### Docker

To build and run the application using Docker, follow these steps:

1. **Build the Docker image**

   ```bash
   docker build -t challenge .
   ```

2. **Run the Docker container**

   ```bash
   docker run -p 8080:8080 challenge
   ```

### Testing

Unit tests are included in the application. Mocking is used for database interactions.

### API Documentation

The API documentation is generated using Swagger. Access it at:

```
http://localhost:8080/swagger-ui.html
```
