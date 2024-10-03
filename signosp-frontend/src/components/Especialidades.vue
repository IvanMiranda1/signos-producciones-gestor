<template>
    <div class="main-container">
        <input v-model="searchTerm" placeholder="Buscar especialidad..." />
        <ul class="especialidades" v-show="especialidades">
            <li v-for="especialidad in filteredEspecialidades" :key="especialidad.id_especialidad">
                <span>{{ especialidad.nombre }}</span>
                <div>
                    <span><button @click="EliminarEspecialidad(especialidad.id_especialidad)">&nbsp;X&nbsp;</button></span>
                    <span><button @click="showPopup('popupMod', especialidad)">Modificar</button>
                        <PopUp v-if="popupMod" v-model:visible="popupMod">
                            <GenericModificar :visible="popupMod" tipo="especialidad" :entidad="especialidadSeleccionada"
                                @update="updatePop" />
                        </PopUp>
                    </span>
                </div>
            </li>
        </ul>
        <ul>
            <li>
                <button @click="showPopup('popupReg')">Agregar Especialidad</button>
                <PopUp v-if="popupReg" v-model:visible="popupReg">
                    <Registrarespecialidad :visible="popupReg" @update="registerOk" />
                </PopUp>
            </li>
        </ul>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';

import PopUp from './ui/PopUp.vue';
import Registrarespecialidad from './Registrar/RegistrarEspecialidad.vue';
import GenericModificar from './Modificar/GenericModificar.vue';

const especialidades = ref([]);
const searchTerm = ref('');
const popupReg = ref(false);
const popupMod = ref(false);
const especialidadSeleccionada = ref(null);

const showPopup = (pop, entidad = null) => {
    if (pop === 'popupReg') {
        popupReg.value = !popupReg.value;
    }
    if (pop === 'popupMod') {
        if (entidad != null) {
            especialidadSeleccionada.value = entidad;
            popupMod.value = !popupMod.value;
        } else {
            popupMod.value = !popupMod.value;
        }
    }
};

const updatePop = () => {
    showPopup('popupMod');
    fetchDatos();
};

const fetchDatos = async () => {
    try {
        const response = await axios.get('http://localhost:8080/api/especialidad');
        especialidades.value = response.data;
    } catch (error) {
        console.log(error);
    }
};

const EliminarEspecialidad = async (id_especialidad) => {
    try {
        const response = await axios.delete(`http://localhost:8080/api/especialidad/${id_especialidad}`);
        alert(response.data); // Muestra el mensaje de éxito o error
        fetchDatos();
    } catch (error) {
        if (error.response) {
            alert(error.response.data);  // Muestra el mensaje de error
        } else {
            console.log(error);
        }
    }
};

const registerOk = () => {
    showPopup('popupReg');
    fetchDatos();
};


const filteredEspecialidades = computed(() => {
    return especialidades.value.filter(especialidad =>
        especialidad.nombre.toLowerCase().includes(searchTerm.value.toLowerCase())
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

.especialidades {
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