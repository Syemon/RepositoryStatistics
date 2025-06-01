FROM eclipse-temurin:21.0.2_13-jdk AS build

WORKDIR /app

# Copy Maven wrapper and POM first (for better layer caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make mvnw executable
RUN chmod +x ./mvnw

# Download dependencies (this will be cached if dependencies don't change)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application with a specific final name to avoid wildcards later
RUN ./mvnw package -DskipTests -Dmaven.finalName=app

# Create runtime image
FROM eclipse-temurin:21.0.2_13-jre-alpine

WORKDIR /app

# Create a non-root user to run the application
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copy the built application from the previous stage with a specific name
#COPY --from=build /app/target/app.jar ./app.jar
COPY --from=build /app/target/repositorystatistics-0.0.1-SNAPSHOT.jar ./app.jar
# Set ownership
RUN chown -R appuser:appgroup /app

# Switch to non-root user
USER appuser

# Expose the port
EXPOSE 8080

# Set the startup command with JVM options optimized for containers
ENTRYPOINT ["java", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-XX:InitialRAMPercentage=50.0", \
  "-jar", "/app/app.jar"]