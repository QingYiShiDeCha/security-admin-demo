import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useMenuStore = defineStore('menu', () => {
  const menuList = ref([])
  const hasRoutes = ref(false)

  const getMenuList = computed(() => menuList.value)

  /**
   * 设置hasRoutes
   * 
   * @param {boolean} value 
   */
  function setHasRoutes(value) {
    hasRoutes.value = value
  }

  function setMenuList(vals) {
    menuList.value = vals
  }

  function removeMenuStore() {
    menuList.value = null
    hasRoutes.value = false
  }

  return {
    menuList,
    getMenuList,
    setMenuList,
    removeMenuStore,
    hasRoutes,
    setHasRoutes
  }

}, {
  persist: {
    enabled: true,
    strategies: [
      {
        key: 'menu',
        storage: sessionStorage,
      }
    ]
  }
})
