#FROM maven:3.9.6-eclipse-temurin-21 AS build
FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

#FROM eclipse-temurin:21-jdk-slim
FROM eclipse-temurin:21-jdk
COPY --from=build /target/MightyBull-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}", "--server.port=${SERVER_PORT}"]