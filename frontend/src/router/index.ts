import { createWebHistory, createRouter } from 'vue-router';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),

    routes: [
        {
            path: "/",
            name: "home",
            component: HomeView,
        },

        {
            path: "/about",
            name: "about",
            component: () => import("../view/AbuotView.vue"),
        },
    ],
});

export default router;

