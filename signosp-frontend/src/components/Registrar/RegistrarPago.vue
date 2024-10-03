<template>
  <div class="registrar-pago-container">
    <h2>Registrar pago de evento: <i>{{ ajustarFecha(evento.fecha) }}</i> - {{ evento.titulo }}</h2>

    <div class="info-evento">
      <p>Valor del evento: {{ paquete.precio }}</p>
      <p>Pagado en: {{ evento.pago.cant_cuotas }} cuotas</p>
      <p>Cuotas de: {{ (paquete.precio / evento.pago.cant_cuotas) }}</p>
    </div>

    <div class="registro-container">
      <div class="datos-pago">
        <h3>Datos del pago:</h3>
        <div class="fechaDePago">
          <label for="fechaDePago">Fecha de pago:</label>
          <input type="date" name="fechaDePago" id="fechaDePago" v-model="cuota.fecha_de_pago">
        </div>
        <div class="campo">
          <label for="numero-cuota">Número de cuota:</label>
          <select id="numero-cuota" v-model="cuotaSeleccionada" @change="cambioDeCuota">
            <option v-for="cuota in cuotasDisponibles" :key="cuota" :value="cuota">{{ cuota }}</option>
          </select>
        </div>

        <div class="campo">
          <label>Forma de pago:</label>
          <div>
            <label for="formadepago">Contado: </label>
            <input type="radio" name="formadepago" value="Contado" v-model="evento.pago.forma_de_pago">
            <label for="formadepago">Tarjeta: </label>
            <input type="radio" name="formadepago" value="Tarjeta" v-model="evento.pago.forma_de_pago">
          </div>
        </div>

        <div class="campo">
          <label for="monto">Monto del pago: (maximo: {{ montoMaximo }})</label>
          <input type="number" id="monto" v-model="montoPago" value="" v-bind:max="montoMaximo">
        </div>

        <button class="buttonclassic" @click="registrarPago">Registrar pago</button>
      </div>

      <div class="cuotas-registradas">
        <h3>Cuotas registradas:</h3>
        <ul>
          <li v-for="cuota in pagoConCuotas.cuotas" :key="cuota.id_cuota">Cuota: {{ cuota.nro_cuota }} | {{
      cuota.monto == 0 ? "Cuota cancelada el " + ajustarFecha(cuota.fecha_de_pago) : "$" + cuota.monto + " a pagar" }} <button
              v-if="cuota.monto != 0" @click="cancelarCuota(cuota.nro_cuota, cuota.monto)">Cancelar</button></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';
import { ajustarFecha } from '../../main';
const route = useRoute();


const evento = ref({
  id_evento: 0,
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
    id_pago: 0,
    forma_de_pago: "",
    cant_cuotas: 0
  },
  empleados: []
}),
  cuota = ref({
    id_pago: 0,
    nro_cuota: 1,
    monto: 0,
    fecha_de_pago: new Date().toISOString().split('T')[0]
  }),
  paquete = ref({}),
  pagoConCuotas = ref({});

const cuotaSeleccionada = ref(1);  // valor inicial de la cuota seleccionada
const montoPago = ref(0);
const cuotasDisponibles = ref([]);
const montoMaximo = ref(0);
const id_evento = ref(0);

const props = defineProps({
  id: {
    type: Number,
    required: true
  }
})

const fetchDatos = async (id_evento) => {
  try {
    const resEvento = await axios.get(`http://localhost:8080/api/evento/${id_evento}`);
    evento.value = resEvento.data

    const resPaquete = await axios.get(`http://localhost:8080/api/paquete/${evento.value.id_paquete}`);
    paquete.value = resPaquete.data;

    const resPago = await axios.get(`http://localhost:8080/api/evento/pago/${evento.value.id_evento}`);
    pagoConCuotas.value = resPago.data;

    cuotasDisponibles.value = Array.from({ length: evento.value.pago.cant_cuotas }, (_, i) => i + 1);
    console.log(pagoConCuotas.value)

  } catch (error) {
    console.log(error)
  }
}

const cambioDeCuota = () => {
  const cuota = pagoConCuotas.value.cuotas.find((c) => c.nro_cuota === cuotaSeleccionada.value);
  if (cuota) {
    montoMaximo.value = cuota.monto;
  } else {
    montoMaximo.value = paquete.value.precio / evento.value.pago.cant_cuotas;
  }
}

const registrarPago = async () => {
  try {
    if (montoMaximo.value === 0) {
      alert("La cuota ya esta cancelada")
      return;
    }
    if (montoPago.value > montoMaximo.value) {
      alert(`El monto supera el maximo (${montoMaximo.value})`);
      return;
    }
    if (cuotaSeleccionada.value > 1) {
      const cuotaAnteriorNumero = cuotaSeleccionada.value - 1;

      const cuotaAnterior = pagoConCuotas.value.cuotas.find(c => c.nro_cuota === cuotaAnteriorNumero);

      if (!cuotaAnterior) {
        alert(`No puedes pagar la cuota ${cuotaSeleccionada.value} porque la cuota ${cuotaAnteriorNumero} no existe.`);
        return;
      }
      if (cuotaAnterior && cuotaAnterior.monto !== 0) {
        alert(`No puedes pagar la cuota ${cuotaSeleccionada.value}° porque la cuota ${cuotaAnteriorNumero}° aún no está pagada por completo.`);
        return;
      }
    }
    const cuotaExiste = pagoConCuotas.value.cuotas.find((c) => c.nro_cuota === cuotaSeleccionada.value);
    if (cuotaExiste) {
      cuota.value.monto = montoPago.value;
    } else {
      // como resultado el monto que queda 'a pagar'
      cuota.value.monto = (parseFloat(paquete.value.precio) / parseFloat(pagoConCuotas.value.pagoDTO.cant_cuotas)) - parseFloat(montoPago.value);
    }
    cuota.value.id_pago = pagoConCuotas.value.pagoDTO.id_pago;
    cuota.value.nro_cuota = cuotaSeleccionada.value;
    cuota.value.fecha_de_pago = new Date(cuota.value.fecha_de_pago).toISOString().split('T')[0];

    await axios.post('http://localhost:8080/api/cuota', cuota.value);
    const resPago = await axios.get(`http://localhost:8080/api/evento/pago/${evento.value.id_evento}`);
    pagoConCuotas.value = resPago.data;
    cambioDeCuota();
  } catch (error) {
    console.log(error);
  }
}

const cancelarCuota = async (nro_cuota, monto) => {
  try {
    cuota.value.id_pago = pagoConCuotas.value.pagoDTO.id_pago;
    cuota.value.nro_cuota = nro_cuota;
    cuota.value.monto = monto;
    cuota.value.fecha_de_pago = new Date().toISOString().split('T')[0];
    await axios.post('http://localhost:8080/api/cuota', cuota.value);
    const resPago = await axios.get(`http://localhost:8080/api/evento/pago/${evento.value.id_evento}`);
    pagoConCuotas.value = resPago.data;
    cambioDeCuota();
  } catch(error) {
    console.log(error)
  }
}




watch(() => route.params.id, async (newValue) => {
  if (newValue) {
    id_evento.value = props.id
    await fetchDatos(id_evento.value)
  }
})

onMounted(async () => {
  await fetchDatos(props.id)
  cambioDeCuota();
})

</script>

<style scoped>
.registrar-pago-container {
  padding: 20px;
}

h2 i {
  color: var(--azul);
}

.info-evento {
  font-size: 14px;
  margin-bottom: 20px;
}

.registro-container {
  display: flex;
  justify-content: space-between;
}

.datos-pago,
.pagos-registrados {
  width: 48%;
}

.datos-pago {
  display: flex;
  flex-direction: column;
  height: 350px;
  padding: 10px;
  justify-content: space-evenly;
}
input,select {
  padding: 5px;
  margin: 5px;
}

.campo {
  margin-bottom: 10px;
}

button {
  margin-top: 20px;
}

.buttonclassic {
  padding: 10px;
}

.cuotas-registradas {
  width: 40%;
  border-left: 2px solid var(--negro);
  padding: 5px;
}
.cuotas-registradas ul li {
  margin: 5px;
}
</style>