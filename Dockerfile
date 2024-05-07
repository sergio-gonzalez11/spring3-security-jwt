
FROM openjdk:17-jdk-alpine

ARG PROFILE=dev

VOLUME /tmp

ARG JAR_FILE
COPY ${JAR_FILE} api.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "/api.jar"]