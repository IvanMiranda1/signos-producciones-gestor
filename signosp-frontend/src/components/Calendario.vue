<template>
    <div class="main-container">
        <h2>{{ year }} - {{ monthNames[month - 1] }}</h2>
        <button @click="prevMonth">Anterior</button>
        <button @click="nextMonth">Siguiente</button>


        <div class="calendar">
            <div class="day" v-for="day in daysOfWeek" :key="day">{{ day }}</div>
            <div v-for="day in emptyDays" class="empty"></div>
            <div class="day" v-for="day in daysInMonth" :key="day.date">
                <span>{{ day.day }}</span>
                <div class="listeventos">
                    <div v-for="evct in eventosCuotas" :key="evct.evento?.id_evento">
                        <div v-if="evct?.evento && obtenerNumeroDia(evct?.evento.fecha) === day.day">
                            <div class="tooltip" @mouseover="handleMouseOver(evct.evento)"
                                @mousemove="updateTooltipPosition(evct.evento)" @mouseleave="hideTooltip">
                                <span class="spanCategorias" @click="showPopup('showEvento', evct.evento?.id_evento)"
                                v-bind:class="{'finalizado':evct.evento?.estado,'pendiente':!evct.evento?.estado}">
                                    {{ getCategoria(evct.evento.id_categoria) }}
                                </span>
                                <div class="tooltiptext" :style="{ top: tooltipY + 'px', left: tooltipX + 'px' }">
                                    {{ tooltipContent }}
                                </div>
                            </div>
                            <PopUp v-if="popupShowEvento" v-model:visible="popupShowEvento">
                                <VistaEvento :visible="popupShowEvento" :id="idSelected" />
                            </PopUp>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import axios from 'axios';
import PopUp from './ui/PopUp.vue';
import VistaEvento from './VistaEvento.vue';
import { obtenerNumeroDia } from '../main';


const today = new Date();
const year = ref(today.getFullYear());
const month = ref(today.getMonth() + 1);
const eventosCuotas = ref([]);
const idSelected = ref(0);

const popupShowEvento = ref(false);

const tooltipX = ref(0);
const tooltipY = ref(0);
const tooltipContent = ref('');

const categorias = ref([]);

const showPopup = (pop, id_evento) => {
    if (pop == 'showEvento') {
        popupShowEvento.value = !popupShowEvento.value;
        idSelected.value = id_evento;
    }
}

const monthNames = [
    "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];

const daysOfWeek = ["Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"];

const getDaysInMonth = (year, month) => {
    const date = new Date(year, month - 1, 1);
    const days = [];

    while (date.getMonth() === month - 1) {
        days.push({
            day: date.getDate(),
            date: new Date(date),
        });
        date.setDate(date.getDate() + 1);
    }

    return days;
};


const getEmptyDays = (year, month) => {
    const firstDay = new Date(year, month - 1, 1).getDay();
    return Array(firstDay).fill(null);
};

const daysInMonth = ref(getDaysInMonth(year.value, month.value));
const emptyDays = ref(getEmptyDays(year.value, month.value));

const prevMonth = () => {
    if (month.value === 1) {
        year.value--;
        month.value = 12;
    } else {
        month.value--;
    }
    daysInMonth.value = getDaysInMonth(year.value, month.value);
    emptyDays.value = getEmptyDays(year.value, month.value);
    obtenerEventos(`${year.value}-${month.value}-${(daysInMonth.value)[0].day}`, `${year.value}-${month.value}-${(daysInMonth.value)[daysInMonth.value.length - 1].day}`)

};

const nextMonth = () => {
    if (month.value === 12) {
        year.value++;
        month.value = 1;
    } else {
        month.value++;
    }
    daysInMonth.value = getDaysInMonth(year.value, month.value);
    emptyDays.value = getEmptyDays(year.value, month.value);
    obtenerEventos(`${year.value}-${month.value}-${(daysInMonth.value)[0].day}`, `${year.value}-${month.value}-${(daysInMonth.value)[daysInMonth.value.length - 1].day}`)
};

const obtenerEventos = async (desde, hasta) => {
    try {
        const datesRqst = ref({
            desde: '',
            hasta: ''
        })
        datesRqst.value.desde = desde;
        datesRqst.value.hasta = hasta;
        const resEventos = await axios.post("http://localhost:8080/api/evento/listado", datesRqst.value);
        eventosCuotas.value = resEventos.data;
        console.log(eventosCuotas.value)

        /* eventosCuotas.value.sort((a, b) => {
            if (a.evento.estado === b.evento.estado) {
                return new Date(a.evento.fecha) - new Date(b.evento.fecha);
            }
            return a.estado - b.estado;
        }); */
    } catch (error) {
        console.log(error);
    }
}

const getCategoria = (id_categoria) => {
    console.log('Buscando categoría con id:', id_categoria);
    const cat = categorias.value.find((cat) => cat.id_categoria === id_categoria);
    console.log('Categoría encontrada:', cat);
    return cat ? cat.nombre : 'Categoría no encontrada';
}
const fetchDatos = async () => {
    try {
        const fetchCategorias = await axios.get(`http://localhost:8080/api/categoria`);
        categorias.value = await fetchCategorias.data;
    } catch (error) {
        console.log(error)
    }
}


const updateTooltipPosition = (event) => {
    tooltipX.value = event.clientX + 10; // Ajusta el valor para mover el tooltip a la derecha
    tooltipY.value = event.clientY - 40; // Ajusta el valor para mover el tooltip arriba
};

const hideTooltip = () => {
    tooltipContent.value = '';
};
const handleMouseOver = (evento) => {
    tooltipContent.value = evento.titulo;
};

onMounted(() => {
    daysInMonth.value = getDaysInMonth(year.value, month.value);
    obtenerEventos(`${year.value}-${month.value}-${(daysInMonth.value)[0].day}`, `${year.value}-${month.value}-${(daysInMonth.value)[daysInMonth.value.length - 1].day}`)
    fetchDatos();
})

</script>

<style scoped>
.main-container {
    padding-left: 40px;
    overflow: auto;
    height: 70vh;
}

 
.calendar {
    display: grid;
    grid-template-columns: repeat(7, 1fr);
    grid-gap: 10px;
    margin-top: 20px;
}

.day {
    padding: 10px;
    text-align: center;
    background-color: #f0f0f0;
    border: 1px solid #ccc;
    width: 150px;
    height: 80px;
}

.empty {
    background-color: transparent;
}

.tooltip {
    position: relative;
    display: inline-block;
}

.tooltip .tooltiptext {
    font-size: smaller;
    visibility: hidden;
    width: 150px;
    background-color: #000000aa;
    color: #fff;
    text-align: center;
    border-radius: 6px;
    position: absolute;
    z-index: 1;
    left: 50%;
    margin-left: 60px;
    padding: 5px;

}

.tooltip:hover .tooltiptext {
    visibility: visible;
}

.tooltiptext a {
    text-decoration: none;
    color: #ffffff;
}

.spanCategorias {
    font-size: smaller;
    color: var(--azul);
}
.finalizado {
    color: var(--verde);
}
.pendiente {
    color: var(--azul);
}
</style>