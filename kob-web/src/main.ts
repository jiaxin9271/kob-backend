import { createApp } from 'vue';
import router from './router/index';
import pinia from './store';
import App from './App.vue';
import * as ElementPlusIconsVue from '@element-plus/icons-vue'; //引入图标
import 'element-plus/dist/index.css'; //引入样式

const app = createApp(App);

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}

app.use(router).use(pinia);

app.mount('#app');
