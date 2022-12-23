<script setup lang="ts">
import axios from "axios";
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

const users = ref([]);

axios.get("/api/v1/users").then((response) => {
  response.data.userResponseDtoList.forEach((r: any) => {
    users.value.push(r);
  });
});

</script>

<template>
  <ul>
    <li v-for="user in users" :key="user.id">
      <div>
        <router-link :to="{name: 'read', params: {userId: user.id}}">{{ user.username}}</router-link>
      </div>
      <div>
        {{ user.email}}
      </div>
    </li>
  </ul>
</template>
