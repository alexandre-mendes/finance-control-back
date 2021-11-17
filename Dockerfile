FROM maven:3.8.1-openjdk-11-slim AS build
COPY /src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

FROM openjdk:11-jdk-slim
EXPOSE 8080
COPY --from=build app/target/*.jar app.jar
ENTRYPOINT ["ls"]