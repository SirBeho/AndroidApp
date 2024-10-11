import Vue from 'nativescript-vue'
import Login from './components/Login.vue';
import Home from './components/Home.vue';
import config from './config';

Vue.prototype.$config = config;
Vue.config.silent = false;

new Vue({
  render: (h) => h('frame', [h(Login)]),
}).$start();