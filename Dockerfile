# ----------------------------
# Stage 1: Build the application
# ----------------------------
FROM gradle:8.2.1-jdk17 AS build
WORKDIR /app

# Copy Gradle wrapper and project files
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src

# Ensure Gradle wrapper is executable
RUN chmod +x gradlew

# Build the project (skip tests for faster build)
RUN ./gradlew clean build -x test --no-daemon

# ----------------------------
# Stage 2: Run the application
# ----------------------------
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the built JAR from the build stage
# Find the exact JAR name to avoid wildcard issues
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
