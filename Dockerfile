FROM openjdk:17

WORKDIR /app

COPY target/transactionstats-api.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
