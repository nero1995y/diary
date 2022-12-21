import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import WriteView from "../views/WriteView.vue";
import UserView from "../views/UserView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },

    {
      path: '/write',
      name: 'write',
      component: WriteView,
    },
    {
      path: '/user',
      name: 'user',
      component: UserView
    }
  ],
});

export default router;
