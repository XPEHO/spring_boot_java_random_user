# ---- Stage 1: Build ----
# Uses the full JDK image to compile the application with Maven
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# Copy Maven wrapper and pom.xml first to leverage Docker layer caching
# Dependencies are downloaded only when pom.xml changes
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy source code and build the JAR (tests are skipped as they run in CI)
COPY src src
RUN ./mvnw package -DskipTests -B

# ---- Stage 2: Run ----
# Uses a lightweight JRE-only image for a smaller and more secure final image
FROM eclipse-temurin:25-jre
WORKDIR /app

# Create a non-root user and group for running the application securely
RUN groupadd --system appgroup && useradd --system --gid appgroup appuser

# Copy the built JAR from the build stage using a stable pattern so version changes do not break the image build
COPY --from=build /app/target/*.jar app.jar

# Ensure the non-root user owns the application files
RUN chown -R appuser:appgroup /app

# Switch to the non-root user
USER appuser

# Document the port the application listens on
EXPOSE 8080

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
