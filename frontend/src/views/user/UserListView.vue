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

const moveToDetail = function(row: any){
  router.push({name:"userRead",params:{userId: row.id}});
}

</script>

<template>
  <span class="text-large font-600 mr-3">유저 목록(관리자 페이지 전용)</span>
  <el-table
      :data="users"
      style="width: 100%; margin-bottom: 20px"
      row-key="id"
      @row-click="moveToDetail"
      class="mt-5"
  >
    <el-table-column prop="username" label="username"/>
    <el-table-column prop="email" label="email"/>
    <el-table-column prop="phone" label="phone"/>
    <el-table-column prop="regDate" label="regDate"/>
  </el-table>
</template>

<style scoped lang="scss">
</style>
