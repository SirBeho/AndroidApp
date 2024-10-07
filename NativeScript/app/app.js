import Vue from 'nativescript-vue'
import Login from './components/Login.vue';
import Home from './components/Home.vue';
import * as https from 'nativescript-https';
 import store, { fetchUsers } from './store/store'; 

 import HomePage from './components/HomePage';

Vue.prototype.$store = store;
Vue.config.silent = false;


new Vue({
  render: (h) => h(HomePage),
}).$start();