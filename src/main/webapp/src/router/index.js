import { createRouter, createWebHistory } from 'vue-router'

// Views
import HomeView from '@/views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import PostView from '@/views/PostView.vue'
import UserView from '@/views/UserView.vue'
import AddPostView from '@/views/AddPostView.vue'
import NotFoundView from '@/views/NotFoundView.vue'

import store from '../store/store.js'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/login",
      name: 'login',
      component: LoginView
    },
    {
      path: "/not-found",
      name: 'not-found',
      component: NotFoundView
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
      path: "/users/:user",
      name: "user",
      component: UserView,
      meta : {
        requiresAuth: true
      }
    },
    {
      path: "/posts/:post",
      name: "post",
      component: PostView,
      meta : {
        requiresAuth: true
      }
    },
    {
      path: "/:catchAll(.*)",
      redirect: { path: "/not-found" }
    }
  ]
});

router.beforeEach((to, from, next) => {

  if(to.matched.some(record => record.meta.requiresAuth)) {
    
    if(store.getters.getToken != null) {
      next();
      return;
    }
      
    next({ path: '/login' });
  }
  else {
    next();
  }
});

export default router
