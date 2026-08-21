.PHONY: dev test format format-check build clean db db-down docker-up docker-down docker-logs docker-rebuild

# Inicia o DB e sobe o Spring Boot
dev: db
	./mvnw spring-boot:run

format:
	./mvnw spotless:apply

format-check:
	./mvnw spotless:check

# Inicia o DB, aplica formatacao e roda os testes
test: db
	./mvnw spotless:apply && ./mvnw clean test

build:
	./mvnw spotless:apply && ./mvnw clean package -DskipTests

clean:
	./mvnw clean

# Local DB infrastructure target
db:
	docker compose up -d postgres

db-down:
	docker compose stop postgres

# Full containerized stack targets
docker-up:
	docker compose up -d

docker-down:
	docker compose down

docker-logs:
	docker compose logs -f

docker-rebuild:
	docker compose down && docker compose up -d --build