<template>
    <div class="main-container">
        <input type="text" v-model="filtro" placeholder="Buscar categoría..." />
        <ul class="categorias" v-show="categoriasFiltradas.length">
            <li v-for="categoria in categoriasFiltradas" :key="categoria.id_categoria">
                <span>{{ categoria.nombre }}</span>
                <div>
                    <span><button @click="EliminarCategoria(categoria.id_categoria)">&nbsp;X&nbsp;</button></span>
                    <span><button @click="showPopup('popupMod', categoria)">Modificar</button>
                        <PopUp v-if="popupMod" v-model:visible="popupMod">
                            <GenericModificar :visible="popupMod" tipo="categoria" :entidad="categoriaSeleccionada"
                                @update="updatePop" />
                        </PopUp>
                    </span>
                </div>
            </li>
        </ul>
        <ul>
            <li>
                <button @click="showPopup('popupReg')">Agregar Categoria</button>
                <PopUp v-if="popupReg" v-model:visible="popupReg">
                    <Registrarcategoria :visible="popupReg" @update="registerOk" />
                </PopUp>
            </li>
        </ul>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';

import PopUp from './ui/PopUp.vue';
import Registrarcategoria from './Registrar/RegistrarCategoria.vue';
import GenericModificar from './Modificar/GenericModificar.vue';

const categorias = ref([]);
const filtro = ref('');
const emit = defineEmits(['registrado', 'update:visible']);
const popupReg = ref(false);
const popupMod = ref(false);
const categoriaSeleccionada = ref(null);

const showPopup = (pop, entidad = null) => {
    if (pop == "popupReg") {
        popupReg.value = !popupReg.value;
    }
    if (pop == "popupMod") {
        if (entidad != null) {
            categoriaSeleccionada.value = entidad;
            popupMod.value = !popupMod.value;
        } else {
            popupMod.value = !popupMod.value;
        }
    }
}

const updatePop = () => {
    showPopup("popupMod");
    fetchDatos();
}

const fetchDatos = async () => {
    try {
        const serv = await axios.get('http://localhost:8080/api/categoria');
        categorias.value = await serv.data;
    } catch (error) {
        console.log(error);
    }
}

const EliminarCategoria = async (id_categoria) => {
    try {
        const response = await axios.delete(`http://localhost:8080/api/categoria/${id_categoria}`);
        alert(response.data);
        fetchDatos();
    } catch (error) {
        if (error.response) {
            alert(error.response.data);
        } else {
            console.log(error);
        }
    }
}

const registerOk = () => {
    showPopup('popupReg');
    fetchDatos();
}

const categoriasFiltradas = computed(() => {
    return categorias.value.filter(categoria =>
        categoria.nombre.toLowerCase().includes(filtro.value.toLowerCase())
    );
});

onMounted(() => {
    fetchDatos();
});
</script>

<style scoped>
.main-container {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.categorias {
    display: flex;
    flex-direction: column;
    overflow: auto;
    width: 400px;
    height: 400px;
    margin-bottom: 20px;
}

li {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

li:hover {
    background-color: #e9e8e4;
}

.botones {
    display: flex;
    gap: 10px;
}
</style>