import { createRouter, createWebHistory } from "vue-router";
import Home from "../components/Home.vue";
import RegistrarEvento from "../components/Registrar/RegistrarEvento.vue"
import RegistrarPaquete from "../components/Registrar/RegistrarPaquete.vue"
import RegistrarEmpleado from "../components/Registrar/RegistrarEmpleado.vue"
import Servicios from "../components/Servicios.vue";
import Materiales from "../components/MaterialDeEntrega.vue";
import Categorias from "../components/Categorias.vue";
import Especialidades from "../components/Especialidades.vue";
import Paquetes from "../components/Paquetes.vue";
import Empleados from "../components/Empleados.vue";
import RegistrarPago from "../components/Registrar/RegistrarPago.vue"
import Calendario from "../components/Calendario.vue";
import Eventos from "../components/Eventos.vue";
import Clientes from "../components/Clientes.vue";
import Ganancias from "../components/Ganancias.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes : [
        {
            path: '/',
            name: 'home',
            component: Home,
            meta:{ title: 'Tablero'}
        },
        {
            path: '/nuevoEvento',
            name: 'nuevoEvento',
            component: RegistrarEvento,
            meta: {title: 'Registrar Nuevo Evento'}
        },
        {
            path: '/nuevoPaquete',
            name: 'nuevoPaquete',
            component: RegistrarPaquete,
            meta: {title: 'Registrar Nuevo Paquete'}
        },
        {
            path: '/registrarEmpleado',
            name: 'registrarEmpleado',
            component: RegistrarEmpleado,
            meta: {title: 'Registrar Nuevo Empleado'}
        },
        {
            path: '/servicios',
            name: 'servicios',
            component: Servicios,
            meta: {title: 'Listado de Servicios'}
        },
        {
            path: '/materiales',
            name: 'materiales',
            component: Materiales,
            meta: {title: 'Listado de Material de Entrega'}
        },
        {
            path: '/categorias',
            name: 'categorias',
            component: Categorias,
            meta: {title: 'Listado de Categorias'}
        },
        {
            path: '/especialidades',
            name: 'especialidades',
            component: Especialidades,
            meta: {title: 'Listado de Especialidades'}
        },
        {
            path: '/paquetes',
            name: 'paquetes',
            component: Paquetes,
            meta: {title: 'Listado de Paquetes'}
        },
        {
            path: '/empleados',
            name: 'empleados',
            component: Empleados,
            meta: {title: 'Listado de Empleados'}
        },
        {
            path: '/registrarPago/:id',
            name: 'registrarPago',
            component: RegistrarPago,
            props:true,
            meta: {title: 'Registrar pago'}
        },
        {
            path: '/calendario',
            name: 'calendario',
            component: Calendario,
            meta: {title: 'Calendario'}
        },
        {
            path: '/eventos',
            name: 'eventos',
            component: Eventos,
            meta: {title: 'Listado de eventos'}
        },
        {
            path: '/clientes',
            name: 'clientes',
            component: Clientes,
            meta: {title: 'Listado de Clientes'}
        },
        {
            path: '/ganancias',
            name: 'ganancias',
            component: Ganancias,
            meta: {title: 'Resultado de ingresos'}
        },
        /*,
        {
            path: '/modificarPrestamo/:id',
            name : 'modificarPrestamo',
            component: ModificarPrestamo,
            props: true,
        }*/
    ]
})

export default router;