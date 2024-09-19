import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

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
   * 删除token
   */
  function removeToken() {
    localStorage.removeItem('auth')
  }

  return {
    token,
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

