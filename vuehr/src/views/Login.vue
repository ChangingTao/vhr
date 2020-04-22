<template>
  <div>
    <el-form :model="LoginForm" :rules="LoginRules" ref="LoginRef" label-width="100px" class="loginContainer">
      <h3 class="loginTitle">系统登录</h3>
      <el-form-item label="用户名称" prop="username">
        <el-input type="text" v-model="LoginForm.username" auto-complete="off" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="用户密码" prop="password">
        <el-input type="password" v-model="LoginForm.password"
                  auto-complete="off" placeholder="请输入密码" @keydown.enter.native="submitClick"></el-input>
      </el-form-item>
      <el-checkbox v-model="checked" class="LoginRemember">记住我</el-checkbox>
      <el-button type="primary" style="width: 100%;" @click="submitClick" >登录</el-button>
    </el-form>
  </div>
</template>

<script>

  export default {
    name: "Login",
    data() {
      return {
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
            this.postKeyValueRequest('/doLogin', this.LoginForm).then(res =>{
              if (res){
                // 将用户信息存储到sessionStorage存储空间中
                window.sessionStorage.setItem("user", JSON.stringify(res.obj))
                this.$router.replace("/home")
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