FROM maven:3.8.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/vertex-app-1.0-SNAPSHOT.jar /app/vertex-app.jar

EXPOSE 8888

CMD ["java", "-jar", "vertex-app.jar"]


#docker build -t vertx-api .

#docker run -d -p 8888:8888 --name vertx-api-container vertx-api

