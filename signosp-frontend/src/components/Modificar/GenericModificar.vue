<template>
    <div>
      <h3>{{ titulo }}</h3>
      <form @submit.prevent="modificar">
        <label for="nombre">Nombre:</label>
        <input v-model="nombre" id="nombre" type="text" required />
  
        <button type="submit">Guardar</button>
      </form>
    </div>
  </template>

<script setup>
import { onMounted, ref } from 'vue';
import axios from 'axios';

const emits = defineEmits(['update']);

const props = defineProps({
    tipo : {
        type: String,
        required: true,
    },
    entidad : {
        type: Object,
        default: ()=>({id:null, nombre:''})
    }
});

const nombre = ref(props.entidad.nombre || '');



const modificar = async ()=>{
  try {
    const entidadMod = ref({
      [`id_${props.tipo=="material"?"material_de_entrega":props.tipo}`] : props.entidad[`id_${props.tipo=="material"?"material_de_entrega":props.tipo}`],
      nombre: nombre.value
    })
    await axios.put(`http://localhost:8080/api/${props.tipo}`, entidadMod.value);
    emits('update');
  } catch (error) {
    console.log(error)
  }
}



</script>