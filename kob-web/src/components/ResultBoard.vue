<template>
  <div class="result-board">
    <div class="result-board-text" v-if="pkStore.loser === 'all'">Draw</div>
    <div class="result-board-text" v-else-if="pkStore.loser === 'A' && pkStore.a_id === parseInt(userStore.id)">
      Lose
    </div>
    <div class="result-board-text" v-else-if="pkStore.loser === 'B' && pkStore.b_id === parseInt(userStore.id)">
      Lose
    </div>
    <div class="result-board-text" v-else>Win</div>
    <div class="result-board-btn">
      <button @click="restart" type="button" class="btn btn-warning btn-lg">再来!</button>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from '@/store/user';
import { usePkStore } from '@/store/pk';

const userStore = useUserStore();
const pkStore = usePkStore();

const restart = () => {
  pkStore.updateStatus('matching');
  pkStore.updateLoser('none');
  pkStore.updateOpponent({
    username: '我的对手',
    photo: 'http://119.23.67.36/static_image/1_1db2488f17-anonymous.png',
  });
};
</script>

<style scoped>
div.result-board {
  height: 30vh;
  width: 30vw;
  background-color: rgba(50, 50, 50, 0.5);
  position: absolute;
  top: 30vh;
  left: 35vw;
}
div.result-board-text {
  text-align: center;
  color: white;
  font-size: 50px;
  font-weight: 600;
  font-style: italic;
  padding-top: 5vh;
}

div.result-board-btn {
  padding-top: 7vh;
  text-align: center;
}
</style>
