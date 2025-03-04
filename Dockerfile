# Build Stage
FROM maven:3.9.6-amazoncorretto-21 AS build
WORKDIR /demo
COPY pom.xml /demo
COPY src /demo/src
RUN mvn -f pom.xml clean package

# Runtime Stage
FROM amazoncorretto:21.0.2-alpine3.19
COPY --from=build /demo/target/*.jar app.jar

# Expose ports for application and debugging
EXPOSE 8080 5005

# Correct ENTRYPOINT format
ENTRYPOINT java -Dspring.profiles.active=docker \
    -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 \
    -jar /app.jar
