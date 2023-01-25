<script setup lang="ts">
import axios from "axios";
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

const users = ref([]);
</script>

<template>
  <a href="/api/oauth2/autorization/google">Google login</a>
</template>

<style scoped lang="scss">
</style>
