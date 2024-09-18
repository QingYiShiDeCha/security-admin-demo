<template>
  <div class="app-wrapper">
    <el-container>
      <el-aside width="200px" :style="{ height: `${height}px`, minHeight: `${minHeight}px` }"
        class="sidebar-container">Asider</el-aside>
      <el-container>
        <el-header>Header</el-header>
        <el-main>Main</el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
const height = ref(0)
const minHeight = ref(0)

function getHeight() {
  height.value = document.documentElement.clientHeight
}

onMounted(() => {
  getHeight()
  minHeight.value = document.documentElement.clientHeight
  window.addEventListener('resize', getHeight)
})
</script>

<style lang="scss" scoped>
.app-wrapper {
  position: relative;
  width: 100%;
  height: 100%;
}

.sidebar-container {
  background-color: #2d3a4b;
  // height: 100%;
}

.el-container {
  height: 100%
}

.el-header {
  padding-left: 0px;
  padding-right: 0px;
}

:deep(ul.el-menu) {
  border-right-width: 0px
}
</style>