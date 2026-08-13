.PHONY: dev test build clean

# Roda a aplicação Spring Boot em modo desenvolvimento
dev:
	./mvnw spring-boot:run

# Roda todos os testes unitários e de integração
test:
	./mvnw test

# Compila o JAR da aplicação (pulando testes se quiser rapidez)
build:
	./mvnw clean package -DskipTests

# Limpa a pasta /target
clean:
	./mvnw clean