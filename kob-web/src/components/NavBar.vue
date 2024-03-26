<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
      <router-link class="navbar-brand" :to="{ name: 'home' }"> King Of Bots </router-link>
      <div class="collapse navbar-collapse" id="navbarText">
        <!-- 子窗口，判断 route_name 的名字来进行高亮-->
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link class="nav-link" active-class="active" :to="{ name: 'pk_index' }"> 对战 </router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" active-class="active" :to="{ name: 'record_index' }"> 对局列表 </router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" active-class="active" :to="{ name: 'ranklist_index' }"> 排行榜 </router-link>
          </li>
        </ul>

        <!-- 登录了 -->
        <ul class="navbar-nav" v-if="is_login">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              {{ username }}
            </a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
              <li>
                <router-link class="dropdown-item" :to="{ name: 'user_bot_index' }"> 我的Bot </router-link>
              </li>
              <li><hr class="dropdown-divider" /></li>
              <li>
                <a class="dropdown-item" href="#" @click="logout">退出</a>
              </li>
            </ul>
          </li>
        </ul>

        <!-- 未登录 -->
        <ul class="navbar-nav" v-else-if="!pulling_info">
        <!-- <ul class="navbar-nav" v-else> -->
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'user_account_login' }" role="button"> 登录 </router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'user_account_register' }" role="button"> 注册 </router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { storeToRefs } from 'pinia';
import { useUserStore } from '@/store/user';
const userStore = useUserStore();
const { pulling_info, is_login, username } = storeToRefs(userStore);
const logout = () => {
  userStore.logout();
};
</script>
