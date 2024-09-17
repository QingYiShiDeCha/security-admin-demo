import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/layout/index.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Layout
    },
    {
      path: '/login',
      name: 'login',
      meta: { title: '登录页' },
      component: () => import('@/views/Login/index.vue')
    }
  ]
})

export default router
