<template>
  <div>
    <div class="login-container">
      <div style="width:350px" class="login-box">
        <div style="font-weight: bold; font-size: 24px; text-align: center; margin-bottom: 30px">登录</div>
        <el-form :model="data.form"  ref="formRef" :rules="rules" >
          <el-form-item prop="userName">
            <el-input prefix-icon="User" v-model="data.form.userName" placeholder="请输入账号"/>
          </el-form-item>
          <el-form-item prop="userPassword">
            <el-input show-password prefix-icon="Lock" v-model="data.form.userPassword" placeholder="请输入密码"/>
          </el-form-item>
          <el-form-item prop="role">
            <el-select style="width: 100%" v-model="data.form.role">
              <el-option value="ADMIN" label="管理员"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="login">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>>

<script setup>
import {reactive,ref} from "vue";
import request from "../utils/request";
import {ElMessage} from "element-plus";
import router from "../router";

const data = reactive({
  userName:'',
  userPassword:'',
  form: {
    userName:'',
    userPassword:'',
    role:'ADMIN'
  }
})
const rules = reactive({
  username: [
    {required: true, message: '请输入账号', trigger: 'blur'},
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
  ],
})

const formRef = ref()

const login = () => {
  formRef.value.validate((valid) => {
    if(valid){
      request.post('/user/login',{
        userName: data.form.userName,
        userPassword: data.form.userPassword
      }).then(res => {
        if(res.code === 0){
          localStorage.setItem('teacher-user',JSON.stringify(res.data))
          ElMessage.success('登录成功')
          router.push('/home')
        }else{
          ElMessage.error(res.msg)
        }
      })
    }
  })

}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: url("src/assets/imgs/bg.jpg");
  background-size: cover;

}
.login-box{
  background-color: rgba(255,255,255,.8);
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
  padding: 30px;
  border-radius: 10px;
}
</style>
