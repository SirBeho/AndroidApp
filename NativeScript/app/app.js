import Vue from 'nativescript-vue'
import Login from './components/Login.vue';
import Home from './components/Home.vue';
import * as https from 'nativescript-https';
import store, { fetchUsers } from './store/store'; // Importa el store y la función


Vue.prototype.$store = store; 
Vue.config.silent = false;


new Vue({
  render: (h) => h(Home),
  async created() {
      // Carga los usuarios al iniciar la app
      await fetchUsers();
  }
}).$start();