import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const Login = () => import ('@/views/Login')
const Home = () => import ('@/views/Home')
const test1 = () => import ('@/views/homeChildrenCmp/test1')
const test2 = () => import ('@/views/homeChildrenCmp/test2')
const FriendChat = () => import('@/views/chat/FriendChat')

const routes = [
  {path: '/', name: 'Login', component: Login, hidden: true},// hidden:隐藏标识，用于标记，当hidden不为true时...
  {path: '/home', name: 'Home', component: Home, hidden: true},
  {
    path: '/home',
    name: '导航栏',
    component: Home,
    children: [{path: '/test1', name: '选项一', component: test1}, {path: '/test2', name: '选项二', component: test2},{path: '/chat', name: '在线聊天', component: FriendChat, hidden: true},]
  },

]

const router = new VueRouter({
  routes,
  // mode: 'history'
})

export default router
