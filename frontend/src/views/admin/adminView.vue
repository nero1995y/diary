<script setup lang="ts">
import axios from "axios";
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();
</script>

<template>
  <div>
    <p class="text-large font-600 mr-3"> 관리자 페이지 </p>
    <router-link to="/users"> 유저 목록 </router-link>
  </div>
</template>

<style scoped lang="scss">
</style>
