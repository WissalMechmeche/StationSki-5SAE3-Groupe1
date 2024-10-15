FROM openjdk:17
EXPOSE 8089
ADD target/GestionStationSki-0.0.1-SNAPSHOT.jar GestionStationSki.jar
ENTRYPOINT ["java", "-jar" , "GestionStationSki.jar"]