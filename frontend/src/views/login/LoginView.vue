<script setup lang="ts">
import {ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const email = ref("")
const password = ref("")

const router = useRouter();

const write = function () {
  axios.post('/api/auth/login',{
    email: email.value,
    password: password.value,
  })
      .then(() => {
        router.replace({name: "home"})
      });
}
</script>

<template>
  <div>
    <el-input v-model="email" placeholder="아이디"/>
  </div>
  <div class="mt-2">
    <el-input v-model="password" placeholder="패스워드"/>
  </div>

  <div class="mt-2">
    <el-button type="primary" @click="write"> 로그인 </el-button>
  </div>
</template>

<style scoped lang="scss">
</style>
