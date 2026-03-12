# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

# Copy the wrapper and pom.xml first to download dependencies
# This leverages Docker cache to avoid re-downloading dependencies if only source code changes
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
# Make the wrapper executable
RUN chmod +x ./mvnw

# Optional: Download dependencies before copying the source
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY src src

# Build the application
RUN ./mvnw package -DskipTests

# Stage 2: Create the runtime image using a smaller base image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Create a non-root user to run the application for better security
RUN groupadd -g 1000 appgroup && \
    useradd -u 1000 -g appgroup -s /bin/sh appuser

# Copy the built jar from the build stage
COPY --from=build --chown=appuser:appgroup /app/target/*.jar app.jar

# Use the non-root user
USER appuser

# Expose the application port
EXPOSE 8080

# Run the application with the docker profile active by default
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
