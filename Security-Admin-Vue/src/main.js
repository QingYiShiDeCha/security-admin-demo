import '@/assets/styles/border.css'
import '@/assets/styles/reset.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import pinaPluginPersist from 'pinia-plugin-persist'

import ElementPlus from 'element-plus'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
import 'virtual:svg-icons-register'
const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

const pinia = createPinia()
pinia.use(pinaPluginPersist)
app.use(ElementPlus)
app.use(pinia)
app.use(router)
app.mount('#app')
