<script setup lang="ts">
import {defineProps, onMounted, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const router = useRouter();

const user = ref({
  id:0,
  email: "",
  username: "",
  phone: "",
  password: "",
});

const props = defineProps({
  userId: {
    type: [Number, String],
    require: true,
  }
})

axios.get(`/api/v1/user/${props.userId}`)
    .then((response) => {
      user.value = response.data;
    });

const edit = () => {
  axios.patch(`/api/v1/user/${props.userId}`, user.value)
      .then(()=> {
        router.replace({name:"home"});
      })
}


</script>

<template>
  <div class="mt-2">
    <el-input v-model="user.email"/>
  </div>

  <div class="mt-2">
    <el-input v-model="user.username" />
  </div>

  <div class="mt-2">
    <el-input v-model="user.password" />
  </div>

  <div class="mt-2">
    <el-input v-model="user.phone"/>
  </div>
  <el-button type="warning" @click="edit()">수정 완료</el-button>

</template>
