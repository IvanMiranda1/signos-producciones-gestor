<template>
    <div v-if="!sesion" class="">
      <!--Inicio de sesion-->
      <InicioSesion/>
    </div>


    <div v-else class="container">
      <!--Contenido de la app-->
      <div class="view-container"> 
        Se muestra la aplicacion
          <!--<RouterView :style="{ width: '80%' }"/>-->
      </div>
    </div>

</template>
<!--

La idea es primero saber si ya hay un inicio de sesion, o podria iniciar sesion cada vez que ingreso a la aplicacion....
si siempre ingreso me ahorro la validacion de comprobar si ya hay una sesion abierta, porque tampoco se como almacenar la sesion, capaz que con el localstorage

-->
<script setup>
//componentes
import InicioSesion from "./components/InicioSesion.vue";
//script
import { RouterLink, RouterView } from 'vue-router';
import {onMounted, ref} from 'vue';
import axios from 'axios';
const token = ref(localStorage.getItem('token'));
const sesion = ref(false);

onMounted(()=>{

  token.value = localStorage.getItem('token');

  if(token.value){
    //Si existe un token, hay una sesion activa....
    isValid(token.value);
  } else {
    sesion.value = false;
  }

})

const isValid = async (token) => {
  try{
      const res = await axios.post("http://localhost:8080/auth/validate",{token: token});
      sesion.value = true;
    } catch(error) {
      sesion.value = false;
      console.log("token no valido: ", error);
    }
}
</script>

<style scoped>
  .container { 
    display: flex;
  }
  nav {
    border-right: 1px solid gray;
    box-shadow: -10px 50px 50px 0 black;
    width: 10%;
    height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  ul {
    margin-left: 10px;
  }
  ul > div {
    width: 80%;
  }
  ul > div > li {
    margin-left: 10px;
  }
  ul > div > p {
    font-style: italic;
  }
  li {
    transition: all 1s;

  }
  li {
    transition: border-bottom 0.3s ease;
  }

  li > *:hover {
    border-bottom: 1px solid #535bf2;
    transition-duration: 0.6s; 
  }
  .view-container{
    display: flex;
    justify-content: center;
    overflow: auto;
    max-height: 100vh;
    width: 100vw;
  }

</style>
