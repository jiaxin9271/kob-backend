<template>
  <div class="matchground">
    <div class="row">
      <div class="col-4">
        <div class="user-photo">
          <img :src="userStore.photo" alt="" />
        </div>
        <div class="user-username">
          {{ userStore.username }}
        </div>
      </div>
      <div class="col-4">
        <div class="user-select-bot">
          <select v-model="select_bot" class="form-select" aria-label="Default select example">
            <option value="-1" selected>亲自出马</option>
            <option v-for="bot in bots" :key="bot.id" :value="bot.id">
              {{ bot.title }}
            </option>
          </select>
        </div>
      </div>
      <div class="col-4">
        <div class="user-photo">
          <img :src="pkStore.opponent_photo" alt="" />
        </div>
        <div class="user-username">
          {{ pkStore.opponent_username }}
        </div>
      </div>
      <div class="col-12" style="text-align: center; padding-top: 15vh">
        <button @click="click_match_btn" type="button" class="btn btn-warning btn-lg">{{ match_btn_info }}</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/store/user';
import { usePkStore } from '@/store/pk';
import { getBotList } from '@/apis/user';

const userStore = useUserStore();
const pkStore = usePkStore();
let match_btn_info = ref('开始匹配');
let bots = ref([]);
let select_bot = ref('-1');

const click_match_btn = () => {
  if (match_btn_info.value === '开始匹配') {
    match_btn_info.value = '取消';
    pkStore.socket.send(
      JSON.stringify({
        event: 'start-matching',
        bot_id: select_bot.value,
      }),
    );
  } else {
    match_btn_info.value = '开始匹配';
    pkStore.socket.send(
      JSON.stringify({
        event: 'stop-matching',
      }),
    );
  }
};

const refresh_bots = () => {
  getBotList().then(res => {
    bots.value = res.data;
  });
};

refresh_bots();
</script>

<style scoped>
div.matchground {
  width: 60vw;
  height: 70vh;
  margin: 40px auto;
  background-color: rgba(50, 50, 50, 0.5);
}
div.user-photo {
  text-align: center;
  padding-top: 10vh;
}
div.user-photo > img {
  border-radius: 50%;
  width: 20vh;
}
div.user-username {
  text-align: center;
  font-size: 24px;
  font-weight: 600;
  color: white;
  padding-top: 2vh;
}
</style>
