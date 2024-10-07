# Proyecto Backend Vert.x con Docker

Este proyecto consiste en una aplicación backend desarrollada con Vert.x, que se ha dockerizado para ser desplegada de manera eficiente y portable. A continuación se describen las tecnologías y herramientas utilizadas, así como una guía para su ejecución.

## Tecnologías Utilizadas

1. **Java 17**
   - Lenguaje principal del proyecto.
   - Utilizado para implementar lógica de negocio con Vert.x.

2. **Vert.x 4.4.5**
   - Framework reactivo basado en Java para la creación de aplicaciones web y backend.
   - Utilizado para la gestión de peticiones HTTP y la lógica reactiva del servidor.

3. **Maven**
   - Herramienta de construcción y gestión de dependencias.
   - Utilizada para compilar el proyecto y empaquetar la aplicación en un archivo JAR.

4. **Docker**
   - Plataforma para empaquetar y desplegar aplicaciones en contenedores.
   - Utilizada para crear una imagen de la aplicación y ejecutarla de forma consistente en cualquier entorno.

5. **C3P0 (0.9.5.5)**
   - Librería para la gestión del pool de conexiones a la base de datos.
   - Utilizada para manejar las conexiones a la base de datos de manera eficiente.

6. **Jackson Databind 2.15.0**
   - Biblioteca para el mapeo entre objetos Java y JSON.
   - Utilizada para serializar y deserializar objetos Java en JSON y viceversa.

7. **Jackson JSR310 (Java Time Module)**
   - Módulo utilizado para soportar tipos de fecha y hora de Java 8 (como `LocalDateTime`) al trabajar con JSON.

8. **Firebase Cloud Messaging (FCM)**
   - Servicio de mensajería en la nube de Firebase.
   - Utilizado para enviar notificaciones push a la aplicación móvil cuando las tareas son completadas.

9. **NativeScript con Vue.js**
   - Framework para desarrollo de aplicaciones móviles multiplataforma.
   - Utilizado para desarrollar el frontend móvil que se comunica con el backend y recibe notificaciones push.

## Notificaciones Push
El backend está integrado con **Firebase Cloud Messaging (FCM)** para enviar notificaciones push a la aplicación móvil cuando una tarea es completada.

## Contribuir
Para contribuir al proyecto, realiza un *fork*, crea una rama con tus cambios y envía un *pull request*. Asegúrate de seguir las mejores prácticas de código y de documentar cualquier nueva funcionalidad.

## Licencia
Este proyecto está hecho completamente por (Benjamin Tavarez 1-192141).

