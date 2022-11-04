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
      path: "/login",
      name: 'login',
      component: LoginView
    },
    {
      path: '/',
      name: 'home',
      component: HomeView,
      meta : {
        requiresAuth: true
      }
    },
    {
      path: "/addpost",
      name: "addpost",
      component: AddPostView,
      meta : {
        requiresAuth: true
      }
    },
    {
      path: "/community/:user",
      name: "user",
      component: UserView,
      meta : {
        requiresAuth: true
      }
    },
    {
      path: "/community/:user/:post",
      name: "post",
      component: PostView,
      meta : {
        requiresAuth: true
      }
    },
    {
      path: "/:catchAll(.*)",
      redirect: { path: "/login" }
    }
  ]
});

router.beforeEach((to, from, next) => {

  if(to.matched.some(record => record.meta.requiresAuth)) {
    
    if(localStorage.getItem("token")) {
      next();
    }
      
    next({ path: '/login' });
  }
  else {
    next();
  }
});

export default router
