import '@/assets/styles/border.css'
import '@/assets/styles/reset.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import pinaPluginPersist from 'pinia-plugin-persist'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
import 'virtual:svg-icons-register'
const app = createApp(App)
const pinia = createPinia()
pinia.use(pinaPluginPersist)
app.use(ElementPlus)
app.use(pinia)
app.use(router)
app.mount('#app')
