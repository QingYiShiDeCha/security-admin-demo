<template>
  <el-dropdown>
    <span class="el-dropdown-link">
      <el-avatar :src="userInfo.avatar" style="margin-right: 5px;" :size="40" alt="用户头像" />
      {{ userInfo.username }}
      <el-icon class="el-icon--right">
        <arrow-down />
      </el-icon>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item>个人中心</el-dropdown-item>
        <el-dropdown-item @click="handleLogout">安全退出</el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script setup>
import { ArrowDown } from '@element-plus/icons-vue'
import { logout } from '@/api/auth'
import { useUserStore } from '@/stores/user'
import { useMenuStore } from '@/stores/menu'
import { useAuthStore } from '@/stores/auth'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const { userInfo, removeUserInfo } = useUserStore()
const { removeMenuStore } = useMenuStore()
const { removeToken } = useAuthStore()

const router = useRouter()

function handleLogout() {
  logout().then(res => {
    console.log(res)
    removeUserInfo()
    removeMenuStore()
    removeToken()
    ElMessage.success(res.message)
    router.replace('/login')
  })
}

</script>

<style lang="scss" scoped>
.el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  outline: none;
  align-items: center;
}
</style>