<template>
  <div class="login-page">
    <el-card class="login-card">
      <h2 class="login-title">用户登录</h2>
      <el-form :model="loginForm" class="login-form" ref="loginFormRef" :rules="rules" label-width="80px">
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
          <el-checkbox label="记住我" v-model="loginForm.remember" @change="onChange"></el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-button :loading="value" type="primary" @click="onSubmit" class="login-btn">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '@/stores/auth'
import { useMenuStore } from '@/stores/menu'
import { useRouter } from 'vue-router'
import { login } from '@/api/auth'
import { encrypt, decrypt } from '@/utils/jsencrypt'
import Cookies from 'js-cookie'
import { useToggle } from '@vueuse/core'

const [value, toggle] = useToggle()
const { setToken } = useAuthStore()
const { setMenuList } = useMenuStore()
const router = useRouter()
const loginForm = ref({
  username: '',
  password: '',
  remember: false
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
  toggle(true)
  loginFormRef.value?.validate(async (valid) => {
    if (valid) {

      if (loginForm.value.remember) {
        setCookit(loginForm.value)
      } else {
        removeCookit()
      }

      const res = await login(loginForm.value)
      if (res.code === 200) {
        setTimeout(() => {
          toggle(false)
        })
        setToken(res.data.token)
        setMenuList(res.data.menu)
        router.replace('/')
        ElMessage.success('登录成功')
      }
    } else {
      ElMessage.error('请检查输入内容')
    }
  })
}

/**
 * 勾选了记住密码存入cookit
 * 
 * @param {{}} form 信息
 * @param {string} form.username 用户名
 * @param {string} form.password 密码
 * @param {boolean} form.remember 记住我
 */
function setCookit(form) {
  Cookies.set('username', form.username, { expires: 30 })
  Cookies.set('password', encrypt(form.password), { expires: 30 })
  Cookies.set('remember', form.remember, { expires: 30 })
}

function removeCookit() {
  Cookies.remove('username')
  Cookies.remove('password')
  Cookies.remove('remember')
}

function getCookit() {
  const username = Cookies.get('username')
  const password = Cookies.get('password')
  const remember = Cookies.get('remember')
  loginForm.value = {
    username: username === undefined ? loginForm.value.username : username,
    password: password === undefined ? loginForm.value.password : decrypt(password),
    remember: remember === undefined ? false : Boolean(remember)
  }
}

const onChange = (val) => {
  console.log('val', val)
}

onMounted(() => {
  getCookit()
})

</script>

<style scoped lang="scss">
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #74ebd5, #acb6e5);

  .login-card {
    padding: 40px;
    width: 400px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
    border-radius: 10px;
    background-color: #ffffff;
    transition: transform 0.3s ease, box-shadow 0.3s ease;

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 15px 40px rgba(0, 0, 0, 0.2);
    }

    .login-title {
      text-align: center;
      font-size: 28px;
      font-weight: bold;
      margin-bottom: 30px;
      color: #333;
      border-bottom: 2px solid #f0f0f0;
      padding-bottom: 10px;
    }

    .login-form {
      display: flex;
      flex-direction: column;

      .el-form-item {
        margin-bottom: 25px;
      }

      .el-input__inner {
        border-radius: 5px;
        padding-left: 40px;
        transition: border-color 0.3s ease;

        &:hover {
          border-color: #409eff;
        }
      }

      .el-input__prefix {
        left: 10px;
        color: #888;
      }

      .login-btn {
        width: 100%;
        height: 45px;
        background-color: #409eff;
        border-color: #409eff;
        font-size: 18px;
        font-weight: bold;
        transition: background-color 0.3s ease, box-shadow 0.3s ease;

        &:hover {
          background-color: #66b1ff;
          box-shadow: 0 8px 15px rgba(0, 0, 0, 0.1);
        }
      }
    }

    .el-checkbox {
      margin-top: 10px;

      .el-checkbox__input.is-checked+.el-checkbox__label {
        font-weight: bold;
        color: #409eff;
      }
    }
  }
}
</style>
