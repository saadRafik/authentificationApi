# Build Stage
FROM maven:3.9.6-amazoncorretto-21 AS build

# Copy necessary files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime Stage
FROM amazoncorretto:21.0.2-alpine3.19
COPY --from=build /target/*.jar app.jar

# Expose ports for the application
EXPOSE 8080 5005

# Start the application
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "/app.jar"]
