import Vue from 'nativescript-vue'
import Login from './components/Login.vue';
import hos from './components/hos.vue';
import * as https from 'nativescript-https';

Vue.config.silent = false;


new Vue({
  render: (h) => h('frame', [h(hos)]),
}).$start()
