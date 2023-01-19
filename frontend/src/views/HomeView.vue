<script setup lang="ts">
import axios from "axios";
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

const users = ref([]);
</script>

<template>

  <div>
    <p>관리자 전용 링크 </p>
    <router-link to="/users"> 유저 목록 </router-link>
    <P></P>
    <router-link to="/login"> 로그인 페이지</router-link>
  </div>

  <div class="bg-primary w-100 h-100">
    일기 화면ㄱㄷㄱㄷ
  </div>
</template>

<style scoped lang="scss">
</style>
