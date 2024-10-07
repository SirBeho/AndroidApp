# Utiliza la imagen oficial de OpenJDK como base
FROM openjdk:17-jdk-slim

# Crea un directorio para la aplicación
WORKDIR /app

# Copia el archivo JAR (fat JAR) de tu aplicación al contenedor
COPY target/vertex-1.0.0-SNAPSHOT-fat.jar /app/mi-vertx-app.jar

# Exponer el puerto en el que la aplicación escucha (8888 en tu caso)
EXPOSE 8888

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/mi-vertx-app.jar"]
