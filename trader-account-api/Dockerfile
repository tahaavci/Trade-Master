FROM openjdk:17 AS build
COPY target/trader-account-api.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]