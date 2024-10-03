<template>
    <div class="container">
        <div class="main-container">
            <input type="text" v-model="searchQuery" placeholder="Buscar empleado..." />

            <ul class="empleados" v-show="filteredEmpleados.length">
                <li v-for="empleado in filteredEmpleados" :key="empleado.id_empleado">
                    <span @click="verDatosempleado(empleado.id_empleado)">{{ empleado.nomyape }}&nbsp;&nbsp;</span>
                    <div>
                        <span><button @click="Eliminarempleado(empleado.id_empleado)">&nbsp;X&nbsp;</button></span>&nbsp;&nbsp;
                        <span>
                            <button @click="showPopup('popupMod', empleado.id_empleado)">Modificar</button>
                            <PopUp v-if="popupMod" v-model:visible="popupMod">
                                <ModificarEmpleado :visible="popupMod" :id="empleadoSeleccionado" @update="updatePop"/>
                            </PopUp>
                        </span>
                    </div>
                </li>
            </ul>
            <div>
                <div class="datos-empleados" v-if="detallesempleado">
                    <h3>Nombre del empleado "{{ detallesempleado.nomyape }}"</h3>
                    <p>Correo: {{ detallesempleado.correo }}</p>
                    <p>Teléfono : {{ detallesempleado.telefono }}</p>
                    <h4>Especialidades</h4>
                    <div v-for="especialidad in detallesempleado.especialidadDTO" :key="especialidad.id">
                        <p>{{ especialidad.nombre }}</p>
                    </div>
                    <h4>Disponibilidad</h4>
                    <div v-for="dispo in detallesempleado.disponibilidadDTO" :key="dispo.id">
                        <div v-if="dispo.dia == 'Lunes'">{{ dispo.dia }} de {{ formatearHorario(dispo.desde) }} - {{ formatearHorario(dispo.hasta) }}</div>
                        <div v-if="dispo.dia == 'Martes'">{{ dispo.dia }} de {{ formatearHorario(dispo.desde) }} - {{ formatearHorario(dispo.hasta) }}</div>
                        <div v-if="dispo.dia == 'Miércoles'">{{ dispo.dia }} de {{ formatearHorario(dispo.desde) }} - {{ formatearHorario(dispo.hasta) }}</div>
                        <div v-if="dispo.dia == 'Jueves'">{{ dispo.dia }} de {{ formatearHorario(dispo.desde) }} - {{ formatearHorario(dispo.hasta) }}</div>
                        <div v-if="dispo.dia == 'Viernes'">{{ dispo.dia }} de {{ formatearHorario(dispo.desde) }} - {{ formatearHorario(dispo.hasta) }}</div>
                        <div v-if="dispo.dia == 'Sábado'">{{ dispo.dia }} de {{ formatearHorario(dispo.desde) }} - {{ formatearHorario(dispo.hasta) }}</div>
                        <div v-if="dispo.dia == 'Domingo'">{{ dispo.dia }} de {{ formatearHorario(dispo.desde) }} - {{ formatearHorario(dispo.hasta) }}</div>
                    </div>
                </div>
                <div class="datos-empleados" v-else></div>
            </div>
            <ul>
                <li><router-link class="buttonclassic" to="/nuevoempleado">Nuevo empleado</router-link></li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import PopUp from './ui/PopUp.vue';
import ModificarEmpleado from './Modificar/ModificarEmpleado.vue';

const empleados = ref([]);
const detallesempleado = ref(null);
const emit = defineEmits(['registrado', 'update:visible']);
const popupMod = ref(false);
const empleadoSeleccionado = ref(null);
const searchQuery = ref(''); 

const showPopup = (pop, id_empleado) => {
    if (pop === "popupMod") {
        empleadoSeleccionado.value = id_empleado;
        popupMod.value = !popupMod.value;
    }
}

const updatePop = () => {
    showPopup("popupMod");
}

const fetchDatos = async () => {
    try {
        const serv = await axios.get('http://localhost:8080/api/empleado');
        empleados.value = await serv.data;
    } catch (error) {
        console.log(error);
    }
}

const Eliminarempleado = async (id_empleado) => {
    try {
        const response = await axios.delete(`http://localhost:8080/api/empleado/${id_empleado}`);
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

const ordenarDisponibilidad = (disponibilidades) => {
    const ordenDias = ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"];
    return disponibilidades.sort((a, b) => ordenDias.indexOf(a.dia) - ordenDias.indexOf(b.dia));
}

const verDatosempleado = async (idempleado) => {
    try {
        const empleado = await axios.get(`http://localhost:8080/api/empleado/completo/${idempleado}`);
        detallesempleado.value = empleado.data;
        detallesempleado.value.disponibilidadDTO = ordenarDisponibilidad(detallesempleado.value.disponibilidadDTO);
    } catch (error) {
        console.log(error);
        detallesempleado.value = null;
    }
}

const formatearHorario = (horario) => {
    const res = horario.toString().padStart(4, '0').slice(0, 2) + ":" + horario.toString().padStart(4, '0').slice(2) + "hs";
    return res;
}


const filteredEmpleados = computed(() => {
    if (!searchQuery.value) {
        return empleados.value;
    }
    return empleados.value.filter(empleado =>
        empleado.nomyape.toLowerCase().includes(searchQuery.value.toLowerCase())
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

.empleados {
    display: flex;
    flex-direction: column;
    overflow: auto;
    width: 400px;
    height: 200px;
    margin-bottom: 20px;
}


.datos-empleados {
    min-width: 400px;
    max-width: 400px;
    height: 200px;
    overflow-y: auto;
    outline: .5px solid black;
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