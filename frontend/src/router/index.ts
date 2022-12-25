import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import WriteView from "../views/WriteView.vue";
import UserCreateView from "../views/user/UserCreateView.vue";
import UserReadView from "../views/user/UserReadView.vue";
import UserEditView from "../views/user/UserEditView.vue";
import ReadView from "../views/post/ReadView.vue";
import EditView from "../views/EditView.vue";

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

    // {
    //   path: '/post/read/:postId',
    //   name: 'read',
    //   component: ReadView,
    //   props: true
    // },
    //
    // {
    //   path: '/post/edit/:postId',
    //   name: 'edit',
    //   component: EditView,
    //   props: true
    // },

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
      path: '/user/edit/:userId',
      name: 'userEdit',
      component: UserEditView,
      props: true
    }
  ],
});

export default router;
