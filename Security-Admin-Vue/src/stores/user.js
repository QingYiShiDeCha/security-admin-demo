import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const userInfo = ref({})

  /**
   * 设置用户信息
   * 
   * @param {{}} values 用户信息
   */
  function setUserInfo(values) {
    userInfo.value = values
  }

  return {
    userInfo,  // Directly expose the reactive ref
    setUserInfo
  }
}, {
  persist: {
    enabled: true,
    strategies: [
      {
        key: 'user',
        storage: sessionStorage
      }
    ]
  }
})
