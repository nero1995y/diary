<script setup lang="ts">
import {defineProps, onMounted, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const props = defineProps({
  userId: {
    type: [Number, String],
    require: true,
  }
})

const user = ref({
  id: 0,
  username: "",
  email: "",
  phone: "",
  password: ""
});

const router = useRouter();

const moveToEdit = () => {
  router.push({name: "userEdit", params:{ postId: props.userId}});
}

onMounted(() => {
  axios.get(`/api/v1/user/${props.userId}`)
      .then((response) => {
        user.value = response.data;
      })
})

</script>

<template>
  <p>{{user.username}}</p>
  <p>{{user.email}}</p>
  <p>{{user.phone}}</p>
  <el-button type="warning" @click="moveToEdit()">수정</el-button>
</template>
