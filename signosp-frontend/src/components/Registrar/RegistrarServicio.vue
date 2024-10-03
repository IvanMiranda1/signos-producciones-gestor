<template>
    <div class="main-container">
      <form @submit.prevent="registrarservicio">
        Nuevo Servicio
        <div>
          <label for="nombre">Nombre</label>
          <input type="text" id="nombre" v-model="servicio.nombre" required />
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
  
  const servicio = ref({
    nombre: "",
  });

const registrarservicio = async () =>{
    try { 
        await axios.post(`http://localhost:8080/api/servicio`, servicio.value);
        emits('update');
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