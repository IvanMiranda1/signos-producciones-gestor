<template>
    <div class="main-container">
        <!-- Campo de búsqueda -->
        <input type="text" v-model="searchQuery" placeholder="Buscar servicio..." />

        <ul class="servicios" v-show="filteredServicios.length">
            <li v-for="servicio in filteredServicios" :key="servicio.id_servicio">
                <span>{{ servicio.nombre }}</span>
                <div>
                    <span><button @click="EliminarServicio(servicio.id_servicio)">&nbsp;X&nbsp;</button></span>
                    <span><button @click="showPopup('popupMod', servicio)">Modificar</button>
                        <PopUp v-if="popupMod" v-model:visible="popupMod">
                            <GenericModificar :visible="popupMod" tipo="servicio" :entidad="servicioSeleccionado"
                                @update="updatePop" />
                        </PopUp>
                    </span>
                </div>
            </li>
        </ul>
        <ul>
            <li>
                <button @click="showPopup('popupReg')">Agregar Servicio</button>
                <PopUp v-if="popupReg" v-model:visible="popupReg">
                    <RegistrarServicio :visible="popupReg" @update="registerOk" />
                </PopUp>
            </li>
        </ul>
    </div>
</template>


<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';

import PopUp from './ui/PopUp.vue';
import RegistrarServicio from './Registrar/RegistrarServicio.vue';
import GenericModificar from './Modificar/GenericModificar.vue';

const servicios = ref([]);
const searchQuery = ref(''); // Nueva refe para la busqueda
const emit = defineEmits(['registrado', 'update:visible']);
const popupReg = ref(false);
const popupMod = ref(false);
const servicioSeleccionado = ref(null);

const showPopup = (pop, entidad = null) => {
    if (pop === "popupReg") {
        popupReg.value = !popupReg.value;
    }
    if (pop === "popupMod") {
        if (entidad != null) {
            servicioSeleccionado.value = entidad;
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
        const serv = await axios.get('http://localhost:8080/api/servicio');
        servicios.value = await serv.data;
    } catch (error) {
        console.log(error);
    }
}

const EliminarServicio = async (id_servicio) => {
    try {
        const res = await axios.delete(`http://localhost:8080/api/servicio/${id_servicio}`);
        alert(res.data);
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


const filteredServicios = computed(() => {
    if (!searchQuery.value) {
        return servicios.value;
    }
    return servicios.value.filter(servicio =>
        servicio.nombre.toLowerCase().includes(searchQuery.value.toLowerCase())
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

.servicios {
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