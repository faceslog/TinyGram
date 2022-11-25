import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/store'
// Axios HTTP Request
import axios from 'axios'
// Google Login Plugin
import googleLogin from 'vue3-google-login'

// --- Vue Sweet Alert Pop Up -----
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';
// ---------------------------

import './assets/index.css'

const app = createApp(App)

app.use(router)
app.use(store)

// Sweet Alert
app.use(VueSweetalert2);

// Setup Axios
const instance = axios.create({
    baseURL: 'https://tinygram-369720.nw.r.appspot.com/_ah/api/instapi/v1',
});

app.config.globalProperties.$axios = { ...instance }
// ---------------------------
// Setup Google Login Btn

app.use(googleLogin, {
    clientId: '187194623723-7ifp7rf55768iigbooraqj9d6o6eqm69.apps.googleusercontent.com'
});
// ---------------------------
app.mount('#app')
