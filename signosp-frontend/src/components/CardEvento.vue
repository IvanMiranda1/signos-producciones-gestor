<template>
    <div class="card-evento" :id="id" v-bind:class="cardComputed">
        <div class="card-estado" v-if="estado">
            <CircleCheck width="30" height="30" iconColor="#8fd14f" />
        </div>
        <div class="card-estado" v-else>
            <CircleCheck width="30" height="30" iconColor="#2d9bf0" />
        </div>
        <div class="card-content">
            <span class="card-titulo">{{ tituloEv }}</span>
            <div>
                <ul>
                    <li><b>Fecha: {{ fechaEv }}</b></li>
                </ul>
            </div>
        </div>
        <div class="card-options">
            <div class="tooltip">
                <div>
                    <OptionIcon width="30" height="30" iconColor="#808080" />
                </div>
                <span class="tooltiptext">
                    <ul>
                        <li v-if="estado"><span class="falsebutton" @click="cambiarEstado(id)">Cancelar</span></li>
                        <li v-else><span class="falsebutton" @click="cambiarEstado(id)">Finalizar</span></li>
                        <li>
                            <span class="falsebutton" @click="showPopup('popupMod')">Modificar</span>
                        </li>
                        <li><span v-if="!estado" @click="registrarPago(id)" class="falsebutton">Registrar pagos</span>
                        </li>
                        <li><span class="falsebutton" @click="iniciarEliminacion(id)">Eliminar</span></li>
                    </ul>
                </span>
                <PopUp v-if="popupMod" v-model:visible="popupMod">
                    <ModificarEvento :visible="popupMod" :id="id" @update="updateEvento" />
                </PopUp>
            </div>
            <div class="tooltip">
                <div>
                    <span class="falsebutton" @click="showPopup('showEvento')">
                        <TabSweepIcon width="30" height="30" iconColor="#808080" />
                    </span>
                    <PopUp v-if="popupShowEvento" v-model:visible="popupShowEvento">
                        <VistaEvento :visible="popupShowEvento" :id="id" />
                    </PopUp>
                </div>
                <span class="tooltiptext">Ver evento</span>
            </div>
        </div>
    </div>
</template>

<script setup>
import axios from 'axios';

import CircleCheck from './icons/CircleCheck.vue';
import OptionIcon from './icons/OptionIcon.vue';
import TabSweepIcon from './icons/TabSweepIcon.vue';
import PopUp from './ui/PopUp.vue';
import ModificarEvento from './Modificar/ModificarEvento.vue';
import VistaEvento from './VistaEvento.vue';

import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter();

const props = defineProps({
    id: Number,
    id_categoria: Number,
    titulo: String,
    fechaDelEvento: String,
    estado: {
        type: Boolean,
        default: false
    },
    modificarEvento: {
        type: Boolean,
        default: false
    }
})

const tituloEv = ref("")
const fechaEv = ref("")

const emit = defineEmits(['modificado', 'update:visible'], ['estadoMod'])
const popupMod = ref(false);
const popupShowEvento = ref(false);

const showPopup = (pop) => { // recibe un parametro para controlar los demas popup, como el de mostrar el evento por ejemplo
    if (pop == "popupMod") {
        popupMod.value = !popupMod.value;
    } if (pop == 'showEvento') {
        popupShowEvento.value = !popupShowEvento.value;
    }
}

const cambiarEstado = async (idEvento) => {
    try {
        console.log(idEvento)
        const res = await axios.put(`http://localhost:8080/api/evento/estado/${idEvento}`);
        alert(res.data);
        emit('estadoMod');
    } catch (error) {
        if (error.response) {
            alert(error.response.data);
        } else {
            console.log(error);
        }
    }
}

const iniciarEliminacion = async (id_evento) => {
    try {
        const res = await axios.post('http://localhost:8080/api/evento/eliminar', null, { params: { id_evento: id_evento } });
        if (res.data.includes('¿Desea eliminar el cliente?')) {//solo se encontro un evento con este cliente...
            if (confirm(res.data)) {//si se confirma se elimina con cliente incluido
                const resDeleteCliente = await axios.post('http://localhost:8080/api/evento/eliminar', null, {
                    params: {
                        id_evento: id_evento,
                        eliminarCliente: true
                    }
                });
                alert(resDeleteCliente.data);
            } else {//Si la respuesta fue cancelar.
                if (confirm("¿Desea eliminar el evento?")) {
                    const resDeleteEvento = await axios.post('http://localhost:8080/api/evento/eliminar', null, {
                        params: {
                            id_evento: id_evento,
                            soloEvento: true
                        }
                    });
                    alert(resDeleteEvento.data);
                } else {
                    alert("Operacion cancelada.")
                }
            }
        } else if (res.data.includes('¿Desea eliminar solo el evento?')) {
            if (confirm(res.data)) {
                const resDeleteEvento = await axios.post('http://localhost:8080/api/evento/eliminar', null, {
                    params: {
                        id_evento: id_evento,
                        soloEvento: true
                    }
                });
                alert(resDeleteEvento.data);
            } else {
                alert('Operacion cancelada.');
            }
        }
    } catch (error) {
        console.log(error);
    }
};

const registrarPago = (id_evento) => {
    router.push({ name: 'registrarPago', params: { id: id_evento } })
}

onMounted(() => {
    tituloEv.value = props.titulo;
    fechaEv.value = props.fechaDelEvento;
})



//Clases para css
const cardClasses = {
    'card-evento': true,
    'cardTrue': false,
    'cardFalse': false
}
const cardComputed = computed(() => {
    cardClasses.cardTrue = props.estado;
    cardClasses.cardFalse = !props.estado;

    const activeClass = Object.keys(cardClasses)
        .filter(key => cardClasses[key])
        .map(key => key);

    return activeClass.join(' ');
})
//////////////////////


const updateEvento = (EventData) => {
    showPopup('popupMod');
    tituloEv.value = EventData.value.titulo;
    fechaEv.value = EventData.value.fecha;
    if (EventData.value.id_categoria != props.id_categoria) {
        window.location.reload();
    }
}
</script>



<style scoped>
.cardTrue {
    border: 2px solid #8fd14f80;
}

.cardTrue:hover {
    border: 2px solid #8fd14f;
}

.cardFalse {
    border: 2px solid #2d9bf080;
}

.cardFalse:hover {
    border: 2px solid #2d9bf0;
}

.card-evento {
    min-width: 300px;
    max-width: 300px;
    min-height: 92px;
    display: flex;
    background-color: #ffffff;
    margin-bottom: 8px;
    position: relative;
}

.card-estado {
    flex: 1;
}

.card-content {
    flex: 8;
}

.card-content .card-titulo {
    font-weight: bold;
}

.card-content :not(.card-titulo) {
    color: var(--gris);
}

.card-options {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    position: absolute;
    right: 0;
}

.card-estado>img {
    width: 30px;
}

.button>img {
    width: 20px;
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
    transform: translate(-100%, 0);
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
</style>