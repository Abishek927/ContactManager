FROM maven:3.8.4-openjdk-11-slim as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:11-jdk-slim
ARG JAR_FILE=/app/target/*.jar
COPY --from=build ${JAR_FILE} contact-manager.jar
ENTRYPOINT ["java","-jar","/contact-manager.jar"]
