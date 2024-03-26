<template>
  <Navbar></Navbar>
  <router-view v-slot="{ Component }">
    <transition name="fade" mode="out-in">
      <component :is="Component" />
    </transition>
  </router-view>
</template>

<script setup>
import Navbar from '@/components/NavBar.vue';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap';
</script>

<style>
body {
  background-image: url('@/assets/images/background.png');
  background-size: cover;
}
</style>
