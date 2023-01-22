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
  axios.post('api/v1/user', {
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
  <el-row>
    <el-col :span="8"></el-col>

    <el-col :span="8">
      <el-card>
        <div class="card-header mb-4">
          <span>회원 가입</span>
          <div class="mt-2">
            <el-input v-model="email" placeholder="이메일"/>
          </div>

          <div class="mt-2">
            <el-input v-model="username" placeholder="닉네임"/>
          </div>

          <div class="mt-2">
            <el-input v-model="password" placeholder="비밀번호"/>
          </div>

          <div class="mt-2">
            <el-input v-model="phone" placeholder="핸드폰 번호"/>
          </div>

          <div class="mt-2">
            <el-button type="primary" @click="user">작성 완료</el-button>
          </div>
        </div>
      </el-card>
    </el-col>
    <el-col :span="8"></el-col>
  </el-row>


</template>≈