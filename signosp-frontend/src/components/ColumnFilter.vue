<template>
    <div class="column-filter"><!--contenedor donde van a estar las tarjetas de cada evento-->
        <div class="column-titulo">{{ categoria }}</div>
        <div v-if="eventos.length > 0" v-for="evento in eventos" :key="evento.id_evento">
            <CardEvento :id="evento.id_evento" :titulo="evento.titulo" :fechaDelEvento="ajustarFecha(evento.fecha)"
                :estado="evento.estado" :id_categoria="evento.id_categoria" @estadoMod="refresh" />
        </div>
        <div v-else>
            <li><router-link to="/nuevoEvento" class="buttonclassic">Crear Evento</router-link></li>
        </div>
    </div>

</template>



<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import {ajustarFecha} from '../main';

import CardEvento from './CardEvento.vue';

const eventos = ref([])



const props = defineProps({
    categoria: String
})

const fetchEventos = async (categoria) => {
    try {
        const res = await axios.get(`http://localhost:8080/api/evento/busqueda/categoria/${categoria}`);
        eventos.value = res.data;
        //ordenar los eventos por estado, primero los de false y luego los true
        eventos.value.sort((a, b) => {
            if (a.estado === b.estado) {
                return new Date(a.fecha) - new Date(b.fecha);
            }
            return a.estado - b.estado;
        });
    } catch {
        console.log(error);
    }
}

const refresh = () => {
    fetchEventos(props.categoria)
}

onMounted(() => {
    fetchEventos(props.categoria)
})
</script>

<style scoped>
.column-filter {
    display: flex;
    flex-direction: column;
    align-items: center;
    max-height: 85vh;
    min-width: 350px;
    max-width: 350px;
    border: 2px solid var(--negro);
    padding: 20px;
    margin-right: 20px;
    overflow: auto;
    box-shadow: 5px 5px 15px -10px var(--negro);
    margin-bottom: 5px;
}

.column-filter::-webkit-scrollbar {
    display: none;
}

.column-titulo {
    align-self: baseline;
    font-weight: bold;
    font-size: x-large;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
</style>