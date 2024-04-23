import { createApp } from 'vue'
import axios from 'axios'
import VueAxios from 'vue-axios'
import router from './router'
import './style.css'
import App from './App.vue'

const app = createApp(App)

// configura axios para incluir el token en todas las solicitudes (Solo configura)
const token = localStorage.getItem('token');
if(token){
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
} 
//Las solicitudes de manera default no tienen agregado el token en su header (aca se agrega el token)
// Intercepta las solicitudes y agrega el token
axios.interceptors.request.use(
    config => {
        const token = localStorage.getItem('token');
        if(token){
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    error => {
        console.log("error en main.js")
        return Promise.reject(error);
    }
);

app.use(VueAxios, axios)
app.use(router)
app.mount('#app')