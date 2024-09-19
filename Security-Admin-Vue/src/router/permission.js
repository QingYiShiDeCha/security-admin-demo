import { useAuthStore } from '@/stores/auth'
import { useUserStore } from '@/stores/user'
import { useMenuStore } from '@/stores/menu'

const WHITE_LIST = ['/login']

export function setPermissionGuard(router) {
  router.beforeEach((to, from, next) => {
    const { token } = useAuthStore()

    if (token) {
      console.log('有token 放行')
      next()
    } else {
      if (WHITE_LIST.includes(to.path)) {
        console.log('白名单 放行')
        next()
      } else {
        console.log('回到登录页')
        next('/login')
      }
    }

  })
}

