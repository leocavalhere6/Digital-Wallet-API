.PHONY: dev test build clean docker-up docker-down docker-logs docker-rebuild

# ==========================================
# Java & Maven Commands
# ==========================================
dev:
	./mvnw spring-boot:run

test:
	./mvnw test

build:
	./mvnw clean package -DskipTests

clean:
	./mvnw clean

# ==========================================
# Docker Lifecycle Commands
# ==========================================
docker-up:
	docker compose up -d

docker-down:
	docker compose down

docker-logs:
	docker compose logs -f

docker-rebuild:
	docker compose down && docker compose up -d --build