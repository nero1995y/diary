<script setup lang="ts">
import axios from "axios";
import {defineProps, ref} from "vue";
import {useRouter} from "vue-router";
import Input from "@/components/DiarySearch.vue";

const router = useRouter();

const diaryList = ref([]);

const username = 'nero'; //TODO 세션 넣기


axios.get(`/api/v1/diaries`,
    {params: {
        username: username
      }
    }
).then((response) => {
  response.data.diaryResponseDtoList.forEach((r: any) => {
    diaryList.value.push(r);
  });
});

const moveToDetail = function(row: any){
  router.push({name:"userRead",params:{userId: row.id}});
}
</script>

<template>
  <span class="text-large font-600 mr-3">다이어리 페이지</span>
  <div class="container-fluid py-4 p-5">
    <Input/>
    <div class="row">
      <div v-for="diary in diaryList" :key="diary.id" class="text-center h-100">
        <div class="m-4">
          <div class="card">
            <h5 class="card-title m-5">{{diary.name}}</h5>
            <p class="card-text">작성자 이름 넣기 2023.01.01</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
  .card {
    height: 150px;
  }
</style>
