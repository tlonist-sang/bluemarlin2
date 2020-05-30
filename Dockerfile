FROM maven:3.6.0-jdk-11-alpine AS build
COPY src /app/src
COPY front /app/front
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

FROM openjdk:11-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} bluemarlin.jar
ENTRYPOINT ["java", "-jar", "/bluemarlin.jar"]