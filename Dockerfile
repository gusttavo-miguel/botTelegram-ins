# Etapa 1: build da aplicação
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B -DskipTests clean package

# Etapa 2: imagem final para execução
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o jar gerado pela etapa de build
COPY --from=builder /app/target/dev-0.0.1-SNAPSHOT.jar app.jar

# Porta padrão do Spring Boot
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]

