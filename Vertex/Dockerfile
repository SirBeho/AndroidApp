# Utiliza la imagen oficial de OpenJDK como base
FROM openjdk:17-jdk-slim

# Crea un directorio para la aplicación
WORKDIR /app

COPY target/vertex-1.0.0-SNAPSHOT-fat.jar /app/mi-vertx-app.jar

EXPOSE 8888

CMD ["java", "-jar", "/app/mi-vertx-app.jar"]
