import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import UserCreateView from "../views/user/UserCreateView.vue";
import UserReadView from "../views/user/UserReadView.vue";
import UserListView from "../views/user/UserListView.vue";
import UserEditView from "../views/user/UserEditView.vue";
import LoginView from  "../views/login/LoginView.vue";
import AdminView from "../views/admin/adminView.vue";
import DiaryView from "../views/diary/DiaryView.vue";
import DiaryWriteView from "../views/diary/DiaryWriteView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: '/user',
      name: 'user',
      component: UserCreateView
    },
    {
      path: '/user/read/:userId',
      name: 'userRead',
      component: UserReadView,
      props: true
    },
    {
      path: '/users',
      name: 'usersList',
      component: UserListView,
    },
    {
      path: '/user/edit/:userId',
      name: 'userEdit',
      component: UserEditView,
      props: true
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/diaryList',
      name: 'diaryList',
      component: DiaryView,
    },
    {
      path: '/diary',
      name: 'DiaryWrite',
      component: DiaryWriteView,
    },
    {
      path: '/admin',
      name: 'admin',
      component: AdminView,
    },

  ],
});

export default router;
