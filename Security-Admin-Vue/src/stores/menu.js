import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useMenuStore = defineStore('menu', () => {
  const menuList = ref([])

  const getMenuList = computed(() => menuList.value)

  function setMenuList(vals) {
    menuList.value = vals
  }

  function removeMenuStore() {
    sessionStorage.removeItem('menu')
  }

  return {
    menuList,
    getMenuList,
    setMenuList,
    removeMenuStore
  }

}, {
  persist: {
    enabled: true,
    strategies: [
      {
        key: 'menu',
        storage: sessionStorage
      }
    ]
  }
})
