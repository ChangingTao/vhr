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
    // 判断是否为登录页面
    if (to.path == '/') {
        next();
    } else {
        // 判断用户是否登录
        if (window.sessionStorage.getItem("user")) {
            initMenu(router, store);
            next();
        } else {
          // 重定向并赋值原本用户想前往的地址（但是因为没登录权限被重定向到登录页面了）
            next("/?redirect="+to.path);
        }
    }
})


Vue.config.productionTip = false

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
