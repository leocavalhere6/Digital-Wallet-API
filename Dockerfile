# Stage 1: Build da aplicação
FROM eclipse-temurin:25-jdk AS builder
WORKDIR /app

# Copia os arquivos de dependência do Maven para aproveitar o cache das camadas
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline -B

# Copia o código fonte e gera o pacote .jar
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Imagem final leve de execução (JRE)
FROM eclipse-temurin:25-jre
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]