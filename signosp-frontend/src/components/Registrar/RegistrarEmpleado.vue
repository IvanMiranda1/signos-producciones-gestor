<template>
    <div class="main-container">
        <form @submit.prevent="registrarEmpleado">
            <div>
                <label for="nomyape">Nombre y Apellido</label>
                <input type="text" id="nomyape" v-model="empleado.nomyape" required />
            </div>

            <div>
                <label for="correo">Correo</label>
                <input type="email" id="correo" v-model="empleado.correo" required />
            </div>

            <div>
                <label for="telefono">Teléfono</label>
                <input type="text" id="telefono" v-model="empleado.telefono" required />
            </div>


            <div class="specialties">
                <div v-for="(especialidad, index) in selectedEspecialidades" :key="index" class="specialtyContainer">
                    <label for="especialidades">Especialidad {{ index + 1 }}</label>
                    <div>
                        <select v-model="especialidad.id_especialidad">
                            <option v-for="spec in allEspecialidades" :key="spec.id_especialidad"
                                :value="spec.id_especialidad">
                                {{ spec.nombre }}
                            </option>
                        </select>
                        <button type="button" @click="removeEspecialidad(index)">&nbsp;-&nbsp;</button>
                    </div>
                </div>
            </div>

            <button type="button" @click="addEspecialidad">Agregar Especialidad</button>

            <!-- Disponibilidad Horaria del Empleado -->
            <div class="availability">
                <div v-for="(disponibilidad, index) in selectedDisponibilidades" :key="index"
                    class="availabilityContainer">
                    <div>
                        <div>
                            <span for="dia">Día {{ index + 1 }}</span>
                            <select id="dia" v-model="disponibilidad.dia">
                                <option value="Lunes">Lunes</option>
                                <option value="Martes">Martes</option>
                                <option value="Miércoles">Miércoles</option>
                                <option value="Jueves">Jueves</option>
                                <option value="Viernes">Viernes</option>
                                <option value="Sábado">Sábado</option>
                                <option value="Domingo">Domingo</option>
                            </select>
                        </div>
                    </div>
                    <div>
                        <div>
                            <span for="desde">Desde</span>
                            <input type="number" v-model="disponibilidad.desde" placeholder="0930" />
                        </div>
                        <div>
                            <span for="hasta">Hasta</span>
                            <input type="number" v-model="disponibilidad.hasta" placeholder="1730" />
                        </div>
                    </div>
                    <div>
                        <button class="buttonclassic" type="button"
                            @click="removeDisponibilidad(index)">&nbsp;-&nbsp;</button>
                    </div>
                </div>
            </div>

            <button type="button" @click="addDisponibilidad">Agregar Disponibilidad</button>

            <button type="submit">Registrar Empleado</button>
        </form>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import {useRouter} from 'vue-router'
const router = useRouter();

const empleado = ref({
    nomyape: "",
    correo: "",
    telefono: "",
    especialidad: [],
    disponibilidades: []
});

const selectedEspecialidades = ref([]);
const allEspecialidades = ref([]);
const selectedDisponibilidades = ref([]);

const fetchDatos = async () => {
    try {
        const response = await axios.get('http://localhost:8080/api/especialidad');
        allEspecialidades.value = response.data;
    } catch (error) {
        console.error(error);
    }
};

const addEspecialidad = () => {
    selectedEspecialidades.value.push({ id_especialidad: null });
};

const removeEspecialidad = (index) => {
    selectedEspecialidades.value.splice(index, 1);
};

const addDisponibilidad = () => {
    selectedDisponibilidades.value.push({ dia: "", desde: null, hasta: null });
};

const removeDisponibilidad = (index) => {
    selectedDisponibilidades.value.splice(index, 1);
};

const formatHour = (hour) => {
    return hour.toString().padStart(4, '0');
};


const registrarEmpleado = async () => {
    try {
        // Mapear los valores de la id, es como hacer un for basicamente
        empleado.value.especialidad = selectedEspecialidades.value.map(especialidad => (especialidad.id_especialidad));
        empleado.value.disponibilidades = selectedDisponibilidades.value.map(d => ({
            dia: d.dia,
            desde: formatHour(d.desde),
            hasta: formatHour(d.hasta)
        }));
        console.log(empleado.value)
        await axios.post('http://localhost:8080/api/empleado', empleado.value);
        alert('Empleado registrado exitosamente');
        router.push({ name: 'empleados' })

    } catch (error) {
        console.error(error);
    }
};

onMounted(() => {
    fetchDatos();
    addEspecialidad();
    addDisponibilidad();

});
</script>

<style scoped>
.main-container {
    display: flex;
    justify-content: center;
    align-items: center;
}

form {
    display: flex;
    flex-direction: column;
    width: 300px;
}

form>div {
    display: flex;
    flex-direction: column;
}

form>div * {
    width: 100%;
}

.specialties {
    margin-top:5px;
    max-height: 100px;
    min-height: 100px;
    overflow-y: auto;
    margin-bottom: 10px;
    background-color: #f0efea;
    border-radius: 10px;
}

.specialtyContainer>div {
    display: flex;
    justify-content: space-evenly;
}

.specialtyContainer select {
    width: 80%;
}

.specialtyContainer button {
    width: 10%;
    cursor: pointer;
}

/**/

.availability {
    margin-top: 5px;
    max-height: 100px;
    min-height: 100px;
    overflow-y: auto;
    overflow-x: hidden;
    background-color: #f0efea;
    border-radius: 10px;
}

.availabilityContainer {
    display: flex;
    align-items: center;
}

.availabilityContainer>div {
    display: flex;
    margin-bottom: 5px;
}

.availabilityContainer>div>div {
    display: flex;
}

.availabilityContainer>div span {
    display: inline;
    font-size: 12px;
    width: 40px;
    margin-right: -4px;
}

.availabilityContainer>div:nth-child(1) span {
    margin-right: -10px;
}

.availabilityContainer>div input {
    font-size: 12px;
    width: 50px;
}

.availabilityContainer>div select {
    width: 60px;
}
</style>