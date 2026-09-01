# Digital Wallet API

A RESTful API for managing a digital wallet system, built with **Java 21** and **Spring Boot 3.4**.

The application provides wallet creation, authentication, secure money transfers, balance validation, external authorization, and notification integration. It follows a layered architecture and includes database migrations, automated tests, code formatting, API documentation, and code coverage reporting.

## 🚀 Tech Stack

- **Java 21**
- **Spring Boot 3.4**
  - Spring Web
  - Spring Data JPA
  - Spring Security

- **PostgreSQL 16**
- **JWT** for authentication and authorization
- **Flyway** for database migrations
- **SpringDoc OpenAPI / Swagger** for API documentation
- **Spotless** for code formatting
- **JaCoCo** for code coverage
- **JUnit** for automated testing
- **Docker & Docker Compose**
- **Maven**
- **Make** for development shortcuts

---

## ✨ Features

- User wallet creation
- Support for different wallet types
- JWT-based authentication
- Secure endpoints protected by Spring Security
- Money transfers between wallets
- Balance validation before transfers
- Transfer restrictions based on wallet type
- External authorization before completing a transfer
- External notification integration
- Global exception handling
- CPF/CNPJ uniqueness validation
- Email uniqueness validation
- Database versioning with Flyway
- Interactive API documentation with Swagger UI
- Automated unit and integration tests
- Code coverage reports with JaCoCo
- Automated code formatting with Spotless
- Fully containerized environment with Docker

---

## 🏗️ Project Structure

```text
src
├── main
│   ├── java
│   │   └── com.wallet.api
│   │       ├── client
│   │       │   ├── dto
│   │       │   ├── AuthorizerClient.java
│   │       │   └── NotificationClient.java
│   │       │
│   │       ├── config
│   │       │   └── OpenApiConfig.java
│   │       │
│   │       ├── domain
│   │       │   ├── auth
│   │       │   ├── transfer
│   │       │   └── wallet
│   │       │
│   │       ├── exception
│   │       │   └── GlobalExceptionHandler.java
│   │       │
│   │       ├── infra
│   │       │   ├── config
│   │       │   └── security
│   │       │
│   │       ├── DigitalWalletApiApplication.java
│   │       └── HealthController.java
│   │
│   └── resources
│       ├── db
│       │   └── migration
│       └── application.yml
│
└── test
    └── java
        └── com.wallet.api
```

### Architecture Overview

The application is organized into clear responsibilities:

- **domain** — Core business logic for authentication, wallets, and transfers.
- **client** — HTTP clients responsible for communication with external services.
- **infra** — Infrastructure concerns such as security and HTTP client configuration.
- **config** — Application and OpenAPI configuration.
- **exception** — Custom exceptions and centralized error handling.

---

## 🛠️ Prerequisites

Make sure you have the following installed:

- **Java 21** or higher
- **Docker**
- **Docker Compose**
- **Make** _(optional, for CLI shortcuts)_

You can verify your Java installation with:

```bash
java -version
```
## 🧪 Test Coverage

The project uses **JaCoCo** to measure automated test coverage across the application.

### Current Coverage

| Metric           | Coverage |
| ---------------- | -------: |
| **Instructions** |  **73%** |
| **Branches**     |  **43%** |
| **Methods**      |  **82%** |
| **Classes**      |  **97%** |

The current test suite covers the application's main business domains, including wallet management, transfers, authentication, security, external service clients, repositories, and controllers.

Some of the strongest-covered areas include:

* **Transfer domain:** 87% instruction coverage and 83% branch coverage
* **Wallet domain:** 85% instruction coverage and 80% branch coverage
* **Authentication:** 85% instruction coverage
* **External clients:** 86% instruction coverage
* **Configuration:** 96% instruction coverage

Coverage is continuously measured using JaCoCo and can be regenerated locally with:

```bash
make coverage
```

The generated report is available at:

```text
target/site/jacoco/index.html
```

To generate the report and inspect it through a local HTTP server:

```bash
make coverage-view
```

Then open:

```text
http://localhost:8000
```

> **Note:** Coverage metrics represent the current state of the test suite and may change as the project evolves.

---

## ⚙️ Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/leocavalhere6/Digital-Wallet-API.git
cd digital-wallet-api
```

### 2. Start the database

```bash
make db
```

Or directly with Docker Compose:

```bash
docker compose up -d postgres
```

### 3. Run the application

```bash
make dev
```

The API will start locally on:

```text
http://localhost:8080
```

---

## 🐳 Running with Docker

To start the entire application stack:

```bash
make docker-up
```

Or:

```bash
docker compose up -d
```

To stop the containers:

```bash
make docker-down
```

To follow the application logs:

```bash
make docker-logs
```

To rebuild and restart the entire stack:

```bash
make docker-rebuild
```

---

## 📚 API Documentation

Once the application is running, the API documentation is available through Swagger UI.

### Swagger UI

```text
http://localhost:8080/swagger-ui/index.html
```

### OpenAPI Specification

```text
http://localhost:8080/v3/api-docs
```

You can also display these URLs using:

```bash
make docs
```

---

## 🔐 Authentication

The API uses **JWT (JSON Web Tokens)** to protect secured endpoints.

The authentication flow is:

1. Authenticate using the login endpoint.
2. Receive a JWT access token.
3. Send the token in subsequent requests using the `Authorization` header.

Example:

```http
Authorization: Bearer <your-jwt-token>
```

---

## 💸 Transfer Flow

A transfer follows the application's business rules before being completed:

1. Validate the payer and payee.
2. Verify that both wallets exist.
3. Validate whether the payer is allowed to perform transfers.
4. Check whether the payer has sufficient balance.
5. Request authorization from the external authorization service.
6. Debit the payer's wallet.
7. Credit the payee's wallet.
8. Complete the transfer.
9. Send a notification through the external notification service.

This flow helps ensure that transfers respect business and security requirements.

---

## 🗄️ Database Migrations

Database schema changes are managed using **Flyway**.

Migration files are located at:

```text
src/main/resources/db/migration
```

Current migrations follow the Flyway naming convention:

```text
V1__create_initial_schema.sql
V2__create_wallets_table.sql
```

Flyway automatically applies pending migrations when the application starts.

---

## 🧪 Running Tests

Run the complete test suite:

```bash
make test
```

This command applies code formatting and executes the tests.

Alternatively:

```bash
./mvnw spotless:apply && ./mvnw clean test
```

The project includes tests covering important areas such as:

- Wallet business logic
- Wallet repository operations
- Wallet controllers
- Authentication services
- JWT functionality
- Transfer business rules
- Transfer endpoints
- External authorization client
- Notification client
- Health endpoint

---

## 📊 Code Coverage

Generate the JaCoCo coverage report:

```bash
make coverage
```

The report will be generated at:

```text
target/site/jacoco/index.html
```

To generate and inspect the report through a local HTTP server:

```bash
make coverage-view
```

Then open:

```text
http://localhost:8000
```

---

## 🎨 Code Formatting

This project uses **Spotless** to maintain consistent code formatting.

### Apply formatting

```bash
make format
```

### Check formatting compliance

```bash
make format-check
```

You can also use Maven directly:

```bash
./mvnw spotless:apply
```

```bash
./mvnw spotless:check
```

---

## 🔨 Build

Build the application JAR:

```bash
make build
```

This command formats the code and packages the application while skipping tests.

Equivalent Maven command:

```bash
./mvnw spotless:apply && ./mvnw clean package -DskipTests
```

The generated artifact will be available in:

```text
target/
```

---

## 🧹 Clean Build Files

To remove generated Maven build files:

```bash
make clean
```

---

## ⌨️ Available Make Commands

| Command               | Description                                          |
| --------------------- | ---------------------------------------------------- |
| `make help`           | Display all available commands                       |
| `make dev`            | Start PostgreSQL and run the Spring Boot application |
| `make test`           | Format the code and run the test suite               |
| `make coverage`       | Run tests and generate the JaCoCo report             |
| `make coverage-view`  | Generate coverage and launch a local server          |
| `make format`         | Apply Spotless formatting                            |
| `make format-check`   | Check formatting compliance                          |
| `make build`          | Format and package the application JAR               |
| `make clean`          | Remove the Maven target directory                    |
| `make db`             | Start the PostgreSQL container                       |
| `make db-down`        | Stop the PostgreSQL container                        |
| `make docker-up`      | Start the complete Docker stack                      |
| `make docker-down`    | Stop and remove Docker containers                    |
| `make docker-logs`    | Follow Docker container logs                         |
| `make docker-rebuild` | Rebuild and restart the Docker stack                 |
| `make docs`           | Display Swagger and OpenAPI URLs                     |

---

## 🔍 Health Check

The application includes a health endpoint that can be used to verify that the API is running correctly.

This is useful for:

- Local development
- Docker environments
- CI/CD pipelines
- Deployment health checks

---

## 🧱 Development Workflow

A typical development workflow can be:

```bash
# Start the database
make db

# Run the application
make dev

# Format the code
make format

# Run tests
make test

# Generate coverage
make coverage

# Build the application
make build
```

---

## 🎯 Project Goals

This project was developed to demonstrate practical backend engineering concepts, including:

- RESTful API design
- Domain-driven organization
- Authentication and authorization with JWT
- Business rule validation
- Transactional operations
- External service integration
- Database persistence with JPA
- Database versioning with Flyway
- Automated testing
- Code quality and formatting
- Code coverage analysis
- API documentation
- Containerized development environments

---
