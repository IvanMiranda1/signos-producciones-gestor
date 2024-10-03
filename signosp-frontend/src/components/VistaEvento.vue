<template>
    <div class="container" v-if="visible">
        <div>
            <span v-bind:class="{'finalizado':estadoEvent,'pendiente':!estadoEvent}">{{ evento.estado ? "Finalizado" : "Pendiente" }}</span>
        </div>
        <h2>{{ evento.titulo }}</h2>
        <div>
            <!--
            Datos del cliente
            -->
            <div class="clienteDatos">
                <h4>Cliente</h4>
                <ul>
                    <li><b>Nombre y apellido</b></li>
                    <li>{{ evento.cliente.nomyape }}</li>
                    <hr>
                    <li><b>Contacto</b></li>
                    <li>Correo: {{ evento.cliente.correo }}</li>
                    <li>Teléfono: {{ evento.cliente.telefono }}</li>
                </ul>
            </div>

            <div>
                <h4>Paquete</h4>
                <span>{{ paquete.nombre }}</span>
            </div>

            <div>
                <h4>Personal designado</h4>
                <ul>
                    <li v-for="empleado in listEmpleados">{{ empleado.nomyape }}</li>
                </ul>
            </div>

            <div class="subtareasContainer">
                <h4>Subtareas</h4>
                <div class="subtareas">
                    <div v-if="subtareas" v-for="subtarea in subtareas" class="subtarea">
                        <span v-if="subtarea.estado">
                            <BadgeCheck width="25" height="25" iconColor="#8fd14f" />
                        </span>
                        <span v-else>
                            <BadgeCheck width="25" height="25" iconColor="#2d9bf0" />
                        </span>
                        <p> {{ subtarea.nombre }}</p>
                        <div>
                            <span @click="terminarSubtarea(subtarea.id_subtarea)">
                                <Check width="30" height="30" iconColor="#8fd14f" />
                            </span>
                            <span @click="eliminarsSubtarea(subtarea.id_subtarea)">
                                <Xsvg width="30" height="30" iconColor="#da0063" />
                            </span>
                        </div>
                    </div>
                </div>
                <div class="subtareabtnContainer">
                    <button @click="showPopup('regSubtarea')">+ Agregar subtarea</button>
                    <PopUp v-if="popupReg" v-model:visible="popupReg">
                        <RegistrarSubtarea :visible="popupReg" @register="crearSubtarea" :id_evento="id" />
                    </PopUp>
                </div>
            </div>


            <div class="comentarios">
                <transition name="slide-up">
                    <div v-bind:class="{ 'comentarios-on': visibilidadComentarios, 'comentarios-off': !visibilidadComentarios }"
                        class="comentarios-activo">
                        <h4 @click="toggleComentarios">
                            <span v-if="!visibilidadComentarios">
                                <ChevronUp width="20" height="20" />
                            </span>
                            <span v-else>
                                <ChevronDown width="20" height="20" />
                            </span>
                            Comentarios
                        </h4>
                        <div v-if="visibilidadComentarios" class="comentariosContainer">
                            <div v-for="comentario in comentarios" class="comentario" :key="comentario.id_comentario">
                                <b>{{ obtenerUsername(comentario.id_usuario) }}</b>
                                <span @click="eliminarComentario(comentario.id_comentario)" class="xcomentario"
                                    v-if="comentario.id_usuario === usuario.id_usuario">
                                    <Xsvg width="30" height="30" iconColor="#da0063" />
                                </span>
                                <p>{{ comentario.contenido }}</p>
                            </div>
                        </div>
                        <div v-if="visibilidadComentarios" class="cajaComent">
                            <form @submit.prevent="registrarComentario">
                                <textarea name="" id="" cols="30" rows="10" v-model="comentario.contenido"
                                    :placeholder="'Comentar como ' + usuario.username + '...'"></textarea>
                                <button class="buttonclassic" type="submit">Comentar</button>
                            </form>
                        </div>
                    </div>
                </transition>
            </div>

        </div>


    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';

import PopUp from './ui/PopUp.vue';
import RegistrarSubtarea from './Registrar/RegistrarSubtarea.vue';
//svgs
import BadgeCheck from './icons/BadgeCheck.vue';
import Check from './icons/Check.vue';
import Xsvg from './icons/Xsvg.vue';
import ChevronUp from './icons/ChevronUp.vue';
import ChevronDown from './icons/ChevronDown.vue';

const popupReg = ref(false);

const showPopup = (pop, entidad = null) => {
    if (pop === "regSubtarea") {
        popupReg.value = !popupReg.value;
    }
}


const evento = ref({
    id_evento: 0,
    titulo: "",
    estado: false,
    cliente: {
        id_cliente: 0,
        nomyape: "",
        correo: "",
        telefono: ""
    },
    fecha: "",
    id_paquete: 0,
    id_categoria: 0,
    pago: {
        id_pago: 0,
        id_evento: 0,
        forma_de_pago: "",
        cant_cuotas: 0
    },
    empleados: []
}),
    comentario = ref({
        id_evento: 0,
        id_usuario: 0,
        contenido: ""
    }),
    paquete = ref({}),
    listEmpleados = ref([]),
    subtareas = ref([]),
    comentarios = ref([]),
    usuario = ref({}),
    usuarios = ref([]),
    estadoEvent = ref(false);


const isVisible = ref(false);

const props = defineProps({
    visible: {
        type: Boolean,
        required: true
    },
    id: {
        type: Number,
        required: true
    }
})

const fetchDatos = async (id_evento) => {
    try {
        const resEvento = await axios.get(`http://localhost:8080/api/evento/${id_evento}`)
        evento.value = resEvento.data
        estadoEvent.value = evento.value.estado;

        const resPaquete = await axios.get(`http://localhost:8080/api/paquete/${evento.value.id_paquete}`);
        paquete.value = resPaquete.data;

        const resEmpleados = await axios.get(`http://localhost:8080/api/evento/empleadosfull/${id_evento}`);
        listEmpleados.value = resEmpleados.data;

        const resSubtareas = await axios.get(`http://localhost:8080/api/evento/subtareas/${id_evento}`);
        subtareas.value = resSubtareas.data;

        const resComentarios = await axios.get(`http://localhost:8080/api/evento/comentarios/${id_evento}`);
        comentarios.value = resComentarios.data;

        const token = ref(localStorage.getItem('token'));
        const resUsuario = await axios.post(`http://localhost:8080/auth/user`, { token: token.value });
        usuario.value = resUsuario.data;

        const resUsuarios = await axios.get('http://localhost:8080/api/usuario');
        usuarios.value = resUsuarios.data;

        console.log(usuario.value)
        console.log(comentarios.value)


    } catch (error) {
        console.log(error);
    }
}

const crearSubtarea = async () => {
    try {
        showPopup('regSubtarea')
        const resSubtareas = await axios.get(`http://localhost:8080/api/evento/subtareas/${props.id}`);
        subtareas.value = resSubtareas.data;
    } catch (error) {
        console.log(error);
    }
}
const terminarSubtarea = async (id_subtarea) => {
    try {
        await axios.put(`http://localhost:8080/api/subtarea/terminar/${id_subtarea}`)
        const resSubtareas = await axios.get(`http://localhost:8080/api/evento/subtareas/${props.id}`);
        subtareas.value = resSubtareas.data;
    } catch (error) {
        console.log(error);
    }
}

const eliminarsSubtarea = async (id_subtarea) => {
    try {
        await axios.delete(`http://localhost:8080/api/subtarea/${id_subtarea}`)
        const resSubtareas = await axios.get(`http://localhost:8080/api/evento/subtareas/${props.id}`);
        subtareas.value = resSubtareas.data;
    } catch (error) {
        console.log(error);
    }
}

const registrarComentario = async () => {
    try {
        comentario.value.id_usuario = usuario.value.id_usuario;
        comentario.value.id_evento = props.id;
        (comentario.value)
        await axios.post(`http://localhost:8080/api/comentario`, comentario.value);
        const resComentarios = await axios.get(`http://localhost:8080/api/evento/comentarios/${props.id}`);
        comentarios.value = resComentarios.data;
        comentario.value.contenido = "";
    } catch (error) {
        console.log(error);
    }
}
const eliminarComentario = async (id_comentario) => {
    try {
        await axios.delete(`http://localhost:8080/api/comentario/${id_comentario}`)
        const resComentarios = await axios.get(`http://localhost:8080/api/evento/comentarios/${props.id}`);
        comentarios.value = resComentarios.data;
    } catch (error) {
        console.log(error);
    }
}
const obtenerUsername = (id_usuario) => {
    const usuario = usuarios.value.find(user => user.id_usuario == id_usuario);
    return usuario ? usuario.username : "Desconocido"
}

//visibilidad de comentarios y CSS
const visibilidadComentarios = ref(false);
const toggleComentarios = () => {
    visibilidadComentarios.value = !visibilidadComentarios.value;

}

watch(() => isVisible.value, (newValue) => {
    if (newValue) {
        fetchDatos(props.id);
    }
});

onMounted(() => {
    isVisible.value = props.visible;
});

</script>

<style scoped>
*{
    text-align: ;
}
.container {
    width: 500px;
}

.comentarios-on {
    position: absolute;
    bottom: -30px;
    left: 0;
    right: 0;
    height: 300px;
    /* Ajusta la altura según necesites */
    background-color: #f4f4f4;
    /* Color de fondo */
    transition: transform 0.5s ease-in-out;
    transform: translateY(100%);
}

.comentarios-off {
    background-color: #f4f4f4;
    position: absolute;
    bottom: -20px;
    left: 0;
    right: 0;
    z-index: 1;
}

.comentarios-activo {
    transform: translateY(0);
    /* Muestra los comentarios */
    border-radius: 5px;
}

.cajaComent {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
}

h2 {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

textarea {
    resize: none;
    height: 70px;
    width: 500px;
}

.comentariosContainer {
    overflow-y: auto;
    height: 160px;
    scrollbar-width: none;
}
.comentariosContainer::-webkit-scrollbar {
    display:none;
}

.subtareasContainer {
    position: relative;
    height: 150px;
}

.subtareas {
    height: 105px;
    overflow-y: auto;
}

.subtareabtnContainer {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
}

.subtarea {
    display: flex;
}

.subtarea>div span {
    cursor: pointer;
    margin-left: 10px;
}

.comentario {
    width: 90%;
    border-radius: 10px;
    background-color: #fbfbfb;
    box-shadow: 0 0 10px -5px black;
    margin-bottom: 10px;
    position:relative;
}

.xcomentario {
    position: absolute;
    left: 95%;
    padding: 0;
    display: inline-flex;
    width: 20px;
    height: 20px;
    justify-content: center;
    align-items: center;
    cursor: pointer;
}

.clienteDatos {
    background-color: #f4f4f480;
    border-radius: 10px;
}
.finalizado { 
    color: var(--verde);
    border: 1px solid var(--verde);
    padding:5px;
    border-radius: 10px;
}
.pendiente{
    color: var(--azul);
    border: 1px solid var(--azul);
    padding:5px;
    border-radius: 10px;
}
</style>
