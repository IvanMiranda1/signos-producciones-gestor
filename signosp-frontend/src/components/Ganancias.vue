<template>
    <div class="eventosContainer">
        <div class="form">
            <div class="form-inputs">
                <div class="bars">
                    <BarsGraph width="40" height="40" iconColor="#8fd14f" />
                </div>
                <div>
                    <label for="desde">Desde: </label>
                    <input type="date" name="desde" id="desde" v-model="datesRqst.desde">
                </div>
                <div>
                    <label for="hasta">Hasta: </label>
                    <input type="date" name="hasta" id="hasta" v-model="datesRqst.hasta">
                </div>
            </div>
            <button @click="obtenerEventos" class="buttonclassic">Calcular</button>
        </div>

        <div class="listado">
            <div v-if="eventos.length > 0" v-for="evento in eventos" :key="evento.id_evento">
                <div class="evento-datos">
                    <div class="containerDatos">
                        <div class="data-item data-fecha">
                            <span>{{ ajustarFecha(evento.evento.fecha) }}</span>
                        </div>
                        <div class="data-item data-titulo">
                            <span>{{ evento.evento.titulo }}</span>
                        </div>
                        <div class="data-item data-recaudacion">
                            <span>Recaudado: <b class="recaudacion">${{ getRecaudacion(evento) }}</b></span>
                        </div>
                    </div>
                </div>
            </div>
            <div v-else>
                <p>No hay evento disponibles</p>
            </div>
        </div>
        <div class="total">
            <p>Total recaudado <i class="gris">(cuotas pagadas)</i>: <b class="recaudacion">${{ totalRecaudado }}</b>
            </p>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

import BarsGraph from './icons/BarsGraph.vue';
import { ajustarFecha } from '../main';

const eventos = ref([]);
const categorias = ref([]);
const paquetes = ref([]);
const clientes = ref([]);
const datesRqst = ref({
    desde: '',
    hasta: ''
})
const totalRecaudado = ref(0);


const getRecaudacion = (eventocp) => {
    const paquete = paquetes.value.find(paquete => paquete.id_paquete == eventocp.evento.id_paquete);

    const recaudacion = eventocp.pagoConCuotas.cuotas.reduce((acumulador, cuota) => {
        const precioPorCuota = paquete.precio / eventocp.pagoConCuotas.pagoDTO.cant_cuotas;
        return acumulador + (cuota.monto == 0 ? precioPorCuota : precioPorCuota - cuota.monto)
    }, 0)
    return recaudacion;
};

const obtenerEventos = async () => {
    try {
        const resEventos = await axios.post("http://localhost:8080/api/evento/listado", datesRqst.value);
        eventos.value = resEventos.data;

        eventos.value.sort((a, b) => {
            if (a.evento.estado === b.evento?.estado) {
                return new Date(a.evento?.fecha) - new Date(b.evento?.fecha);
            }
            return a.evento?.estado - b.evento?.estado;
        });
        totalRecaudado.value = 0;

        eventos.value.forEach(evento => {
            const recaudacion = getRecaudacion(evento);
            totalRecaudado.value += recaudacion;  // Sumar al total
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
}

onMounted(() => {
    fetchDatos();
})
</script>

<style scoped>
.eventosContainer {
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 94vh;
    overflow: auto;
    padding-left: 25px;
    display: flex;
}

.form {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.form-inputs {
    display: flex;
    align-items: center;
    padding: 30px;
    gap: 30px;
}

.listado {
    width: 80%;
    display: flex;
    justify-content: space-evenly;
    flex-wrap: wrap;
    padding: 10px;
    height: 350px;
    overflow: auto;
}

.total {
    width: 80%;
    display: flex;
    flex-direction: row-reverse;
}


.evento-datos {
    display: flex;
    flex-direction: column;
    width: 150px;
    max-height: 150px;
    min-height: 150px;
    display: flex;
    background-color: #ffffff;
    margin-bottom: 8px;
    margin-right: 8px;
    box-shadow: 1px 10px 10px -10px black;
    font-size: 15px;
}

.listado {
    display: flex;
}

.data-titulo {
    font-weight: bold;

    white-space: wrap;
    overflow: hidden;
    text-overflow: ellipsis;

    display: -webkit-box;
    -webkit-line-clamp: 2;
    /* Número de líneas a mostrar por defecto */
    -webkit-box-orient: vertical;
}

.containerDatos {
    min-height: 150px;
    display: grid;
    grid-template-columns: repeat(1, 1fr);
    grid-template-rows: 30px 1fr 30px;
    text-align: center;
    border: 2px solid var(--gris);

}

.data-item:not(:last-child) {
    border-bottom: 2px solid var(--gris);
}

.data-fecha {
    font-weight: 400;
    font-size: medium;
}

.recaudacion {
    color: var(--verde);
}

.gris {
    color: var(--gris);
}

.buttonclassic {
    background-color: var(--verde);
    border: none;
    color: #ffffff;
    padding: 10px;
    font-size: medium;
}
.data-recaudacion {
    font-size: small;
}
</style>