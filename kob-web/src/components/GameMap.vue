<template>
  <div ref="parent" class="gamemap">
    <canvas ref="canvas" tabindex="0"></canvas>
  </div>
</template>

<script setup>
import { GameMap } from '@/assets/scripts/GameMap';
import { ref, onMounted } from 'vue';
import { usePkStore } from '@/store/pk';
import { useRecordStore } from '@/store/record';

const pkStore = usePkStore();
const recordStore = useRecordStore();
let parent = ref(null);
let canvas = ref(null);

onMounted(() => {
  pkStore.updateGameObject(new GameMap(canvas.value.getContext('2d'), parent.value, pkStore, recordStore));
});
</script>

<style scoped>
div.gamemap {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
