.PHONY: dev test build clean db db-down docker-up docker-down docker-logs docker-rebuild

dev:
	./mvnw spring-boot:run

test:
	./mvnw test

build:
	./mvnw clean package -DskipTests

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