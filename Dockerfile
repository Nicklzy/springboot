FROM openjdk:8-jdk-alpine

RUN mkdir /app

WORKDIR /app

COPY target/spring-demo-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "spring-demo-0.0.1-SNAPSHOT.jar"]