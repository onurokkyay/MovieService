
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:17-oracle
WORKDIR /app
COPY --from=build /app/target/movieservice-0.0.1-SNAPSHOT.jar movieservicedocker.jar
COPY src/main/resources/application.properties application.properties

ENV SPRING_DATA_MONGODB_URI=mongodburl
ENV SPRING_DATA_MONGODB_DATABASE=MovieServiceDatabase
ENV LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_DATA_MONGODB_CORE_MONGOTEMPLATE=DEBUG
ENV MOVIESERVICE_MOVIEAPIBEARERTOKEN=apiToken

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "movieservicedocker.jar"]
