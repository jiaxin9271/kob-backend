<template>
  <ContentField v-if="!pulling_info">
    <div class="row justify-content-md-center">
      <div class="col-3">
        <form @submit.prevent="login">
          <div class="mb-3">
            <label for="username" class="form-label">用户名</label>
            <input id="username" v-model="username" type="text" class="form-control" placeholder="请输入用户名" />
          </div>
          <div class="mb-3">
            <label for="password" class="form-label">密码</label>
            <input id="password" v-model="password" type="password" class="form-control" placeholder="请输入密码" />
          </div>
          <div class="error-message">{{ error_message }}</div>
          <button type="submit" class="btn btn-primary">提交</button>
        </form>
        <div style="text-align: center; margin-top: 20px; cursor: pointer" @click="acwing_login">
          <img
            width="30"
            src="https://cdn.acwing.com/media/article/image/2022/09/06/1_32f001fd2d-acwing_logo.png"
            alt=""
          />
          <br />
          AcWing一键登录
        </div>
      </div>
    </div>
  </ContentField>
</template>

<script setup>
import { acwingApplyCode } from '@/apis/user';
import { storeToRefs } from 'pinia';
import { useUserStore } from '@/store/user';
import { ref } from 'vue';
import router from '@/router/index';
const userStore = useUserStore();
const { pulling_info } = storeToRefs(userStore);
let username = ref('');
let password = ref('');
let error_message = ref('');

userStore.initLogin({
  success() {
    router.push({ name: 'home' });
  },
});

const login = () => {
  error_message.value = '';
  userStore.login({
    username: username.value,
    password: password.value,
    success() {
      router.push({ name: 'home' });
    },
    error() {
      error_message.value = '用户名或密码错误';
    },
  });
};

const acwing_login = () => {
  acwingApplyCode()
    .then(res => {
      let resp = res.data;
      if (resp.error_message === 'success') {
        window.location.replace(resp.apply_code_url);
      }
    })
    .catch(res => {
      console.log(res.message);
    });
};
</script>

<style scoped>
button {
  width: 100%;
}
div.error-message {
  color: red;
}
</style>
