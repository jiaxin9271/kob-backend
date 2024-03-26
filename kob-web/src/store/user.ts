import { defineStore } from 'pinia';
import { getInfo, getToken, register } from '@/apis/user';

export const useUserStore = defineStore('user', {
  state: () => {
    return {
      id: '',
      username: '',
      photo: '',
      is_login: false,
      pulling_info: true, // 是否正在从云端拉取信息
      token: '',
    };
  },
  getters: {
    userName: state => state.username,
  },
  actions: {
    initLogin(data: any) {
      const jwt_token = localStorage.getItem('jwt_token');
      if (jwt_token) {
        this.token = jwt_token;
        getInfo()
          .then(res => {
            let resp = res.data;
            this.id = resp.id;
            this.username = resp.username;
            this.photo = resp.photo;
            this.is_login = true;
            data.success();
          })
          .catch(res => console.log(res.message));
      }
      this.pulling_info = false;
    },
    getInfo() {
      getInfo()
        .then(res => {
          let data = res.data;
          this.id = data.id;
          this.username = data.username;
          this.photo = data.photo;
        })
        .catch(res => console.log(res.message));
    },
    login(data: any) {
      localStorage.removeItem('jwt_token');
      getToken({ username: data.username, password: data.password })
        .then(resp => {
          let res = resp.data;
          if (res.error_message == 'success') {
            localStorage.setItem('jwt_token', res.token);
            this.token = res.token;
            this.is_login = true;
            this.getInfo();
            data.success();
          } else {
            data.error();
          }
        })
        .catch(res => {
          console.log(res.message);
        });
    },
    register(data: any) {
      localStorage.removeItem('jwt_token');
      register({ username: data.username, password: data.password, confirmedPassword: data.confirmedPassword })
        .then(resp => {
          let res = resp.data;
          if (res.error_message == 'success') {
            data.success();
          } else {
            data.error(res.error_message);
          }
        })
        .catch(res => {
          console.log(res.message);
        });
    },
    logout() {
      localStorage.removeItem('jwt_token');
      this.id = '';
      this.username = '';
      this.photo = '';
      this.token = '';
      this.is_login = false;
      this.pulling_info = false;
    },
  },
});
