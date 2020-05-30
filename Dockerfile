FROM maven:3.6-jdk-11-slim AS build
COPY src /app/src
COPY front /app/front
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package


FROM openjdk:11-jdk-slim
#ARG JAR_FILE=/app/target/bluemarlin2-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} bluemarlin.jar
COPY --from=build /app/target/bluemarlin2-0.0.1-SNAPSHOT.jar /
ENTRYPOINT ["java", "-jar", "/bluemarlin2-0.0.1-SNAPSHOT.jar"]