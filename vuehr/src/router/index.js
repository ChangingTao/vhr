import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const Login = () => import ('@/views/Login/Login')
const Home = () => import ('@/views/Home/Home')
const test1 = () => import ('@/views/Home/childrenCmp/test1')
const test2 = () => import ('@/views/Home/childrenCmp/test2')

const routes = [
  {path: '/', name: 'Login', component: Login, hidden: true},// hidden:隐藏标识，用于标记，当hidden不为true时...
  {path: '/home', name: 'Home', component: Home, hidden: true},
  {
    path: '/home',
    name: '导航栏',
    component: Home,
    children: [{path: '/test1', name: '选项一', component: test1}, {path: '/test2', name: '选项二', component: test2}]
  }
]

const router = new VueRouter({
  routes,
  mode: 'history'
})

export default router
