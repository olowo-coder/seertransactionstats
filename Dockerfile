FROM maven:3.8.7-openjdk-18 as builder

ENV HOME=/opt/app

RUN mkdir $HOME

WORKDIR $HOME

ADD pom.xml ./pom.xml

RUN mvn dependency:go-offline

ADD . .

RUN mvn clean package

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/app/target/transactionstats-api.jar"]
