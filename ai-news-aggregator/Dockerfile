# BUILD STAGE
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy project files
COPY . .

# Build jar inside container
RUN mvn clean package -DskipTests

# RUNTIME STAGE
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy built jar from build stage
COPY --from=build /app/target/*SNAPSHOT.jar app.jar

# Render provides PORT env variable
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]