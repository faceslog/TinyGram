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
    clientId: '266339239467-9ssi0u1qmbqt5ahb1f40gj0no10ch4e9.apps.googleusercontent.com'
});
// ---------------------------
app.mount('#app')
