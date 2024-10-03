<template>
    <div class="container">
        <div class="main-container">
            <input type="text" v-model="searchQuery" placeholder="Buscar paquete..." />

            <ul class="paquetes" v-show="filteredPaquetes.length">
                <li v-for="paquete in filteredPaquetes" :key="paquete.id_paquete">
                    <span class="nombre-paquete" @click="verDatosPaquete(paquete.id_paquete)">{{ paquete.nombre }}&nbsp;&nbsp;</span>
                    <div class="buttons">
                        <span><button @click="Eliminarpaquete(paquete.id_paquete)">&nbsp;X&nbsp;</button></span>&nbsp;&nbsp;
                        <span>
                            <button @click="showPopup('popupMod', paquete.id_paquete)">Modificar</button>
                            <PopUp v-if="popupMod" v-model:visible="popupMod">
                                <ModificarPaquete :visible="popupMod" :id="paqueteSeleccionado" @update="updatePop" />
                            </PopUp>
                        </span>
                    </div>
                </li>
            </ul>
            <div>
                <div class="datos-paquetes" v-if="detallespaquete">
                    <h3>Detalles de: "{{ detallespaquete.paqueteDTO.nombre }}"</h3>
                    <p><b>Precio</b>: <span class="verde">${{ detallespaquete.paqueteDTO.precio }}</span></p>
                    <p><b>Detalles</b>: {{ detallespaquete.paqueteDTO.detalles }}</p>
                    <h4>Material de entrega</h4>
                    <div v-for="mde in detallespaquete.paqueteMaterialDeEntregaDTOS">
                        <p>{{ mde.material_de_entrega.nombre }} x {{ mde.cantidad }}</p>
                    </div>
                    <h4>Servicios</h4>
                    <div v-for="servicio in detallespaquete.servicioDTOS">
                        <p>{{ servicio.nombre }}</p>
                    </div>
                </div>
                <div class="datos-paquetes" v-else></div>
            </div>
            <ul>
                <li><router-link class="buttonclassic" to="/nuevoPaquete">Nuevo Paquete</router-link></li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import PopUp from './ui/PopUp.vue';
import ModificarPaquete from './Modificar/ModificarPaquete.vue';

const paquetes = ref([]);
const detallespaquete = ref(null);
const emit = defineEmits(['registrado', 'update:visible']);
const popupMod = ref(false);
const paqueteSeleccionado = ref(null);
const searchQuery = ref(''); 

const showPopup = (pop, id_paquete) => {
    if (pop === "popupMod") {
        paqueteSeleccionado.value = id_paquete;
        popupMod.value = !popupMod.value;
    }
}

const updatePop = () => {
    showPopup("popupMod");
}

const fetchDatos = async () => {
    try {
        const serv = await axios.get('http://localhost:8080/api/paquete');
        paquetes.value = await serv.data;
    } catch (error) {
        console.log(error);
    }
}

const Eliminarpaquete = async (id_paquete) => {
    try {
        const response = await axios.delete(`http://localhost:8080/api/paquete/${id_paquete}`);
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

const verDatosPaquete = async (idPaquete) => {
    try {
        const paquete = await axios.get(`http://localhost:8080/api/paquete/completo/${idPaquete}`);
        detallespaquete.value = paquete.data;
    } catch (error) {
        console.log(error);
        detallespaquete.value = null;
    }
}

const filteredPaquetes = computed(() => {
    if (!searchQuery.value) {
        return paquetes.value;
    }
    return paquetes.value.filter(paquete =>
        paquete.nombre.toLowerCase().includes(searchQuery.value.toLowerCase())
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

.paquetes {
    display: flex;
    flex-direction: column;
    overflow: auto;
    width: 400px;
    height: 200px;
    margin-bottom: 20px;
}
.nombre-paquete {
    align-self: baseline;
}

.datos-paquetes {
    min-width: 400px;
    max-width: 400px;
    height: 200px;
    overflow-y: auto;
    outline: .5px solid black;
    border-radius: 5px;
    padding: 5px;
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
.verde {
    color: var(--verde);
    font-weight: bold;
}
</style>