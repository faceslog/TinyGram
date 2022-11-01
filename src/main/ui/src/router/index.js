import { createRouter, createWebHistory } from 'vue-router'

// Views
import HomeView from '@/views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import PostView from '@/views/PostView.vue'
import UserView from '@/views/UserView.vue'
import AddPostView from '@/views/AddPostView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: "/login",
      name: 'login',
      component: LoginView
    },
    {
      path: "/addpost",
      name: "addpost",
      component: AddPostView
    },
    {
      path: "/community/:user",
      name: "user",
      component: UserView,
    },
    {
      path: "/community/:user/:post",
      name: "post",
      component: PostView
    },
    {
      path: "/:catchAll(.*)",
      redirect: { path: "/login" }
    }
  ]
});

export default router
