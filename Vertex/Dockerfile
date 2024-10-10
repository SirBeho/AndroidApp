# Usar la imagen oficial de Maven para compilar la aplicación
FROM maven:3.8.6-openjdk-17 AS build-stage

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml y el directorio src al contenedor
COPY pom.xml .
COPY src ./src

# Compilar el proyecto usando Maven
RUN mvn clean package

# Usar la imagen oficial de OpenJDK para ejecutar la aplicación
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR desde la etapa de compilación
COPY --from=build-stage /app/target/*.jar app.jar

# Exponer el puerto en el que la API estará disponible
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
