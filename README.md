# Task Manager

A REST API for managing tasks, projects and users, with asynchronous notifications when a task status changes.

## Tech stack
- Java 17, Spring Boot, Spring Data JPA (Hibernate)
- PostgreSQL, Flyway (database migrations)
- RabbitMQ (messaging), `@Async` with a custom thread pool
- JUnit 5, Mockito
- Docker Compose

## Features
- CRUD for tasks; create projects and users
- Entity relations: a task belongs to a project and has an assignee (`@ManyToOne` / `@OneToMany`)
- Filtering by status and project, pagination
- Input validation and centralized error handling (400, 404)
- When a task status changes, an event is published to RabbitMQ and handled asynchronously in a separate thread
- Unit tests for the service layer

## Architecture
```
Controller -> Service -> Repository -> PostgreSQL
                  |
                  v
          TaskEventPublisher -> RabbitMQ -> TaskEventListener -> NotificationService (@Async)
```

## Getting started
1. Start PostgreSQL and RabbitMQ:
   ```
   docker compose up -d
   ```
2. Run the application:
   ```
   ./mvnw spring-boot:run
   ```
3. API: `http://localhost:8080`
4. RabbitMQ