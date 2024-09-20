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

  /**
   * remove userInfo
   */
  function removeUserInfo() {
    userInfo.value = null
  }

  return {
    userInfo,  // Directly expose the reactive ref
    setUserInfo,
    removeUserInfo
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
