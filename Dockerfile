FROM maven:3.6-jdk-11-slim AS builder
COPY src /app/src
COPY front /app/front
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

FROM openjdk:11-jdk-slim
COPY --from=builder ./app/target/bluemarlin2-0.0.1-SNAPSHOT.jar ./
COPY --from=builder ./app/target/class/application-prod.properties ./
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "bluemarlin2-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT ["java", "-jar", "bluemarlin2-0.0.1-SNAPSHOT.jar"]