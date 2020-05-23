import Vue from 'vue'
import Vuex from 'vuex'
import {getRequest} from "../util/api";
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import {Notification} from 'element-ui';

Vue.use(Vuex)

const now = new Date();

const store = new Vuex.Store({
    state: {
        routes: [],
        sessions: {},
        currentSession: null, // 发送信息的对象（聊天列表中被点击的用户）
        currentHr: null, // 当前用户对象
        filterKey: '',
        hrs: [], // 用户列表
        stomp: null, // 连接对象
        isDot: {} // 用户列表标点 key:用户 value:是否拥有红点
    },
    mutations: {
        INIT_CURRENTHR(state, hr) {
            state.currentHr = hr
        },
        initRoutes(state, data) {
            state.routes = data;
        },
        changeCurrentSession(state, currentSession) {
            // 设置红点为false（红点消失）
            Vue.set(state.isDot, state.currentHr.username+'#'+currentSession.username, false)
            state.currentSession = currentSession;
        },
        // 点击发送消息之后触发的事件
        addMessage(state, msg) {
            // 在 state.sessions 中创建聊天对象记录数组（如果聊天对象记录数组存在则为引用）
            let mss = state.sessions[state.currentHr.username + '#' + msg.to];
            // 如果聊天记录为空则进行初始化
            if (!mss) {
                // state.sessions[state.currentHr.username +'#'+msg.to] = []
                Vue.set(state.sessions, state.currentHr.username + '#' + msg.to, [])
            }
            // 将发送的消息对象存储到对应数组的session中
            state.sessions[state.currentHr.username + '#' + msg.to].push({
                content: msg.content,
                date: new Date(),
                self: !msg.notSelf // 用作标识是否为自己发送的内容
            })
        },
        // 存储聊天内容
        INIT_DATA(state) {
            // TODO 浏览器本地的历史聊天记录可以在这里完成
            let data = localStorage.getItem('vue-chat-session');
            if (data) {
                state.sessions = JSON.parse(data);
            }
        },
        INIT_HR(state, data) {
            state.hrs = data
        }
    },
    actions: {
        connect(context) {
            // 连接服务端操作
            context.state.stomp = Stomp.over(new SockJS("/ws/ep"));
            console.log('链接中！');
            // 连接成功后的操作
            context.state.stomp.connect({}, success => {
                console.log('链接成功！' + success);
                // 订阅服务端的消息，以/user/queue/chat作为队列，给服务器发信息，服务器会监听此队列接收信息
                context.state.stomp.subscribe("/user/queue/chat", msg => {
                    // 获取服务端发送的消息 msg
                    let receiveMsg = JSON.parse(msg.body)
                    // 如果当前选中的用户不是消息的发送方
                    if (!context.state.currentSession || receiveMsg.from != context.state.currentSession.username) {
                        Notification.info({
                            title: '【' + receiveMsg.fromNickName + '】发来一条消息',
                            message: receiveMsg.content.length > 10 ? receiveMsg.content.substr(0, 10) : receiveMsg.content,
                            position: 'bottom-right'
                        });
                        // 标记红点
                        Vue.set(context.state.isDot, context.state.currentHr.username+'#'+receiveMsg.from, true)
                    }
                    receiveMsg.notSelf = true // 确定是当前角色发送的内容
                    receiveMsg.to = receiveMsg.from // 修改发送的对象
                    context.commit('addMessage', receiveMsg) // 发送消息
                })
            }, error => {
                console.log('链接失败！');
                console.log(error);
            })
        },
        initData(context) {
            context.commit('INIT_DATA')
            getRequest("/chat/hrs").then(res => {
                if (res) {
                    context.commit('INIT_HR', res)
                }
            })
        }
    }
})

// 当 sessions 值发送变化的时候会存储聊天信息
store.watch(function (state) {
    return state.sessions
}, function (val) {
    localStorage.setItem('vue-chat-session', JSON.stringify(val));
}, {
    deep: true/*这个貌似是开启watch监测的判断,官方说明也比较模糊*/
})


export default store;
