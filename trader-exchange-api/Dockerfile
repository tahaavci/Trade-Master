FROM openjdk:17 AS build
COPY target/trader-exchange-api.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]