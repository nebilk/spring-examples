FROM openjdk:8-jdk-alpine AS build
EXPOSE 8081
VOLUME /spring-security-jwt
ARG JAR_FILE=target/spring-security-jwt-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /spring-security-jwt.jar
ENTRYPOINT ["java", "-jar", "/spring-security-jwt.jar"]