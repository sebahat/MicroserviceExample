FROM openjdk:17

COPY target/naming-server-0.0.1-SNAPSHOT.jar naming-server.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/naming-server.jar"]