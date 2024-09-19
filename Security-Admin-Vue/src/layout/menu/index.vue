<template>
  <div class="app-container">
    <el-menu text-color="#fafafa" background-color="#2d3a4b" default-active="/index" router>
      <el-menu-item index="/index">
        <SvgIcon icon-class="dashboard" style="margin-right: 14px;" />
        <span>首页</span>
      </el-menu-item>

      <template v-for="item in menuList" :key="item.id">
        <el-sub-menu v-if="item.children && item.children.length > 0" :index="item.path">
          <template #title>
            <SvgIcon :icon-class="item.icon" style="margin-right: 14px;" />
            <span>{{ item.name }}</span>
          </template>
          <el-menu-item v-for="child in item.children" :key="child.id" :index="child.path">
            <SvgIcon :icon-class="child.icon" style="margin-right: 14px;" />
            <span>{{ child.name }}</span>
          </el-menu-item>
        </el-sub-menu>
        <el-menu-item v-else :index="item.path">
          <SvgIcon :icon-class="item.icon" style="margin-right: 14px;" />
          <span>{{ item.name }}</span>
        </el-menu-item>
      </template>
    </el-menu>
  </div>
</template>

<script setup>
import { useMenuStore } from '@/stores/menu'
import { ref } from 'vue'
const menuStore = useMenuStore()

const menuList = ref(menuStore.getMenuList)
console.log(menuList.value)

</script>

<style lang="scss" scoped></style>