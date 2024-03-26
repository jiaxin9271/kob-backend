import { defineStore } from 'pinia';

export const usePkStore = defineStore('pk', {
  state: () => {
    return {
      status: 'matching', // matching表示匹配界面，playing表示对战界面
      socket: null,
      opponent_username: '',
      opponent_photo: '',
      gamemap: null, // 服务器创建地图，保证对战双方的地图是一样的
      a_id: 0,
      a_sx: 0,
      a_sy: 0,
      b_id: 0,
      b_sx: 0,
      b_sy: 0,
      gameObject: null,
      loser: 'none', // none、all、A、B
    };
  },
  getters: {},
  actions: {
    updateSocket(socket: any) {
      this.socket = socket;
    },
    updateOpponent(opponent: any) {
      this.opponent_username = opponent.username;
      this.opponent_photo = opponent.photo;
    },
    updateStatus(status: any) {
      this.status = status;
    },
    updateGame(game: any) {
      this.gamemap = game.map;
      this.a_id = game.a_id;
      this.a_sx = game.a_sx;
      this.a_sy = game.a_sy;
      this.b_id = game.b_id;
      this.b_sx = game.b_sx;
      this.b_sy = game.b_sy;
    },
    updateGameObject(gameObject: any) {
      this.gameObject = gameObject;
    },
    updateLoser(loser: any) {
      this.loser = loser;
    },
  },
});
