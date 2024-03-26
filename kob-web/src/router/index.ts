import { createRouter, createWebHistory } from 'vue-router';
import routes from './routes';
import { storeToRefs } from 'pinia';
import pinia from '@/store';
import { useUserStore } from '@/store/user';

const userStore = useUserStore(pinia);
const { is_login } = storeToRefs(userStore);
const router = createRouter({
  history: createWebHistory(), //可传参数，配置base路径，例如'/app'
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.meta.requestAuth && !is_login.value) {
    next({ name: 'user_account_login' });
  } else {
    next();
  }
});

export default router;
