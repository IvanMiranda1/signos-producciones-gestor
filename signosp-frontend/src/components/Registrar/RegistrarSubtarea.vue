<template>
    <div class="main-container">
      <form @submit.prevent="registrarsubtarea">
        Nueva subtarea
        <div>
          <label for="nombre">Nombre</label>
          <input type="text" id="nombre" v-model="subtarea.nombre" required />
        </div>
        <div>
          <button class="buttonclassic" type="submit">Crear</button>
        </div>
  
      </form>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import axios from 'axios';

  const emits = defineEmits(['register'])

  const props = defineProps({
    id_evento: {
        type:Number,
        required: true
    }
  })
  const subtarea = ref({
    id_evento: 0,
    nombre: "",
    estado:false
  });

const registrarsubtarea = async () =>{
    try {
        subtarea.value.id_evento = props.id_evento;
        console.log(subtarea.value)
        await axios.post(`http://localhost:8080/api/subtarea`, subtarea.value);
        emits('register');
    } catch(error){
        console.log(error)
    }
} 

</script>

<style scoped>
.main-container form {
    display:flex;
    flex-direction:column;
    justify-content: center;
    align-items: center;
    height: 150px
}
.main-container form div {
    display:flex;
    flex-direction:column;
    height: 200px
}
</style>