<template>
    <div>
        <el-form
                :model="LoginForm"
                :rules="LoginRules"
                v-loading="loading"
                element-loading-text="正在登陆中"
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)"
                ref="LoginRef"
                label-width="100px"
                class="loginContainer">
            <h3 class="loginTitle">系统登录</h3>
            <el-form-item label="用户名称" prop="username">
                <el-input size="normal" type="text" v-model="LoginForm.username" auto-complete="off"
                          placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item label="用户密码" prop="password">
                <el-input size="normal" type="password" v-model="LoginForm.password"
                          auto-complete="off" placeholder="请输入密码" @keydown.enter.native="submitClick"></el-input>
            </el-form-item>
            <el-checkbox size="normal" v-model="checked" class="LoginRemember">记住我</el-checkbox>
            <el-button size="normal" type="primary" style="width: 100%;" @click="submitClick">登录</el-button>
        </el-form>
    </div>
</template>

<script>

    export default {
        name: "Login",
        data() {
            return {
                loading: false,
                LoginForm: {
                    username: 'admin',
                    password: '123'
                },
                checked: true,
                LoginRules: {
                    username: [{required: true, message: '请输入用户名', trigger: true}],
                    password: [{required: true, message: '请输入密码', trigger: true}]
                }
            }
        },
        methods: {
            submitClick() {
                this.$refs.LoginRef.validate((valid) => {
                    if (valid) {
                        this.loading = true;
                        this.postKeyValueRequest('/doLogin', this.LoginForm).then(res => {
                            this.loading = false;
                            if (res) {
                                this.$store.commit("INIT_CURRENTHR", res.obj)
                                // 将用户信息存储到sessionStorage存储空间中
                                window.sessionStorage.setItem("user", JSON.stringify(res.obj))
                                // 用户是否在登录之前输入过链接,如果输入过链接则登录完毕跳转到那个页面
                                let path = this.$route.query.redirect;
                                // 如果传递过来的参数为 "/" 或者 undefined 则跳转到 home 页面,否则就跳转到path链接
                                this.$router.replace((path == "/" || path == undefined) ? "/home" : path)
                            }
                        })
                    } else {
                        this.$message.error('用户名或密码不可为空')
                        return false;
                    }
                })
            }
        }
    }
</script>

<style scoped>
    .loginContainer {
        border-radius: 15px;
        background-clip: padding-box;
        margin: 180px auto;
        width: 350px;
        padding: 15px 35px 15px 35px;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
    }

    .loginTitle {
        margin: 15px auto;
        text-align: center;
        color: #505458;
    }

    .LoginRemember {
        text-align: left;
        margin: 0 0 35px 0;
    }
</style>