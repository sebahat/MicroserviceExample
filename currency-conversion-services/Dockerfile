FROM openjdk:19

COPY target/currency-conversion-services-0.0.1-SNAPSHOT.jar currency-conversion.jar

EXPOSE 8100

ENTRYPOINT ["java", "-jar", "/currency-conversion.jar"]