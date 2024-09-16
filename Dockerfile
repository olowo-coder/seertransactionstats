FROM openjdk:17-jre-slim
# Copy the jar file
COPY target/transactionstats-api.jar app.jar
# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
