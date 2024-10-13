# Etapa de compilación
FROM eclipse-temurin:17-jdk AS build

# Establecer directorio de trabajo
WORKDIR /app

COPY target/quarkus-app/lib/ ./lib/
COPY target/quarkus-app/app/ ./app/
COPY target/quarkus-app/quarkus/ ./quarkus/
COPY target/quarkus-app/quarkus-run.jar ./
# Copiar archivos de Maven
COPY mvnw ./
COPY .mvn/ .mvn/
RUN chmod +x mvnw

# Copiar archivo de configuración de Maven y descargar dependencias
COPY pom.xml ./
RUN ./mvnw dependency:go-offline

# Copiar el código fuente
COPY src/ ./src/

# Compilar la aplicación
RUN ./mvnw clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copiar el JAR compilado desde la etapa anterior
COPY --from=build /app/target/quarkus-app/lib/ ./lib/
COPY --from=build /app/target/quarkus-app/app/ ./app/
COPY --from=build /app/target/quarkus-app/quarkus/ ./quarkus/
COPY --from=build /app/target/quarkus-app/quarkus-run.jar ./
COPY --from=build /app/target/quarkus-app/quarkus-app-dependencies.txt ./



# Exponer el puerto de la aplicación
EXPOSE 9999

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "quarkus-run.jar"]


#docker run -d -p 9999:9999 --name quarkus-api quarkus-api



