FROM openjdk:13-ea-19-alpine AS build
COPY . /app
WORKDIR /app
RUN chmod +x mvnw && ./mvnw install

FROM openjdk:13-ea-19-alpine
COPY --from=build /app/target/*.jar /app/learningToGrow.jar
WORKDIR /app
CMD "java" "-jar" "learningToGrow.jar"