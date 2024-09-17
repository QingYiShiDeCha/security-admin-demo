import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login } from '@/api/auth'

export const useAuthStore = defineStore('auth', () => {
  const token = ref('')
  /**
   * 获取token
   */
  const getToken = computed(() => token.value)
  /**
   * 设置token
   * 
   * @param {string} val token
   */
  function setToken(val) {
    token.value = val
  }

  /**
   * 登录方法
   * 
   * @param {{}} data 登录数据
   * @param {string} data.username 用户名
   * @param {string} data.password 密码
   */
  async function doLogin(data) {
    const res = await login(data)
    console.log('login res', res)
    setToken(res.token)
  }

  return {
    token,
    getToken,
    setToken,
    doLogin
  }
}, {
  persist: {
    enabled: true,
    strategies: [
      {
        key: 'auth',
        storage: localStorage
      }
    ]
  }
})

