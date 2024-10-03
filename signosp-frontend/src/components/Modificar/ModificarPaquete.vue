<template>
    <div v-if="visible">
        <form @submit.prevent="modificarPaquete">
            <div class="content">
                <div class="leftside">
                    <div>
                        <label for="nombre">Nombre</label>
                        <input type="text" id="nombre" v-model="paquete.nombre" required />
                    </div>
                    <div>
                        <label for="precio">Precio</label>
                        <input type="number" id="precio" v-model="paquete.precio" required />
                    </div>
                    <div>
                        <label for="detalles">Detalles</label>
                        <textarea id="detalles" v-model="paquete.detalles"></textarea>
                    </div>
                </div>
                <div class="rightside">
                    <div class="serviceOverflow">
                        <div v-for="(servicio, index) in selectedServicios" :key="index" class="serviceContainer">
                            <label for="servicios">Servicio {{ index + 1 }}</label>
                            <div>
                                <select v-model="servicio.id_servicio">
                                    <option v-for="service in allServicios" :key="service.id_servicio"
                                        :value="service.id_servicio">
                                        {{ service.nombre }}
                                    </option>
                                </select>
                                <button class="buttonclassic" type="button" @click="removeServicio(index)">
                                    &nbsp;-&nbsp;
                                </button>
                            </div>
                        </div>
                    </div>
                    <div>
                        <button class="buttonclassic" type="button" @click="addServicio">Agregar Servicio</button>
                    </div>
                    <div class="mdeOverflow">
                        <div v-for="(material, index) in selectedMateriales" :key="index" class="materialContainer">
                            <label for="materiales">Material de entrega {{ index + 1 }}</label>
                            <div>
                                <select v-model="material.id_material_de_entrega">
                                    <option v-for="material in allMateriales" :key="material.id_material_de_entrega"
                                        :value="material.id_material_de_entrega">
                                        {{ material.nombre }}
                                    </option>
                                </select>
                                <input type="number" v-model="material.cantidad" min="1" class="cantidadInput" />
                                <button class="buttonclassic" type="button" @click="removeMaterial(index)">
                                    &nbsp;-&nbsp;
                                </button>
                            </div>
                        </div>
                    </div>
                    <div>
                        <button class="buttonclassic" type="button" @click="addMaterial">Agregar Material</button>
                    </div>
                </div>
            </div>
            <div>
                <button class="buttonclassic" type="submit">Guardar Cambios</button>
            </div>
        </form>
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';

const paquete = ref({
    nombre: "",
    precio: 0,
    detalles: "",
    servicios: [],
    materiales: []
});

const paqueteMod = ref({
    paqueteDTO: {},
    paqueteMaterialDeEntregaDTOS: [],
    servicioDTOS: []
})

const selectedServicios = ref([]);
const selectedMateriales = ref([]);
const allMateriales = ref([]);
const allServicios = ref([]);
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

const fetchDatos = async (idPaquete) => {
    try {
        const resPaquete = await axios.get(`http://localhost:8080/api/paquete/completo/${idPaquete}`);
        paquete.value = resPaquete.data.paqueteDTO;

        const fetchServ = await axios.get('http://localhost:8080/api/servicio');
        allServicios.value = fetchServ.data;

        const fetchMateriales = await axios.get('http://localhost:8080/api/material');
        allMateriales.value = fetchMateriales.data;

        selectedServicios.value = resPaquete.data.servicioDTOS.map(servicio => ({
            id_servicio: servicio.id_servicio
        }));

        selectedMateriales.value = resPaquete.data.paqueteMaterialDeEntregaDTOS.map(material => ({
            id_material_de_entrega: material.material_de_entrega.id_material_de_entrega,
            cantidad: material.cantidad,
            id_paquete_material_de_entrega: material.id_paquete_material_de_entrega
        }));
    } catch (error) {
        console.log(error);
    }
};

const addServicio = () => {
    selectedServicios.value.push({ id_servicio: null });
};

const removeServicio = (index) => {
    selectedServicios.value.splice(index, 1);
};

const addMaterial = () => {
    selectedMateriales.value.push({ id_material_de_entrega: null, cantidad: 1 });
};

const removeMaterial = (index) => {
    selectedMateriales.value.splice(index, 1);
};

const modificarPaquete = async () => {
    try {
        paqueteMod.value.paqueteDTO = paquete.value;
        paqueteMod.value.servicioDTOS = selectedServicios.value.map(servicio => ({
            id_servicio: servicio.id_servicio,
            nombre: ""
        }));

        paqueteMod.value.paqueteMaterialDeEntregaDTOS = selectedMateriales.value.map(material => ({
            material_de_entrega: {
                id_material_de_entrega:material.id_material_de_entrega
            },
            cantidad:material.cantidad
        }));
        console.log(paqueteMod.value)

        await axios.put(`http://localhost:8080/api/paquete`, paqueteMod.value);
        emits('update');
    } catch (error) {
        console.error(error);
    }
};

watch(() => props.visible, (newValue) => {
    if (newValue) {
        fetchDatos(props.id);
    }
});

onMounted(() => {
    if (props.visible) {
        fetchDatos(props.id);
    }
});
</script>

<style scoped>
.main-container {
    font-weight: 400;
    display: flex;
    justify-content: center;
    align-items: center;
}

textarea {
    resize: none;
    height: 200px
}

form {
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 100%;
}

.content {
    display: flex;
}

form .leftside>div,
form .rightside>div {
    display: flex;
    flex-direction: column;
    width: 300px;
    margin-bottom: 10px;
}


.serviceContainer {
    width: 300px;
    flex-direction: column;
}

.serviceContainer>div {
    width: 300px;
    display: flex;
    justify-content: space-evenly
}

.serviceContainer select {
    width: 250px;
}

.serviceContainer .buttonclassic {
    width: 30px;
    font-weight: bolder;
}

form>div>div>div>button {
    margin: auto;
}

.serviceOverflow {
    display: flex;
    flex-direction: column;
    align-items: center;
    min-width: 350px;
    min-height: 200px;
    max-height: 200px;
    overflow-y: auto;
}

.mdeOverflow {
    display: flex;
    flex-direction: column;
    align-items: center;
    min-width: 350px;
    min-height: 200px;
    max-height: 200px;
    overflow-y: auto;
}

.materialContainer {
    width: 300px;
    flex-direction: column;
}

.materialContainer>div {
    width: 300px;
    display: flex;
    justify-content: space-evenly
}

.materialContainer select {
    width: 250px;
}

.materialContainer .buttonclassic {
    width: 30px;
    font-weight: bolder;
}

.cantidadInput {
    width: 50px;
    text-align: center;
}

.leftside,
.rightside {
    display: flex;
    flex-direction: column;
}
</style>