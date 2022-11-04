import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'

import './assets/index.css'

const app = createApp(App)

app.use(router)

// Setup Axios
const instance = axios.create({
    baseURL: 'https://tinyinsta-366314.nw.r.appspot.com/_ah/api/instapi/v1',
});

app.config.globalProperties.axios = instance;
// ---------------------------

app.mount('#app')
