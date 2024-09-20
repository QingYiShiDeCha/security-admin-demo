import { useAuthStore } from '@/stores/auth'
import { useUserStore } from '@/stores/user'
import { useMenuStore } from '@/stores/menu'
import { logout } from '@/api/auth'
import { ElMessage } from 'element-plus'

const WHITE_LIST = ['/login']

export function setPermissionGuard(router) {
  router.beforeEach(async (to, from, next) => {
    const { token, removeToken } = useAuthStore()
    const { hasRoutes, menuList, removeMenuStore, setHasRoutes } = useMenuStore()
    const { removeUserInfo } = useUserStore()
    console.log('hasRoutes--->', hasRoutes)
    console.log('token--->', token)
    console.log('toMatch', to)
    if (to.path === '/login') {
      removeToken()
      removeMenuStore()
      removeUserInfo()
    }

    if (token) {
      console.log('有token 放行')
      if (!hasRoutes) {
        console.log('menuList--->', menuList) // 检查 menuList 是否为空
        setDyniamicRoutes(router, menuList)
        setHasRoutes(true)
        next({ path: to.fullPath }) // 确保新路由生效后跳转到目标路由
      }

      if (to.matched.length === 0) {
        console.log('没有匹配值')   // 目前执行到这
      }

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

/**
 * 设置动态路由
 * 
 * @param {{}} router 路由实例
 * @param {[]} routes 路由数组
 */
function setDyniamicRoutes(router, routes) {
  console.log('router--->', router)
  let newRoutes = router.options.routes
  routes.forEach(item => {
    if (item.children) {
      item.children.forEach(child => {
        const route = mapRoute(child, item.name)
        if (route) {
          newRoutes[0].children.push(route)
        }
      })
    }
  })

  // 重新添加到路由
  newRoutes.forEach(route => {
    router.addRoute(route)
  })
}

/**
 * 菜单映射路由
 * @param {{}} menu 菜单
 * @param {string} parentName 父节点名称  
 */
function mapRoute(menu, parentName) {
  if (!menu.component) {
    return null;
  } else {
    const route = {
      name: menu.name,
      path: menu.path,
      component: () => import(/* @vite-ignore */`../views/${menu.component}.vue`),
      meta: {
        parentName: parentName,
      }
    }
    console.log('Added route:', route)  // 检查是否生成正确的路由
    return route;
  }
}

