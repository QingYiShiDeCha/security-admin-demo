import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useAuthStore = defineStore('auth', () => {
  const token = ref('')
  const isLogin = ref(false)
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
    isLogin.value = true
  }

  /**
   * 删除token
   */
  function removeToken() {
    token.value = null
    isLogin.value = false
  }

  return {
    token,
    isLogin,
    getToken,
    setToken,
    removeToken
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

