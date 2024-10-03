<template>
    <div class="container">
        <div class="main-container">
            <input type="text" v-model="searchQuery" placeholder="Buscar cliente..." />

            <ul class="clientes" v-show="filteredclientes.length">
                <li v-for="cliente in filteredclientes" :key="cliente.id_cliente">
                    <span @click="verDatoscliente(cliente)">{{ cliente.nomyape }}&nbsp;&nbsp;</span>
                    <div>
                        <span><button @click="Eliminarcliente(cliente.id_cliente)">&nbsp;X&nbsp;</button></span>&nbsp;&nbsp;
                        <span>
                            <button @click="showPopup('popupMod', cliente.id_cliente)">Modificar</button>
                            <PopUp v-if="popupMod" v-model:visible="popupMod">
                                <ModificarCliente :visible="popupMod" :id="clienteSeleccionado" @update="updatePop"/>
                            </PopUp>
                        </span>
                    </div>
                </li>
            </ul>
            <div>
                <div class="datos-clientes" v-if="detallesCliente" v-bind="verDatoscliente">
                    <h3>Nombre del cliente: <br>&nbsp;&nbsp;"{{ detallesCliente.nomyape }}"</h3>
                    <p>Correo: <br>&nbsp;&nbsp; {{ detallesCliente.correo }}</p>
                    <p>Teléfono : <br>&nbsp;&nbsp; {{ detallesCliente.telefono }}</p>
                </div>
                <div class="datos-clientes" v-else></div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import PopUp from './ui/PopUp.vue';
import ModificarCliente from './Modificar/ModificarCliente.vue';

const clientes = ref([]);
const detallesCliente = ref(null);
const emit = defineEmits(['registrado', 'update:visible']);
const popupMod = ref(false);
const clienteSeleccionado = ref(null);
const searchQuery = ref(''); 

const showPopup = (pop, id_cliente) => {
    if (pop === "popupMod") {
        clienteSeleccionado.value = id_cliente;
        popupMod.value = !popupMod.value;
    }
}

const updatePop = () => {
    showPopup("popupMod");
    fetchDatos();
}

const fetchDatos = async () => {
    try {
        const serv = await axios.get('http://localhost:8080/api/cliente');
        clientes.value = await serv.data;
    } catch (error) {
        console.log(error);
    }
}

const Eliminarcliente = async (id_cliente) => {
    try {
        const response = await axios.delete(`http://localhost:8080/api/cliente/${id_cliente}`);
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

const verDatoscliente = (cliente) => {
        detallesCliente.value = cliente;
}

const filteredclientes = computed(() => {
    if (!searchQuery.value) {
        return clientes.value;
    }
    return clientes.value.filter(cliente =>
        cliente.nomyape.toLowerCase().includes(searchQuery.value.toLowerCase())
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

.clientes {
    display: flex;
    flex-direction: column;
    overflow: auto;
    width: 400px;
    height: 200px;
    margin-bottom: 20px;
}


.datos-clientes {
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