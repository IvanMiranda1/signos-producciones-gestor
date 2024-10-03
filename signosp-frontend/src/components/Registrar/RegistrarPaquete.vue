<template>
    <div class="main-container">
        <form @submit.prevent="registrarPaquete">

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
                        <button class="buttonclassic" type="button" @click="addServicio">&nbsp;+&nbsp;</button>
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
                        <button class="buttonclassic" type="button" @click="addMaterial">&nbsp;+&nbsp;</button>
                    </div>
                </div>
            </div>
            <div>
                <button class="buttonclassic" type="submit">Guardar</button>
            </div>
        </form>
    </div>

</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router'
const router = useRouter();

const paquete = ref({
    nombre: "",
    precio: 0,
    detalles: "",
    servicios: [],
    materiales: []
})
const selectedServicios = ref([])
const selectedMateriales = ref([])
const allMateriales = ref([])
const allServicios = ref([])

const fetchDatos = async () => {
    try {
        const fetchServ = await axios.get('http://localhost:8080/api/servicio')
        allServicios.value = await fetchServ.data;
        const fetchMateriales = await axios.get('http://localhost:8080/api/material')
        allMateriales.value = await fetchMateriales.data;
    } catch (error) {
        console.log(error)
    }
}

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
}

const registrarPaquete = async () => {
    try {
        console.log(selectedServicios)
        for (const servicio of selectedServicios.value) {
            paquete.value.servicios.push(servicio.id_servicio);
        }
        for(const material of selectedMateriales.value) {
            console.log(material)
            paquete.value.materiales.push(material);
        }
        console.log(paquete.value)
        await axios.post("http://localhost:8080/api/paquete", paquete.value);
        alert("Paquete registrado.");
        router.push({ name: 'home' });
    } catch (error) {
        console.log(error)
    }
    
}

onMounted(() => {
    fetchDatos();
    addServicio();
    addMaterial();
})

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