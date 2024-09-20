import { createRouter, createWebHistory } from 'vue-router'
import { setPermissionGuard } from './permission'
import Layout from '@/layout/index.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Layout',
      component: Layout,
      redirect: '/index',
      children: [
        {
          path: '/index',
          name: 'index',
          meta: {
            title: '首页'
          },
          component: () => import('@/views/home/index.vue')
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      meta: { title: '登录页' },
      component: () => import('@/views/Login/index.vue')
    }
  ]
})

setPermissionGuard(router)

export default router
