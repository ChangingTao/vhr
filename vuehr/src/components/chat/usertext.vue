<template>
    <div id="uesrtext">
        <textarea placeholder="按 Ctrl + Enter 发送" v-model="content" v-on:keyup="addMessage"></textarea>
    </div>
</template>

<script>
    import {mapState} from 'vuex'

    export default {
        name: 'uesrtext',
        data() {
            return {
                content: ''
            }
        },
        computed: mapState([
            'hrs',
            'currentSession'
        ]),
        methods: {
            addMessage(e) {
                if (e.ctrlKey && e.keyCode === 13 && this.content.length) {
                    // 如果用户没有选择发消息的对象则进行提示，不进行下面操作
                    if (!this.currentSession){
                        this.$message.error('请选择要发送信息的用户对象！！！')
                        return
                    }
                    // 创建一个对象，从里面放 接收方、消息的内容
                    let msgObj = new Object();
                    msgObj.to = this.currentSession.username; // 发送给xxx
                    msgObj.content = this.content
                    // 通过 stomp.send 来发送信息给服务器，参数：消息地址，消息优先级，消息的内容
                    this.$store.state.stomp.send("/ws/chat", {}, JSON.stringify(msgObj));
                    // 将发送信息的对象提交到vuex中
                    this.$store.commit('addMessage', msgObj);
                    this.content = '';
                }
            }
        }
    }
</script>

<style lang="scss" scoped>
    #uesrtext {
        position: absolute;
        bottom: 0;
        right: 0;
        width: 100%;
        height: 30%;
        border-top: solid 1px #DDD;

        > textarea {
            padding: 10px;
            width: 100%;
            height: 100%;
            border: none;
            outline: none;
        }
    }
</style>
