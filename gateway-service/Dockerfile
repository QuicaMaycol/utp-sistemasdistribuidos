FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

COPY discovery-service discovery-service
COPY gateway-service gateway-service
COPY auth-service auth-service
COPY textil-service textil-service

# Compilar solo el módulo de gateway
RUN chmod +x mvnw && ./mvnw clean package -pl gateway-service -am -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/gateway-service/target/gateway-service-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
