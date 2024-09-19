import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useMenuStore = defineStore('menu', () => {
  const menuList = ref([])

  const getMenuList = computed(() => menuList.value)

  function setMenuList(vals) {
    menuList.value = vals
  }

  return {
    menuList,
    getMenuList,
    setMenuList
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
