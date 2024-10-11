# Proyecto Task Manager
### <span style="display: inline-flex; align-items: center;gap:7px"><img src="https://simpleskill.icons.workers.dev/svg?i=vue.js" height="29" width="29" style="border-radius: 20%; border:solid 4px #42b883;padding:3px;padding-bottom:1px"> <img src="https://simpleskill.icons.workers.dev/svg?i=quarkus,eclipsevertdotx,nativescript" height="40"> <img src="https://simpleskill.icons.workers.dev/svg?i=mysql" height="35" style="border-radius: 20%; background-color: lightgray;padding:2px;">  <img src="https://skillicons.dev/icons?i=docker,firebase" height="40">   <img src="https://avatars.githubusercontent.com/u/130129149?v=4" height="38" style="border-radius: 20%" ></span>
  </span>



## Descripción del Proyecto

El proyecto consta de cuatro componentes principales:

> 1. **Frontend en NativeScript con Vue.js**: Una aplicación móvil desarrollada con NativeScript y Vue.js que sirve como interfaz para los usuarios. Permite interactuar con los datos y visualizar información relevante proveniente del backend.

> 2. **API en Quarkus**: Esta API disponible en el puerto `8080` proporciona acceso a los datos almacenados en la base de datos MySQL. Está diseñada para ser rápida y eficiente, permitiendo la consulta y administración de la información utilizada en el sistema.

> 3. **API en Vert.x**: Esta API disponible en el puerto `8888`  se encarga de procesar las tareas de forma paralela, aprovechando la naturaleza reactiva de Vert.x. Ha sido desplegada utilizando Docker para facilitar su portabilidad y escalabilidad.

> 4. **Base de Datos MySQL**: Almacena todos los datos críticos, incluyendo tareas, usuarios, configuraciones y demás información necesaria para la operación del sistema.


## Características

- **Gestión de Tareas en Paralelo**: Permite ejecutar varias tareas simultáneamente, maximizando el uso de los recursos del sistema.
- **Comunicación en Tiempo Real**: Uso de WebSockets para proporcionar actualizaciones inmediatas sobre el estado de las tareas.
- **API REST**: Endpoints bien definidos para interactuar con el backend de manera eficiente.

## Tecnologías Utilizadas

### <span style="display: inline-flex; align-items: center;gap:10px"><img src="https://simpleskill.icons.workers.dev/svg?i=nativescript" height="40">  Frontend</span>

- **<span style="display: inline-flex; align-items: center;gap:10px">
 <img src="https://simpleskill.icons.workers.dev/svg?i=vue.js" height="20">  NativeScript con Vue.js 2.9.3** </span>

  - Framework para el desarrollo de aplicaciones móviles multiplataforma.
  - Proporciona una interfaz de usuario reactiva y moderna para interactuar con el backend.


### <span style="display: inline-flex; align-items: center;gap:10px"><img src="https://skillicons.dev/icons?i=java" height="40">  Backend</span>

- **<span style="display: inline-flex; align-items: center;gap:10px">
 <img src="https://simpleskill.icons.workers.dev/svg?i=quarkus" height="20">  Quarkus 3.15.1** </span>
  - Framework Java nativo en la nube diseñado para ejecutarse de manera rápida y eficiente.
  - Proporciona una API REST que facilita el acceso a los datos almacenados en la base de datos MySQL.

- **<span style="display: inline-flex; align-items: center;gap:10px">
 <img src="https://cdn-icons-png.flaticon.com/512/136/136525.png
 " height="20">  Jackson 3.15.1** </span>
  - Utilizado para la serialización y deserialización de objetos JSON, facilitando la comunicación entre la API y el frontend.

- **<span style="display: inline-flex; align-items: center;gap:10px">
 <img src="https://simpleskill.icons.workers.dev/svg?i=Hibernate" height="20">  Hibernate 3.15.1** </span>
  - Utilizado para mapear las entidades Java a tablas de la base de datos, permitiendo una interacción más sencilla con MySQL.


### <span style="display: inline-flex; align-items: center;gap:10px"><img src="https://skillicons.dev/icons?i=java" height="40">  API</span>


- **<span style="display: inline-flex; align-items: center;gap:10px">
 <img src="https://simpleskill.icons.workers.dev/svg?i=eclipsevertdotx" height="20">  Vert.x 4.4.5** </span>

  - Toolkit reactivo basado en Java para la creación del backend.
  - Utilizado para manejar tareas intensivas en paralelo y proporcionar comunicación eficiente entre componentes.

- **<span style="display: inline-flex; align-items: center;gap:10px">
 <img src="https://dt-cdn.net/hub/connection-pools_r9Q4U4J.png" height="20"> C3P0 0.9.5.5** </span>
  - Librería para la gestión del pool de conexiones a la base de datos.
  - Facilita el manejo eficiente de conexiones a MySQL.

- **<span style="display: inline-flex; align-items: center;gap:10px">
 <img src="https://cdn-icons-png.flaticon.com/512/136/136525.png
 " height="20">  Jackson Databind 2.15.0** </span>
  - Utilizado para el mapeo entre objetos Java y JSON en la API de Vert.x, permitiendo una comunicación fluida y sencilla con los clientes.



### <span style="display: inline-flex; align-items: center;gap:10px"><img src="https://cdn-icons-png.flaticon.com/512/9850/9850774.png" height="40">  Base de Datos</span>

- **<span style="display: inline-flex; align-items: center;gap:10px">
 <img src="https://simpleskill.icons.workers.dev/svg?i=mySQL" height="30"> MySQL** </span>

  - Base de datos relacional utilizada para almacenar información sobre tareas, usuarios, y demás elementos críticos del sistema.
  - Proporciona almacenamiento seguro y eficiente para los datos requeridos por las APIs.

## Otras Tecnologías Utilizadas


- **<span style="display: inline-flex; align-items: center;gap:10px">
 <img src="https://skillicons.dev/icons?i=firebase" height="30"> Firebase Cloud Messaging (FCM)** </span>

  - Utilizado para enviar notificaciones push a la aplicación móvil cuando las tareas son completadas.

- **<span style="display: inline-flex; align-items: center;gap:10px">
 <img src="https://avatars.githubusercontent.com/u/130129149?v=4" height="30" style="border-radius: 20%" > WebSockets** </span>
  - Proporcionan una comunicación bidireccional y en tiempo real entre el backend y el cliente, para las actualizaciones del estado de las tareas.

- **<span style="display: inline-flex; align-items: center;gap:10px">
 <img src="https://simpleskill.icons.workers.dev/svg?i=Docker" height="30"> Docker** </span>

  - Plataforma para empaquetar y desplegar aplicaciones en contenedores.
  - Utilizada para desplegar la API Vert.x, permitiendo una implementación consistente en cualquier entorno.

## Dependencias y Tecnologías Adicionales

Para conocer todas las tecnologías y dependencias utilizadas en el proyecto, consulta los archivos `pom.xml` y `package.json` correspondientes a los componentes desarrollados en Java y JavaScript, respectivamente. Estos archivos contienen información detallada sobre las librerías, versiones y configuraciones necesarias para la construcción del proyecto.

## Instalación

1. **Clonar el repositorio**
   ```bash
   git clone <URL_del_repositorio>
   ```

2. **Construir las imágenes de Docker**
   ```bash
   docker build -t vertex-app ./vertex
   ```

3. **Ejecutar las aplicaciones con Docker**
   ```bash
   docker run -p 8888:8888 vertex-app
   ```



## Configuración

El proyecto se ejecuta en los puertos `8080` (Quarkus) y `8888` (Vert.x) por defecto, pero se puede cambiar esta configuración en los archivos de configuración correspondientes.

Asegúrate de tener configurado el servidor MySQL correctamente y modificar las credenciales en las aplicaciones según sea necesario.

## Despliegue

El backend puede desplegarse utilizando Docker en cualquier servidor compatible con contenedores, como AWS o Digital Ocean. Las instrucciones de configuración y despliegue detalladas se encuentran en la documentación del proyecto.

## Contribución

Las contribuciones son bienvenidas. Por favor, sigue los siguientes pasos:

1. Haz un fork del proyecto.
2. Crea una rama para tu nueva característica (`git checkout -b feature/nueva-caracteristica`).
3. Realiza los cambios y haz commit (`git commit -m 'Agregada nueva característica'`).
4. Envía un pull request.

## Licencia

Este proyecto está hecho completamente por Ing. Benjamin Tavarez .

## Contacto

Para cualquier duda o sugerencia, puedes contactar al equipo de desarrollo a través del siguiente email: [benjamin.tavarez.98@gmail.com](mailto:benjamin.tavarez.98@gmail.com).