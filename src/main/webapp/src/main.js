import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import googleLogin from 'vue3-google-login'

import './assets/index.css'

const app = createApp(App)

app.use(router)

// Setup Axios
const instance = axios.create({
    baseURL: 'https://tinyinsta-366314.nw.r.appspot.com/_ah/api/instapi/v1',
});

app.config.globalProperties.axios = instance;
// ---------------------------
// Setup Google Login Btn

app.use(googleLogin, {
    clientId: '666928071557-7tupn0nhb8v6cg13vsjtlreg61b6akob.apps.googleusercontent.com'
});
// ---------------------------
app.mount('#app')
