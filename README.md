# 👛 Digital Wallet API

A RESTful API for digital wallet management built with **Java 21** and **Spring Boot 3.4**, featuring JWT security, database migrations via **Flyway**, code formatting via **Spotless**, and coverage metrics using **JaCoCo**.

## 🚀 Tech Stack

- **Java 21**
- **Spring Boot 3.4** (Spring Data JPA, Spring Security, Spring Web)
- **PostgreSQL 16**
- **Flyway** (Database Migrations)
- **Spotless** (Code Formatting)
- **JaCoCo** (Code Coverage)
- **Docker & Docker Compose**
- **Maven**

## 🛠️ Prerequisites

- **Java 21** or higher
- **Docker** and **Docker Compose**
- **Make** (optional, for CLI shortcuts)

## ⚙️ Quick Start

### 1. Development Mode

Start the local PostgreSQL container and launch the Spring Boot application:

```bash
make dev
2. Manual SetupSpin up the PostgreSQL database:Bashdocker compose up -d postgres
Run the application:Bash./mvnw spring-boot:run
The API will be available at http://localhost:8080.🧪 Testing & Code QualityRun Tests: Run Spotless auto-formatting and execute unit/integration tests:Bashmake test
Code Coverage Report: Generate JaCoCo coverage metrics in target/site/jacoco:Bashmake coverage
Inspect Coverage in Browser: Serve JaCoCo report on http://localhost:8000:Bashmake coverage-view
Format Codebase: Apply Spotless formatting rules:Bashmake format
📌 API EndpointsMethodEndpointDescriptionAuthenticationPOST/auth/loginUser authenticationPublicPOST/walletsCreate walletJWTGET/wallets/{id}Fetch balance & wallet detailsJWTPOST/transfersTransfer funds between walletsJWTGET/actuator/healthService health checkPublic
```
