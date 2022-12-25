<script setup lang="ts">
import {ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const email = ref("");
const username = ref("");
const password = ref("");
const phone = ref("");

const router = useRouter();

const user = function () {
  axios.post('api/v1/user',{
    email: email.value,
    username: username.value,
    password: password.value,
    phone: phone.value,
  })
  .then(() => {
    router.replace({name: "home"})
  });
}

</script>

<template>
  <div class="mt-2">
    <el-input v-model="email" placeholder="이메일을 입력해주세요"/>
  </div>

  <div class="mt-2">
    <el-input v-model="username" placeholder="유저이름을 입력해주세요"/>
  </div>

  <div class="mt-2">
    <el-input v-model="password" placeholder="비밀번호를 입력해주세요"/>
  </div>

  <div class="mt-2">
    <el-input v-model="phone" placeholder="핸드폰 번를을 입력해주세요"/>
  </div>

  <div class="mt-2">
    <el-button type="primary" @click="user">작성 완료</el-button>
  </div>

</template>≈