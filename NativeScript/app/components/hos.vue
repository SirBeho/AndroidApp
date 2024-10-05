<template>
    <Page>
      <ActionBar title="API Data" class="action-bar" />
      <StackLayout>
        <Button text="Obtener Datos" @tap="fetchData" />
  
        <!-- Lista que muestra los datos obtenidos de la API -->
        <ListView v-if="data.length > 0" for="item in data">
          <v-template>
            <Label :text="item.name" class="list-item" />
          </v-template>
        </ListView>
      </StackLayout>
    </Page>
  </template>
  
  <script>
  export default {
    data() {
      return {
        data: [] // Aquí se almacenarán los datos obtenidos
      };
    },
    methods: {
      fetchData() {
        const xhr = new XMLHttpRequest();
        const self = this;
  
        // Configuración de la solicitud
        xhr.open('GET', 'http://192.168.100.134:8080/hello', true);
        console.log('hol2a');
        // Configuración de headers (si es necesario)
        xhr.setRequestHeader('Content-Type', 'application/json');
  
        // Evento que se ejecuta cuando la solicitud está completa
        xhr.onload = function () {
          if (xhr.status >= 200 && xhr.status < 300) {
            // Aquí se maneja la respuesta
            self.data = JSON.parse(xhr.responseText);
            console.log(self.data);
          } else {
            console.error('Error en la solicitud:', xhr.statusText);
          }
        };
  
        // Manejo de errores
        xhr.onerror = function (error) {
          console.error('Error en la solicitud2', error);
        };
  
        // Envío de la solicitud
        xhr.send();
      }
    }
  };
  </script>
  
  <style scoped>
  .action-bar {
    background-color: #3b5998;
    color: white;
  }
  
  .list-item {
    padding: 10px;
    font-size: 18px;
    border-bottom-width: 1px;
    border-color: #ccc;
  }
  </style>
  