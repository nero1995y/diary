<script setup lang="ts">
import {defineProps, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const name = ref("");
const content = ref("");
const username = ref("");

const router = useRouter();

const props = defineProps({
  userName: {
    type: String,
    default: "SIM JH"
  }
})
const write = function () {
  axios.post('/api/v1/diary',{
    name: name.value,
    content: content.value,
    username: props.userName
  })
  .then(() => {
    router.replace({name: "home"})
  });
}
</script>

<template>
  <p>세션 ID: {{props.userName}}</p>
  <div>
    <el-input v-model="name" placeholder="제목을 입력해주세요"/>
  </div>
  <div class="mt-2">
    <el-input v-model="content" type="textarea" rows="15"/>
  </div>


  <div class="mt-2">
    <el-button type="primary" @click="write"> 작성 완료</el-button>
  </div>

</template>≈