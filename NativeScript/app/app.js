import Vue from 'nativescript-vue'
import Login from './components/Login.vue';
import Home from './components/Home.vue';
import * as https from 'nativescript-https';

Vue.config.silent = false;


new Vue({
  render: (h) => h('frame', [h(Home)]),
}).$start()
