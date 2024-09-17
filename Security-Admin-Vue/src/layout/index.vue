<template>
  <div>
    <el-button type="primary" @click="getUserList">接口测试</el-button>
  </div>
</template>

<script setup>
import { ListUser } from '@/api/user'
async function getUserList() {
  const res = await ListUser()
  console.log(res)
}

</script>

<style scoped></style>