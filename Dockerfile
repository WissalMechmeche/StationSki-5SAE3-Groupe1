# Use the official OpenJDK 17 image from the Docker Hub
FROM openjdk:17

# Create a directory to hold the application JAR file
RUN mkdir -p /app

# Copy the JAR package into the image
ARG JAR_FILE=target/gestion-station-skii-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app/gestion-station-skii.jar

# Expose the application port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "/app/gestion-station-skii.jar"]