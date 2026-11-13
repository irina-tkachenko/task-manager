# Task Manager

REST API для управления задачами, проектами и пользователями.

## Стек
- Java 21, Spring Boot, Spring Data JPA (Hibernate)
- PostgreSQL, Flyway (миграции)
- JUnit 5, Mockito
- Docker Compose

## Возможности
- CRUD для задач, создание проектов и пользователей
- Связи: задача принадлежит проекту и имеет исполнителя
- Фильтрация по статусу и проекту, пагинация
- Валидация входных данных и единая обработка ошибок (400, 404)
- Unit-тесты сервисного слоя

## Запуск
1. Запустить базу данных:
   ```
   docker compose up -d
   ```
2. Запустить приложение:
   ```
   ./mvnw spring-boot:run
   ```
3. API доступно на `http://localhost:8080`

## Тесты
```
./mvnw test
```

## Примеры запросов
```
curl -X POST http://localhost:8080/api/projects -H "Content-Type: application/json" -d '{"name":"Website"}'
curl -X POST http://localhost:8080/api/tasks -H "Content-Type: application/json" -d '{"title":"Design homepage","status":"IN_PROGRESS","projectId":1}'
curl "http://localhost:8080/api/tasks?status=IN_PROGRESS&page=0&size=5"
```

## Эндпоинты
| Метод | URL | Описание |
|-------|-----|----------|
| GET | /api/tasks | Список задач (фильтры status, projectId, пагинация) |
| GET | /api/tasks/{id} | Задача по id |
| POST | /api/tasks | Создать задачу |
| PUT | /api/tasks/{id} | Обновить задачу |
| DELETE | /api/tasks/{id} | Удалить задачу |
| GET/POST | /api/projects | Проекты |
| GET/POST | /api/users | Пользователи |