<script setup lang="ts">
import { ref } from 'vue'
const input = ref('');

</script>


<template>
  <el-input v-model="input" placeholder="제목을 검색해주세요" />
</template>

<style scoped>

</style>