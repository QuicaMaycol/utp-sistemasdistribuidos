FROM eclipse-temurin:21-jre
WORKDIR /app
COPY textil-service/target/textil-service-1.0-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
