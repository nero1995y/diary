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
  <el-row>
    <el-col :span="8">
    </el-col>
    <el-col :span="8">
      <el-card>
        <div class="card-header mb-4">
          <span>로그인</span>
        </div>
        <div>
          <el-input v-model="email" placeholder="아이디"/>
        </div>
        <div class="mt-2">
          <el-input v-model="password" placeholder="패스워드"/>
        </div>
        <div class="mt-2">
          <el-button type="success" @click="write"> 로그인 </el-button>
        </div>
        <div class="mt-2">
          <span>Don't have an account?</span>
          <router-link to="/user" class="sing-up">
            <span class="m-3">회원가입</span>
          </router-link>
        </div>
      </el-card>
    </el-col>
    <el-col :span="8">
    </el-col>

  </el-row>

</template>

<style scoped lang="scss">
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;

}
.sing-up {
  text-decoration-line: none;
  color: #e5687c;
}
</style>
