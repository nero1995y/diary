<script setup lang="ts">
import { ref } from 'vue'
const input = ref('');

</script>
<template>
  <el-input v-model="input" placeholder="검색 내용" />
</template>

<style scoped>
</style>