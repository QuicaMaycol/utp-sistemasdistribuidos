FROM eclipse-temurin:21-jre
WORKDIR /app
COPY discovery-service/target/discovery-service-1.0-SNAPSHOT.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
