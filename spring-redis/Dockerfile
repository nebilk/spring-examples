FROM openjdk:8-jdk-alpine AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:8-jdk-alpine
WORKDIR spring-redis
COPY --from=build target/*.jar spring-redis.jar
ENTRYPOINT ["java", "-jar", "spring-redis.jar"]