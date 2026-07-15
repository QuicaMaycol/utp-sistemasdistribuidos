FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar configuración del POM padre
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Copiar todos los módulos para poder compilar con referencias cruzadas
COPY discovery-service discovery-service
COPY gateway-service gateway-service
COPY auth-service auth-service
COPY textil-service textil-service

# Compilar solo el módulo de discovery
RUN chmod +x mvnw && ./mvnw clean package -pl discovery-service -am -DskipTests

# Imagen de ejecución ligera
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/discovery-service/target/discovery-service-1.0-SNAPSHOT.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
