#FROM maven:3.9.6-eclipse-temurin-21 AS build
FROM maven:3.9.5-openjdk-21 AS build
COPY . .
RUN mvn clean package -DskipTests

#FROM eclipse-temurin:21-jdk-slim
FROM openjdk:21-jdk-slim
COPY --from=build /target/MightyBull-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]