<template>
    <div class="main-container">
        <form @submit.prevent="registrar">
            <div class="form-header">
                <input id="titulo" type="text" v-model="evento.titulo" placeholder="Titulo del nuevo evento">
            </div>
            <div class="container">
                <div class="leftside">
                    <h2>Datos del evento</h2>
                    <div>
                        <label for="fecha">Fecha:</label>
                        <input type="date" name="fecha" id="fecha" v-model="evento.fecha">
                    </div>
                    <div>
                        <label for="categoria">Categoria:</label>
                        <select id="categoria" v-model="evento.id_categoria">
                            <option v-for="categoria in categorias" :key="categoria.id_categoria"
                                :value="categoria.id_categoria">
                                {{ categoria.nombre }}
                            </option>
                        </select>
                    </div>
                    <h2>Datos del cliente
                        <select value="clientes" name="cliente" id="cliente" @change="completarCliente"
                            v-model="selectedCliente">
                            <option value="" selected>Clientes</option>
                            <option v-for="cliente in clientes" :key="cliente.id_cliente" :value="cliente.id_cliente">
                                {{ cliente.nomyape }} *ID:{{ cliente.id_cliente }}
                            </option>
                        </select>
                    </h2>
                    <div>
                        <label for="nomyape">Nombre y apellido</label>
                        <input type="text" name="nomyape" id="nomyape" v-model="evento.cliente.nomyape">
                    </div>
                    <h3>Contacto</h3>
                    <div>
                        <label for="email">Email</label>
                        <input type="email" name="email" id="email" v-model="evento.cliente.correo">
                    </div>
                    <div>
                        <label for="">Telefono</label>
                        <input type="number" name="telefono" id="telefono" v-model="evento.cliente.telefono">
                    </div>
                    <h2>Forma de pago</h2>
                    <div>
                        <div>
                            <label for="formadepago">Contado: </label>
                            <input type="radio" name="formadepago" value="Contado" v-model="evento.pago.forma_de_pago">
                            <label for="formadepago">Tarjeta: </label>
                            <input type="radio" name="formadepago" value="Tarjeta" v-model="evento.pago.forma_de_pago">
                        </div>
                        <label for="cantcuotas">Cantidad de cuotas:</label>
                        <select name="cantcuotas" id="cantcuotas" v-model="evento.pago.cant_cuotas">
                            <option value="1">1</option>
                            <option value="3">3</option>
                            <option value="6">6</option>
                            <option value="12">12</option>
                        </select>
                    </div>
                </div>
                <div class="rightside">
                    <h2>Paquete Seleccionado</h2>
                    <div v-if="paquetes">
                        <div class="paquetes">
                            <div v-for="paquete in paquetes" :key="paquete.id_paquete">
                                <input type="radio" name="paquete" :value="paquete.id_paquete"
                                    :id="'paquete-' + paquete.id_paquete" @change="verDatosPaquete(paquete.id_paquete)"
                                    v-model="evento.id_paquete">
                                <label :for="'paquete-' + paquete.id_paquete">{{ paquete.nombre }}</label>
                            </div>
                        </div>
                        <div class="datos-paquetes" v-if="detallespaquete">
                            <h3>Detalles del paquete "{{ detallespaquete.paqueteDTO.nombre }}"</h3>
                            <p>Precio: {{ detallespaquete.paqueteDTO.precio }}</p>
                            <p>Detalles: {{ detallespaquete.paqueteDTO.detalles }}</p>
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
                    <h2>Personal asignado</h2>
                    <div>
                        <div class="empleados">
                            <div v-for="empleado in empleados" :key="empleado.id">
                                <input type="checkbox" :id="'empleado-' + empleado.id_empleado"
                                    v-model="selectedEmpleados" :value="empleado.id_empleado"
                                    @change="verDisponibilidad(empleado.id_empleado)">
                                <label :for="'empleado-' + empleado.id_empleado">{{ empleado.nomyape }}</label>
                            </div>
                        </div>
                        <table v-if="dispEmpleados">
                            <tr>
                                <th>Viernes</th>
                                <div v-for="dispo in dispEmpleados">
                                    <div v-if="dispo.dia == 'Viernes'">
                                        <div v-if="dispo.desde == 0 && dispo.hasta == 0">
                                            <td>Dia Libre.</td>
                                        </div>
                                        <div v-else>
                                            <td>Desde: {{ formatearHorario(dispo.desde) }} Hasta
                                                {{ formatearHorario(dispo.hasta) }}</td>
                                        </div>

                                    </div>
                                </div>
                            </tr>
                            <tr>
                                <th>Sábado</th>
                                <div v-for="dispo in dispEmpleados">
                                    <div v-if="dispo.dia == 'Sábado'">
                                        <div v-if="dispo.desde == 0 && dispo.hasta == 0">
                                            <td>Dia libre</td>
                                        </div>
                                        <div v-else>
                                            <td>Desde: {{ formatearHorario(dispo.desde) }} Hasta
                                                {{ formatearHorario(dispo.hasta) }}</td>
                                        </div>

                                    </div>
                                </div>
                            </tr>
                            <tr>
                                <th>Domingo</th>
                                <div v-for="dispo in dispEmpleados">
                                    <div v-if="dispo.dia == 'Domingo'">
                                        <div v-if="dispo.desde == 0 && dispo.hasta == 0">
                                            <td>Dia libre</td>
                                        </div>
                                        <div v-else>
                                            <td>Desde: {{ formatearHorario(dispo.desde) }} Hasta
                                                {{ formatearHorario(dispo.hasta) }}</td>
                                        </div>

                                    </div>
                                </div>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <div class="submitcontainer">
                <input class="buttonclassic" type="submit" value="Registrar Evento">
            </div>
        </form>
    </div>
</template>

<script setup>

import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router'
import { ajustarFecha } from '../../main';
const router = useRouter();

const evento = ref({
    titulo: "",
    fecha: "",
    cliente: {
        id_cliente: null,
        nomyape: "",
        correo: "",
        telefono: ""
    },
    id_paquete: 0,
    id_categoria: 0,
    pago: {
        forma_de_pago: "",
        cant_cuotas: 0
    },
    empleados: []
});

const categorias = ref([]);
const clientes = ref({});
const paquetes = ref([]);
const detallespaquete = ref(null);
const empleados = ref([]);
const dispEmpleados = ref([]);
const selectedEmpleados = ref([])
const selectedCliente = ref(0);


const props = defineProps({
    id: {
        type: Number,
        required: true
    }
});

const registrar = async () => {
    try {
        evento.value.empleados = selectedEmpleados.value;

        try {
            const existeCli = await axios.post(`http://localhost:8080/api/cliente/existe`, evento.value.cliente)
            evento.value.cliente = existeCli.data;
        } catch (error) {
            if (error.response) {
                alert(error.response.data);
            } else {
                console.log(error);
            }
        }

        evento.value.fecha = new Date(evento.value.fecha).toISOString().split('T')[0];
        await axios.post(`http://localhost:8080/api/evento`, evento.value)
        //Navegacion
        alert("Evento registrado.");
        evento.value = {
            titulo: "",
            fecha: "",
            cliente: {
                id_cliente: null,
                nomyape: "",
                correo: "",
                telefono: ""
            },
            id_paquete: 0,
            id_categoria: 0,
            pago: {
                forma_de_pago: "",
                cant_cuotas: 0
            },
            empleados: []
        }
        router.push({ name: 'home' })
    } catch (error) {
        console.log(error)
    }
}

const completarCliente = async () => {
    try {
        console.log(selectedCliente.value)
        const cli = await axios.get(`http://localhost:8080/api/cliente/${selectedCliente.value}`)
        evento.value.cliente.id_cliente = cli.data.id_cliente;
        evento.value.cliente.nomyape = cli.data.nomyape;
        evento.value.cliente.correo = cli.data.correo;
        evento.value.cliente.telefono = cli.data.telefono;
    } catch (error) {
        console.log(error);
        evento.value.cliente.id_cliente = null;
        evento.value.cliente.nomyape = "";
        evento.value.cliente.correo = "";
        evento.value.cliente.telefono = "";
    }
}

const fetchDatos = async () => {
    try {
        console.log("Fetching...")

        const fetchPaquetes = await axios.get(`http://localhost:8080/api/paquete`);
        paquetes.value = await fetchPaquetes.data;

        verDatosPaquete(evento.value.id_paquete);

        const fetchClientes = await axios.get(`http://localhost:8080/api/cliente`)
        clientes.value = await fetchClientes.data;

        const fetchEmpleados = await axios.get(`http://localhost:8080/api/empleado`)
        empleados.value = await fetchEmpleados.data;

        const fetchCategorias = await axios.get(`http://localhost:8080/api/categoria`)
        categorias.value = await fetchCategorias.data;

    } catch (error) {
        console.log(error)
    }
}

const verDatosPaquete = async (idPaquete) => {
    try {
        const paquete = await axios.get(`http://localhost:8080/api/paquete/completo/${idPaquete}`);
        detallespaquete.value = paquete.data;
    } catch (error) {
        console.log(error)
        detallespaquete.value = null;
    }
}

const verDisponibilidad = async (idEmpleado) => {
    try {
        const disponibilidad = await axios.get(`http://localhost:8080/api/disponibilidad/empleado/${idEmpleado}`);
        dispEmpleados.value = disponibilidad.data;
    } catch (error) {
        console.log(error);
        dispEmpleados.value = [];
    }
}

const formatearHorario = (horario) => {
    const res = horario.toString().padStart(4, '0').slice(0, 2) + ":" + horario.toString().padStart(4, '0').slice(2) + "hs";
    return res;
}

onMounted(() => {
    fetchDatos()
})
/** si quiero mostrar los dias trabajados de los empleados en cierto evento, aunque el evento es solo un dia, se incluyen los dias de grabacion en la pre-producccion,etc primero debo tener el id del empleado y del evento */


</script>

<style scoped>
.main-container {
    font-weight: 400;
    display: flex;
    justify-content: center;
    align-items: center;
}

form {
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 100%;

}

#titulo {
    color: var(--negro);
    font-size: x-large;
    border: none;
    background-color: transparent;
    text-align: center;
    text-decoration: underline;
}

#titulo:focus {
    outline: none;
}

input:not(#titulo) {
    background-color: transparent;
    outline: auto solid var(--negro);
    width: max-content;
}

.container {
    color: var(--negro);
    max-width: 800px;
    display: grid;
    grid-template-columns: 1fr 1fr;
    overflow-y: visible;
}

.leftside,
.rightside {
    display: flex;
    flex-direction: column;
    align-items: center;
    min-width: 400px;
    max-width: 400px;
}

.leftside>div,
.rightside>div {
    display: flex;
    flex-direction: column;
}

.leftside {
    border-right: 1px solid black;
}

.rightside {
    border-left: 1px solid black;
}

.paquetes {
    display: flex;
    flex-direction: column;
    overflow-y: auto;
    height: 60px;
    margin-bottom: 10px;
}

.paquetes>div {
    font-size: small;
    display: flex;
    align-items: center;
    background-color: #dfdfdf40;
    margin: 2px;
}

.datos-paquetes {
    min-width: 350px;
    max-width: 350px;
    height: 140px;
    overflow-y: auto;
    outline: .5px solid black;
}

.empleados {
    width: 100%;
    height: 70px;
    overflow-y: scroll;
    display: flex;
    flex-wrap: wrap;
    overflow: auto;
    margin: 0;
}

.empleados>div {
    margin-right: 20px;
    height: 30px;
    padding-right: 10px;
}

input {
    color: black;
}

th {
    font-weight: 400;
    border-right: 1px solid var(--negro);
}

tr {
    outline: .5px solid var(--negro);
}

.submitcontainer {
    display: flex;
    width: 100%;
    justify-content: center;
}
</style>