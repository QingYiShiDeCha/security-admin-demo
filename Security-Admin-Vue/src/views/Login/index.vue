<template>
  <div class="login-page">
    <el-card class="login-card">
      <h2 class="login-title">用户登录</h2>
      <el-form :model="loginForm" class="login-form" ref="loginForm" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名">
            <template #prefix>
              <SvgIcon iconClass="user" />
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码">
            <template #prefix>
              <SvgIcon iconClass="password" />
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit" class="login-btn">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
// import SvgIcon from '@/components/svg-icon/index.vue'

const loginForm = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

const loginFormRef = ref(null)

const onSubmit = () => {
  loginFormRef.value.validate((valid) => {
    if (valid) {
      ElMessage.success('登录成功！')
      // 执行登录逻辑
    } else {
      ElMessage.error('请检查输入内容')
    }
  })
}
</script>

<style scoped>
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f7fa;
}

.login-card {
  padding: 40px;
  width: 400px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  background-color: #fff;
}

.login-title {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

.login-form {
  display: flex;
  flex-direction: column;
}

.login-btn {
  width: 100%;
}
</style>
