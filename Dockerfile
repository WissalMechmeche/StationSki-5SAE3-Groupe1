FROM openjdk:17
EXPOSE 8089
ADD target/gestion-station-skii-0.0.1.jar GestionStationSki.jar
ENTRYPOINT ["java", "-jar", "GestionStationSki.jar"]
