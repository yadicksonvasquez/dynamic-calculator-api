# Use a base image with Java 21
FROM openjdk:21

# Copy the JAR package into the image
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} dynamic-calculator-api-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 8080

# Run the App
ENTRYPOINT ["java", "-jar", "/dynamic-calculator-api-0.0.1-SNAPSHOT.jar"]