import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from "./store";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import {initMenu} from "./util/menus";
import 'font-awesome/css/font-awesome.min.css'

import {
  getRequest,
  postKeyValueRequest,
  deleteRequest,
  putRequest,
  postRequest
} from "./util/api";

Vue.prototype.postKeyValueRequest = postKeyValueRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.postRequest = postRequest;

Vue.use(ElementUI);

// 配置全局导航守卫
router.beforeEach((to, from, next) => {
  if(to.path == '/'){
    next();
  }else {
    initMenu(router, store);
    next();
  }
})


Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
