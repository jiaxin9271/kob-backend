<template>
  <div></div>
</template>

<script setup>
import { acwingRceiveCode } from '@/apis/user';
import router from '@/router/index';
import { useUserStore } from '@/store/user';
import { useRoute } from 'vue-router';

const myRoute = useRoute();
const userStore = useUserStore();
acwingRceiveCode({
  code: myRoute.query.code,
  state: myRoute.query.state,
}).then(res => {
  let resp = res.data;
  if (resp.error_message === 'success') {
    localStorage.setItem('jwt_token', resp.jwt_token);
    userStore.token = resp.jwt_token;
    userStore.pulling_info = 'false';
    router.push({ name: 'home' });
  } else {
    router.push({ name: 'user_account_login' });
  }
});
</script>

<style scoped></style>
