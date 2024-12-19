FROM openjdk:21-jdk-slim
LABEL authors="940pp"
WORKDIR /app
# Copy the application JAR file into the container
COPY target/demoJournalApp-0.0.1-SNAPSHOT.jar app.jar
# Expose the port the application listens on (default is 8080)
EXPOSE 9090
# Run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
