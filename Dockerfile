FROM openjdk:17

# Copy the JAR package into the image
ARG JAR_FILE=target/gestion-station-skii-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app/gestion-station-skii.jar

# Expose the application port
EXPOSE 8081

# Run the App
ENTRYPOINT ["java", "-jar", "/app/gestion-station-skii.jar"]