import { createRouter, createWebHistory } from "vue-router";
import InicioSesion from "../components/InicioSesion.vue";
//import ModificarPrestamo from "../page/ModificarPrestamo/index.vue"

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes : [
        {
            path: '/',
            name: 'iniciosesion',
            component: InicioSesion
        }/*,
        {
            path: '/modificarPrestamo/:id',
            name : 'modificarPrestamo',
            component: ModificarPrestamo,
            props: true,
        }*/
    ]
})

export default router;