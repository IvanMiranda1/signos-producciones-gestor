<template>
    <div v-if="visible">
        <form @submit.prevent="modificar">
            <div>
                <label for="nomyape">Nombre y Apellido</label>
                <input type="text" id="nomyape" v-model="cliente.nomyape" required />
            </div>

            <div>
                <label for="correo">Correo</label>
                <input type="email" id="correo" v-model="cliente.correo" required />
            </div>

            <div>
                <label for="telefono">Teléfono</label>
                <input type="text" id="telefono" v-model="cliente.telefono" required />
            </div>
            <div class="buttoncontainer"><button class="buttonclassic" type="submit">Guardar Cambios</button></div>
        </form>
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';

const isVisible = ref(false);
const cliente = ref({
    id_cliente: 0,
    nomyape: "",
    correo: "",
    telefono: ""
});

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
const fetchDatos = async (idCliente) => {
    try {
        const resCliente = await axios.get(`http://localhost:8080/api/cliente/${idCliente}`);
        cliente.value = resCliente.data;
        
    } catch (error) {
        console.log(error);
    }
};

const modificar = async () => {
    try {
        cliente.value.id_cliente = props.id;
        console.log(cliente.value)
        await axios.put(`http://localhost:8080/api/cliente`, cliente.value);
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
