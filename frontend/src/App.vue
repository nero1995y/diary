<script setup lang="ts">
import { RouterLink, RouterView } from "vue-router";
</script>

<template>
  <header>
      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/write">글 작성</RouterLink>
        <RouterLink to="/user">회원 가입</RouterLink>
      </nav>
  </header>

  <RouterView />
</template>

<style>

</style>
