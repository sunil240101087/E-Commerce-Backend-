FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy Maven descriptor and download dependencies
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw
COPY mvnw.cmd mvnw.cmd
RUN mvn -B -q dependency:go-offline

# Copy source and build the application (skip tests for faster image builds)
COPY src src
RUN mvn -B -q package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the packaged Spring Boot jar from the build stage
COPY --from=build /app/target/Ecommerce-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Use the prod profile by default inside the container
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]
