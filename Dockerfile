# Use the official OpenJDK 17 image as the base
FROM openjdk:17

# Set the working directory (optional but good practice)
WORKDIR /app

# Expose the port the application will run on
EXPOSE 8089

# Copy the JAR file from the target directory to the working directory in the container
COPY target/gestion-station-ski-1.0.jar app.jar

# Set the entrypoint to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
