import Vue from 'nativescript-vue'
import Login from './components/Login.vue';
import Home from './components/Home.vue';
import * as https from 'nativescript-https';
import store, { fetchUsers } from './store/store'; 
import { firebase } from "@nativescript/firebase";
import { messaging } from "@nativescript/firebase/messaging"; 

Vue.prototype.$store = store; 
Vue.config.silent = false;



// Inicializar Firebase
firebase().initializeApp().then(() => {
  console.log("Firebase initialized");

  // Solicitar permisos para notificaciones
  messaging().requestPermission().then((permissionStatus) => {
    console.log("Permission granted: ", permissionStatus);

    // Obtener token de FCM
    return messaging().getToken();
  }).then((token) => {
    console.log("FCM Token: ", token);
    // Aquí puedes enviar el token al backend
  }).catch((error) => {
    console.error("Error during Firebase setup: ", error);
  });

  // Manejar mensajes recibidos
  messaging().onMessage((message) => {
    console.log("Message received: ", message);
    // Puedes mostrar la notificación o manejar el mensaje como desees
  });

}).catch((error) => {
  console.error("Firebase initialization error: ", error);
});


new Vue({
  render: (h) => h(Home),
  async created() {
      // Carga los usuarios al iniciar la app
      await fetchUsers();
  }
}).$start();