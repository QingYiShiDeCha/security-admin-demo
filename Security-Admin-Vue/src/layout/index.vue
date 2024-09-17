<template>
  <div>
    <el-col>
      <el-button type="primary" @click="getUserList">接口测试</el-button>
      <el-button type="primary" @click="doLogout">退出测试</el-button>
    </el-col>
  </div>
</template>

<script setup>
import { ListUser } from '@/api/user'
import { logout } from '@/api/auth'
import { useAuthStore } from '@/stores/auth'

const { removeToken } = useAuthStore()

async function getUserList() {
  const res = await ListUser()
  console.log(res)
}

async function doLogout() {
  const res = await logout()
  console.log('退出res', res)
  removeToken()
}

</script>

<style scoped></style>