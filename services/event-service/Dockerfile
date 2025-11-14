# Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests clean package spring-boot:repackage spring-boot:repackage

# Run
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/event-service-0.0.1.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar","--server.port=8082"]
