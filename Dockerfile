# Configured Dockerfile
FROM openjdk:21-jdk-slim AS build
LABEL maintainer="940pp"
WORKDIR /app
COPY target/demoJournalApp-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
