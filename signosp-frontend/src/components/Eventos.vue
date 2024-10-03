<template>
    <div class="eventosContainer">
        <h2>Año {{ year }}</h2>
        <button @click="prevMonth" class="buttonclassic">Anterior</button>
        <button @click="nextMonth" class="buttonclassic">Siguiente</button>

        <div v-if="eventos.length > 0" v-for="evento in eventos" :key="evento.id_evento">
            <div class="evento-datos"
                v-bind:class="{ 'eventoFinalizado': evento.evento?.estado, 'eventoPendiente': !evento.evento?.estado }">
                <h3>{{ evento.evento?.titulo }}</h3>
                <div class="EventoContainer">
                    <div class="containerDatos">
                        <p><b>Estado:</b> <span
                                v-bind:class="{ 'spanfinalizado': evento.evento?.estado, 'spanpendiente': !evento.evento?.estado }">{{
            evento.evento?.estado ? "Finalizado" : "Pendiente" }}</span></p>
                        <p><b>Fecha:</b> {{ ajustarFecha(evento.evento?.fecha) }}</p>
                        <p><b>Cliente:</b> {{ getCliente(evento.evento?.id_cliente) }}</p>
                        <p><b>Paquete:</b> {{ getPaquete(evento.evento?.id_paquete) }}</p>
                        <p><b>Cantidad de Cuotas:</b> {{ evento.pagoConCuotas?.pagoDTO?.cant_cuotas }}</p>
                        <p><b>Cuotas pagadas:</b> {{ countCuotas(evento.pagoConCuotas?.cuotas) }}</p>

                    </div>
                    <div class="eventoOptions">
                        <div class="card-options">
                            <div class="tooltip" @mouseover="showTooltip($event)" @mousemove="moveTooltip($event)"
                                @mouseleave="hideTooltip">
                                <div>
                                    <OptionIcon width="30" height="30" iconColor="#808080" />
                                </div>
                                <span class="tooltiptext">
                                    <ul>
                                        <li v-if="estado"><span class="falsebutton"
                                                @click="cambiarEstado(evento.evento?.id_evento)">Cancelar</span></li>
                                        <li v-else><span class="falsebutton"
                                                @click="cambiarEstado(evento.evento?.id_evento)">Finalizar</span></li>
                                        <li><span v-if="!estado" @click="registrarPago(evento.evento?.id_evento)"
                                                class="falsebutton">Registrar pagos</span></li>
                                        <li><span class="falsebutton">Eliminar</span></li>
                                    </ul>
                                </span>
                            </div>
                            <div class="tooltip" @mouseover="showTooltip($event)" @mousemove="moveTooltip($event)"
                                @mouseleave="hideTooltip">
                                <div>
                                    <span class="falsebutton"
                                        @click="showPopup('showEvento', evento.evento?.id_evento)">
                                        <TabSweepIcon width="30" height="30" iconColor="#808080" />
                                    </span>
                                    <PopUp v-if="popupShowEvento" v-model:visible="popupShowEvento">
                                        <VistaEvento :visible="popupShowEvento" :id="eventoSeleccionado" />
                                    </PopUp>
                                </div>
                                <span class="tooltiptext">Ver evento</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <div v-else>
            <p>No hay evento disponibles</p>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
const router = useRouter();

import OptionIcon from './icons/OptionIcon.vue';
import TabSweepIcon from './icons/TabSweepIcon.vue';
import PopUp from './ui/PopUp.vue';
import VistaEvento from './VistaEvento.vue';
import { ajustarFecha } from '../main';

const eventos = ref([]);
const categorias = ref([]);
const paquetes = ref([]);
const clientes = ref([]);
const eventoSeleccionado = ref(0);

const year = ref(new Date().getFullYear());

const prevMonth = () => {
    year.value--;
    obtenerEventos(year.value);
};

const nextMonth = () => {
    year.value++;
    obtenerEventos(year.value)
};

const countCuotas = (cuotas) => {
    const count = cuotas.filter(cuota => cuota.monto === 0).length;
    return count;
};
const getCliente = (id_cliente) => {
    const cliente = clientes.value.find(cliente => cliente.id_cliente === id_cliente);
    return cliente.nomyape
}
const getPaquete = (id_paquete) => {
    const paquete = paquetes.value.find(paquete => paquete.id_paquete === id_paquete);
    return paquete.nombre
}

const showTooltip = (event) => {
    this.tooltip = event.target.querySelector('.tooltiptext');
    this.tooltip.style.visibility = 'visible';
}
const moveTooltip = (event) => {
    if (this.tooltip) {
        // Ajusta las coordenadas para que el tooltip esté cerca del mouse
        this.tooltip.style.top = `${event.clientY + 10}px`;
        this.tooltip.style.left = `${event.clientX + 10}px`;
    }
}

const hideTooltip = () => {
    if (this.tooltip) {
        this.tooltip.style.visibility = 'hidden';
    }
}

const obtenerEventos = async (year) => {
    try {
        const datesRqst = ref({
            desde: '',
            hasta: ''
        })
        datesRqst.value.desde = `${year}-01-01`;
        datesRqst.value.hasta = `${year}-12-31`;

        const resEventos = await axios.post("http://localhost:8080/api/evento/listado", datesRqst.value);
        eventos.value = resEventos.data;

        eventos.value.sort((a, b) => {
            if (a.evento.estado === b.evento?.estado) {
                return new Date(a.evento?.fecha) - new Date(b.evento?.fecha);
            }
            return a.evento?.estado - b.evento?.estado;
        });

    } catch (error) {
        console.log(error);
    }
}
const fetchDatos = async () => {

    const resCategoria = await axios.get('http://localhost:8080/api/categoria');
    categorias.value = await resCategoria.data;

    const resPaquete = await axios.get('http://localhost:8080/api/paquete');
    paquetes.value = await resPaquete.data;

    const resClientes = await axios.get(`http://localhost:8080/api/cliente`)
    clientes.value = resClientes.data;

    console.log(clientes.value)
}

const popupShowEvento = ref(false);

const showPopup = (pop, id_evento) => { // recibe un parametro para controlar los demas popup, como el de mostrar el evento por ejemplo
    if (pop == 'showEvento') {
        popupShowEvento.value = !popupShowEvento.value;
        eventoSeleccionado.value = id_evento;
    }
}

const cambiarEstado = async (idEvento) => {
    try {
        console.log(idEvento)
        const res = await axios.put(`http://localhost:8080/api/evento/estado/${idEvento}`);
        alert(res.data);
        obtenerEventos(year.value);
    } catch (error) {
        if (error.response) {
            alert(error.response.data);
        } else {
            console.log(error);
        }
    }
}

const registrarPago = (id_evento) => {
    router.push({ name: 'registrarPago', params: { id: id_evento } })
}

onMounted(() => {
    fetchDatos();
    obtenerEventos(year.value);
})
</script>

<style scoped>
.eventosContainer {
    height: 94vh;
    overflow: auto;
    padding-left: 25px;
}

h3 {

    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.evento-datos {
    display: flex;
    flex-direction: column;
    min-width: 600px;
    max-width: 600px;
    min-height: 92px;
    display: flex;
    background-color: #ffffff;
    margin-bottom: 8px;
    box-shadow: 1px 10px 10px -10px black;
}

.EventoContainer {
    display: flex;
    justify-content: space-between;
}

.eventoOptions {
    width: 50px;
}

.containerDatos {
    display: grid;
    grid-template-columns: 200px 300px;
    gap: 10px;
}

.containerDatos p {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.spanfinalizado {
    color: var(--verde);
    font-weight: bold;
}

.spanpendiente {
    color: var(--azul);
    font-weight: bold;
}

.eventoFinalizado {
    border: 2px solid var(--verde);
}

.eventoPendiente {
    border: 2px solid var(--azul);
}

.card-options {
    position: relative;
}

.tooltip {
    position: relative;
    display: inline-block;
}

.tooltip .tooltiptext {
    visibility: hidden;
    width: 120px;
    background-color: #000000aa;
    color: #fff;
    text-align: center;
    padding: 5px 0;
    border-radius: 6px;

    position: absolute;
    z-index: 100;
    top: 0;
    left: 0;
    transform: translate(20%, 0);
    /* Evita que el tooltip interfiera con el puntero */
}

.tooltiptext a {
    text-decoration: none;
    color: #ffffff;
}

.tooltip:hover .tooltiptext {
    visibility: visible;
}

.falsebutton {
    cursor: pointer;
}

.falsebutton:hover {
    color: #fff;
}

.buttonclassic {
    margin: 5px;
    background-color: #fff;
    border-radius: 5px;
    font-size: medium;
}
</style>