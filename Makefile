.PHONY: help dev test coverage coverage-view format format-check build clean db db-down docker-up docker-down docker-logs docker-rebuild

.DEFAULT_GOAL := help

help: ## Display available commands list
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-18s\033[0m %s\n", $$1, $$2}'

dev: db ## Start local Postgres database and run Spring Boot application
	./mvnw spring-boot:run

format: ## Apply Spotless code formatting
	./mvnw spotless:apply

format-check: ## Check code formatting compliance
	./mvnw spotless:check

test: db ## Apply formatting and execute unit/integration test suite
	./mvnw spotless:apply && ./mvnw clean test

coverage: db ## Run tests and generate JaCoCo report in target/site/jacoco
	./mvnw spotless:apply && ./mvnw clean test jacoco:report
	@echo "Report generated at: target/site/jacoco/index.html"

coverage-view: coverage ## Run tests and launch local HTTP server on port 8000 to inspect JaCoCo report
	python3 -m http.server 8000 --directory target/site/jacoco

build: ## Format code and package application JAR (skipping tests)
	./mvnw spotless:apply && ./mvnw clean package -DskipTests

clean: ## Clean Maven target build folder
	./mvnw clean

db: ## Start Postgres database via Docker in background
	docker compose up -d postgres

db-down: ## Stop local Postgres container
	docker compose stop postgres

docker-up: ## Start full containerized stack via Docker Compose
	docker compose up -d

docker-down: ## Tear down all Docker stack containers
	docker compose down

docker-logs: ## Follow unified Docker container logs in real time
	docker compose logs -f

docker-rebuild: ## Tear down, rebuild Docker images, and launch stack
	docker compose down && docker compose up -d --build