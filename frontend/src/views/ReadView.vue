<script setup lang="ts">
import {defineProps, onMounted, ref} from "vue";
import axios from "axios";

const props = defineProps({
  userId: {
    type: [Number, String],
    require: true,
  }
})

const user = ref({
  id: 0,
  usernanme: "",
  email: "",
  phone: "",
  password: ""
});


onMounted(() => {
  axios.get(`/api/v1/user/${props.userId}`)
      .then((response) => {
        user.value = response.data;
      })
})


</script>

<template>
  <p>{user.username}</p>
  <p>{user.email}</p>
  <p>{user.phone}</p>
</template>
