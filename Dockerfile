FROM openjdk:8-jdk-slim AS build
COPY . /app
USER root
WORKDIR /app
RUN ./mvnw install

FROM openjdk:8-jre-slim
COPY --from=build /app/target/*.jar /app/learningToGrow.jar
WORKDIR /app
CMD "java" "-jar" "learningToGrow.jar"