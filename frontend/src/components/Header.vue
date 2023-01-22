<template>
  <el-header class="header">
    <el-menu mode="horizontal" router >
      <el-menu-item index="/">Home</el-menu-item>
      <el-menu-item index="/diary">다이어리 작성</el-menu-item>
      <el-menu-item index="/login">로그인</el-menu-item>
      <el-menu-item index="/diaryList">다이어리 페이지</el-menu-item>
      <el-menu-item index="/admin">관리자 페이지</el-menu-item>
    </el-menu>
  </el-header>
</template>

<script setup lang="ts">

</script>

<style scoped>
  .header {
    padding: 0;
  }
</style>