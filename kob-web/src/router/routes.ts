const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/pk',
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/pk',
    name: 'pk_index',
    component: () => import('@/views/pk/PkIndexView.vue'),
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/record',
    name: 'record_index',
    component: () => import('@/views/record/RecordIndexView.vue'),
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/record/:recordId",
    name: "record_content",
    component: () => import('@/views/record/RecordContentView.vue'),
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/ranklist',
    name: 'ranklist_index',
    component: () => import('@/views/ranklist/RanklistIndexView.vue'),
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/user/bot',
    name: 'user_bot_index',
    component: () => import('@/views/user/bot/UserBotIndexView.vue'),
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/user/account/login',
    name: 'user_account_login',
    component: () => import('@/views/user/account/UserAccountLoginView.vue'),
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/user/account/acwing/web/receive_code",
    name: "user_account_acwing_web_receive_code",
    component: () => import('@/views/user/account/UserAccountAcWingWebReceiveCodeView.vue'),
    meta: {
      requestAuth: false,
    }
  },
  {
    path: '/user/account/register',
    name: 'user_account_register',
    component: () => import('@/views/user/account/UserAccountRegisterView.vue'),
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/:pathMatch(.*)*",
    component: () => import("@/views/error/NotFound.vue"),
    meta: {
      requestAuth: false,
    }
  },
];

export default routes;
