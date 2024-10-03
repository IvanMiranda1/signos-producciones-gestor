<template>
    <div v-if="visible">
        <form @submit.prevent="modificar">
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
                        <select v-model="especialidad.id_especialidad" >
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

            <div class="buttoncontainer"><button class="buttonclassic" type="submit">Guardar Cambios</button></div>
        </form>
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';

const empleado = ref({
    id_empleado: 0,
    nomyape: "",
    correo: "",
    telefono: "",
    especialidadDTO: [],
    disponibilidadDTO: []
});

const selectedEspecialidades = ref([]);
const allEspecialidades = ref([]);
const selectedDisponibilidades = ref([]);
const isVisible = ref(false);

const emits = defineEmits(['update']);
const props = defineProps({
    visible: {
        type: Boolean,
        required: true
    },
    id: {
        type: Number,
        required: true
    }
});
const fetchDatos = async (idEmpleado) => {
    try {
        const resEmpleado = await axios.get(`http://localhost:8080/api/empleado/completo/${idEmpleado}`);
        empleado.value = resEmpleado.data;
        
        const resEspecialidades = await axios.get(`http://localhost:8080/api/especialidad`);
        allEspecialidades.value = resEspecialidades.data;

        selectedEspecialidades.value = empleado.value.especialidadDTO.map(especialidad => ({
            id_especialidad: especialidad.id_especialidad
        }));

        selectedDisponibilidades.value = empleado.value.disponibilidadDTO.map(disp => ({
            dia: disp.dia,
            desde: formatHour(parseInt(disp.desde)),
            hasta: formatHour(parseInt(disp.hasta))
        }));
    } catch (error) {
        console.log(error);
    }
};

const addEspecialidad = () => {
    selectedEspecialidades.value.push({ id_especialidad: null, nombre: "" });
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

const modificar = async () => {
    try {
        empleado.value.id_empleado = props.id;
        empleado.value.especialidadDTO = selectedEspecialidades.value.map(especialidad => ({
            id_especialidad : especialidad.id_especialidad,
            nombre : ""
        }));
        empleado.value.disponibilidadDTO = selectedDisponibilidades.value.map(d => ({
            dia: d.dia,
            desde: formatHour(d.desde),
            hasta: formatHour(d.hasta)
        }));
        console.log(empleado.value)
        await axios.put(`http://localhost:8080/api/empleado`, empleado.value);
        emits('update');
    } catch (error) {
        console.error(error);
    }
};

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

form {
    display: flex;
    flex-direction: column;
    width: 300px;
    padding: 20px;
    background: white;
    border: 1px solid #ccc;
    min-width:400px;
}

input,
select,
button {
    margin: 5px 0;
}

.buttonclassic {
    border:1px solid #555;
    padding: 5px;
    cursor: pointer;
    margin-top: 10px;
    width: 50px;
    height: 20px;
    margin: auto;
}


.buttoncontainer {
    display: flex;
    justify-content: center;
}
.buttoncontainer .buttonclassic {
    display: flex;
    justify-content: center;
    width: 80%;
    text-align: center;
    height: 28px;

}

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
    margin-top: 5px;
    max-height: 100px;
    min-height: 100px;
    overflow-y: auto;
    margin-bottom: 10px;
    background-color: #f0efea3d;
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
    background-color: #f0efea3d;
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
    display:flex;
    flex-direction:column;
    justify-content: center;
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
