FROM openjdk:19

COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

EXPOSE 8087

ENTRYPOINT ["java", "-jar", "/api-gateway.jar"]
