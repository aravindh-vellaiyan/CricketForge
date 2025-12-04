# ---------- Stage 1: Build ----------
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Maven wrapper instead of using heavy Maven base image
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src ./src

# Use the wrapper to download only what Maven actually needs
RUN ./mvnw -B -DskipTests clean package

# ---------- Stage 2: Runtime ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy only the built artifact
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]