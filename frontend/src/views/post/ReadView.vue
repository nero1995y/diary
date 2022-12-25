<script setup lang="ts">
import {defineProps, onMounted, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const props = defineProps({
  postId: {
    type: [Number, String],
    require: true,
  }
})

// const user = ref({
//   id: 0,
//   title: "",
// });
//
//
// onMounted(() => {
//   axios.get(`/api/post/${props.postId}`)
//       .then((response) => {
//         post.value = response.data;
//       })
// })
//
// const router = useRouter();
//
// const moveToEdit = () => {
//   router.push({name: "edit", params:{ postId: props.postId}});
// }


</script>

<template>
  <p>{{post.title}}</p>
  <p>{{post.content}}</p>
  <el-button type="warning">수정</el-button>
</template>
