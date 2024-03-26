<template>
  <div>
    <PlayGround v-if="pkStore.status === 'playing'" />
    <MatchGround v-if="pkStore.status === 'matching'" />
    <ResultBoard v-if="pkStore.loser != 'none'" />
    <div class="user-color" v-if="pkStore.status === 'playing' && parseInt(userStore.id) === parseInt(pkStore.a_id)">
      左下角
    </div>
    <div class="user-color" v-if="pkStore.status === 'playing' && parseInt(userStore.id) === parseInt(pkStore.b_id)">
      右上角
    </div>
  </div>
</template>

<script setup>
import PlayGround from '@/components/PlayGround.vue';
import MatchGround from '@/components/MatchGround.vue';
import ResultBoard from '@/components/ResultBoard.vue';
import { onMounted, onUnmounted } from 'vue';
import { useUserStore } from '@/store/user';
import { usePkStore } from '@/store/pk';
import { useRecordStore } from '@/store/record';

const userStore = useUserStore();
const pkStore = usePkStore();
const recordStore = useRecordStore();
const socketUrl = `ws://119.23.67.36:3000/websocket/${userStore.token}/`;
pkStore.updateLoser('none');
recordStore.updateIsRecord(false);
let socket = null;

onMounted(() => {
  pkStore.updateOpponent({
    username: '我的对手',
    photo: 'http://119.23.67.36/static_image/1_1db2488f17-anonymous.png',
  });
  socket = new WebSocket(socketUrl);

  socket.onopen = () => {
    console.log('WebSocket connected!');
    pkStore.updateSocket(socket);
  };

  socket.onmessage = msg => {
    const data = JSON.parse(msg.data);
    if (data.event === 'start-matching') {
      // 匹配成功
      pkStore.updateOpponent({
        username: data.opponent_username,
        photo: data.opponent_photo,
      });
      setTimeout(() => {
        pkStore.updateStatus('playing');
      }, 200);
      pkStore.updateGame(data.game);
    } else if (data.event === 'move') {
      const game = pkStore.gameObject;
      const [snake0, snake1] = game.snakes;
      snake0.set_direction(data.a_direction);
      snake1.set_direction(data.b_direction);
    } else if (data.event === 'result') {
      const game = pkStore.gameObject;
      const [snake0, snake1] = game.snakes;
      if (data.loser === 'all' || data.loser === 'A') {
        snake0.status = 'die';
      }
      if (data.loser === 'all' || data.loser === 'B') {
        snake1.status = 'die';
      }
      pkStore.updateLoser(data.loser);
    }
  };

  socket.onerror = () => {
    console.log('网络错误');
  }

  socket.onclose = () => {
    console.log('WebSocket disconnected!');
  };
});

onUnmounted(() => {
  socket.close();
  pkStore.updateStatus('matching');
});
</script>

<style scoped>
div.user-color {
  text-align: center;
  color: white;
  font-size: 30px;
  font-weight: 600;
}
</style>
