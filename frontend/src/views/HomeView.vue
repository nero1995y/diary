<script setup lang="ts">
import axios from "axios";
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

const users = ref([]);
// const post = ref([]);

axios.get("/api/v1/users").then((response) => {
  response.data.userResponseDtoList.forEach((r: any) => {
    users.value.push(r);
  });
});

// axios.get("/api/post").then((response) => {
//   response.data.forEach((r: any) => {
//     users.value.push(r);
//   });
// });
</script>

<template>
  <ul>
    <li v-for="user in users" :key="user.id">
      <div class="title">
        <router-link :to="{name: 'userRead', params: {userId: user.id}}">{{ user.username}}</router-link>
      </div>
      <div class="content">
        {{ user.email}}
      </div>

      <div class="sub d-flex">
        <div class="category">개발 </div>
        <div class="regDate">2022-06-01</div>
      </div>
    </li>
  </ul>
</template>

<style scoped lang="scss">
ul {
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 1.3rem;

    .title  {
      a {
        font-size: 1.2rem;
        color: #303030;
        text-decoration: none;
      }

      &:hover {
        text-decoration: underline;
      }
    }

    .content {
      font-size: 0.95rem;
      margin-top: 8px;
      color: #303030;
    }

    &:last-child {
      margin-bottom: 0;
    }

    .sub {
      margin-top: 8px;
      font-size: 0.78rem;

      .regDate {
        margin-left: 10px;
        color: #6b6b5b;
      }
    }
  }
}
</style>
