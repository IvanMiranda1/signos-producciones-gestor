<template>
    <div class="main-container">
        <input v-model="searchTerm" placeholder="Buscar material de entrega..." />
        <ul class="materiales" v-show="materiales">
            <li v-for="material in filteredMaterials" :key="material.id_material_de_entrega">
                <span>{{ material.nombre }}</span>
                <div>
                    <span><button
                            @click="Eliminarmaterial(material.id_material_de_entrega)">&nbsp;X&nbsp;</button></span>
                    <span><button @click="showPopup('popupMod', material)">Modificar</button>
                        <PopUp v-if="popupMod" v-model:visible="popupMod">
                            <GenericModificar :visible="popupMod" tipo="material" :entidad="materialSeleccionado"
                                @update="updatePop" />
                        </PopUp>
                    </span>
                </div>
            </li>
        </ul>
        <ul>
            <li>
                <button @click="showPopup('popupReg')">Agregar Material de Entrega</button>
                <PopUp v-if="popupReg" v-model:visible="popupReg">
                    <Registrarmaterial :visible="popupReg" @update="registerOk" />
                </PopUp>
            </li>
        </ul>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';

import PopUp from './ui/PopUp.vue';
import Registrarmaterial from './Registrar/RegistrarMaterialDeEntrega.vue';
import GenericModificar from './Modificar/GenericModificar.vue';

const materiales = ref([]);
const searchTerm = ref('');
const popupReg = ref(false);
const popupMod = ref(false);
const materialSeleccionado = ref(null);

const showPopup = (pop, entidad = null) => {
    if (pop === 'popupReg') {
        popupReg.value = !popupReg.value;
    }
    if (pop === 'popupMod') {
        if (entidad != null) {
            materialSeleccionado.value = entidad;
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
        const response = await axios.get('http://localhost:8080/api/material');
        materiales.value = response.data;
    } catch (error) {
        console.log(error);
    }
};

const Eliminarmaterial = async (id_material) => {
    try {
        const res = await axios.delete(`http://localhost:8080/api/material/${id_material}`);
        alert(res.data)
        fetchDatos();
    } catch (error) {
        if (error.response) {
            alert(error.response.data);
        } else {
            console.log(error);
        }
    }
};

const registerOk = () => {
    showPopup('popupReg');
    fetchDatos();
};

const filteredMaterials = computed(() => {
    return materiales.value.filter(material =>
        material.nombre.toLowerCase().includes(searchTerm.value.toLowerCase())
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

.materiales {
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