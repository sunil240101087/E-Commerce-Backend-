<<<<<<< HEAD
# Ecommerce API

Backend REST API for an ecommerce-style system: catalog (products and categories), user accounts, JWT authentication, orders with inventory reservation, and Stripe-oriented payment flows. Built as a portfolio-grade Spring Boot service with clear layering and domain-focused packages.

## Tech stack

| Area | Choice |
|------|--------|
| Runtime | Java 21 |
| Framework | Spring Boot 4 |
| API | Spring Web MVC, Jakarta Validation |
| Security | Spring Security, JWT (JJWT), BCrypt passwords |
| Data | Spring Data JPA, Hibernate |
| Databases | PostgreSQL (dev/prod), H2 in-memory (tests) |
| Mapping | MapStruct |
| Payments | Stripe Java SDK + webhook endpoint |
| Ops | Docker, Docker Compose (app + PostgreSQL + Adminer) |

## What this project demonstrates

- **Layered design**: controllers → service interfaces/implementations → JPA entities and repositories, with DTOs and MapStruct mappers at the boundaries.
- **Feature modules**: packages such as `auth`, `catalog`, `order`, `inventory`, `payment`, and `user` keep related code together.
- **Security model**: public read access to catalog and inventory; registration/login open; customer-specific routes authenticated; admin-only CRUD and management endpoints.
- **Domain rules**: custom exceptions and a global handler for consistent API errors; inventory reserve/release aligned with order lifecycle; guards around categories, products, and orders where applicable.
- **External integration**: Stripe payment creation and a dedicated webhook path for asynchronous status updates.

For diagrams and a full architectural narrative, see [README-ARCHITECTURE.md](README-ARCHITECTURE.md). For an exhaustive feature list, see [README-FEATURES.md](README-FEATURES.md).

## Requirements

- JDK 21
- Maven 3.9+ (or use the included Maven Wrapper)
- PostgreSQL 14+ when **not** using Docker (local `dev` profile expects `localhost:5432`)

## Run locally (development)

1. Start PostgreSQL and create a database named `ecommerce` (or match `application-dev.yml`).
2. Default dev datasource in `application-dev.yml` uses user `postgres` and password `password` — adjust if needed.
3. From the project root:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell, use `.\mvnw.cmd spring-boot:run` (and `.\mvnw.cmd test` below).

The app loads the `dev` profile by default (`spring.profiles.active` in `application.yml`). API base URL: `http://localhost:8080`.

## Run with Docker Compose

Builds the Spring Boot image, starts PostgreSQL, wires the app to the `prod` profile, and exposes Adminer for DB inspection.

```bash
docker compose up --build
```

- **API**: `http://localhost:8080`
- **Adminer**: `http://localhost:8888` (map container port 8080; use host `db`, database `ecommerce`, user/password from `docker-compose.yml`)

## API overview

All routes are under `/api/v1` unless noted.

| Area | Endpoints (summary) |
|------|---------------------|
| Auth | `POST /auth/register`, `POST /auth/login` |
| Categories | `GET/POST /categories`, `GET/PUT/DELETE /categories/{id}` |
| Products | `GET/POST /products`, `GET /products/category/{categoryId}`, `GET/PUT/DELETE /products/{id}` |
| Inventory | `GET /inventories`, `GET /inventories/product/{productId}`, `PUT /inventories/product/{id}` (admin) |
| Orders | `POST /orders`, `GET /orders/me`, `GET /orders/{id}`, `PUT /orders/{id}`, `POST /orders/{id}/cancel`, `POST /orders/{id}/pay`; admin `GET /orders` |
| Payments | `GET /payments/me`; admin `GET /payments`, `GET /payments/{id}` |
| Users | `GET /users` (admin) |
| Webhooks | `POST /webhooks/stripe` (Stripe; no JWT — verify signature in implementation) |

Send `Authorization: Bearer <token>` for protected routes after login.

## Configuration and secrets

JWT settings, optional admin seeding, and Stripe keys are configured in `application.yml`. **Before making the repository public**, replace any real secrets with environment-specific configuration (for example Spring `SPRING_APPLICATION_JSON` or externalized `application-prod.yml`), rotate compromised keys, and avoid committing production credentials.

## Tests

```bash
./mvnw test
```

Tests use an in-memory H2 database (see `src/test/resources/application.properties`).

## Project layout (high level)

```
src/main/java/com/younesghu/Ecommerce/
├── auth/           # JWT, login, register
├── catalog/        # categories, products
├── order/          # orders and order items
├── inventory/      # stock reservation and updates
├── payment/        # payments + Stripe webhook
├── user/           # user API and security user details
├── security/       # filter chain, CORS, password encoding
└── exception/      # global error handling
```

## License

This repository is provided as a personal / portfolio project. Add a `LICENSE` file if you want to specify terms for reuse.
=======
# E-Commerce-Backend-
>>>>>>> e83bf2bae9ed87a6cc7669a070fd8525721f3ebb
