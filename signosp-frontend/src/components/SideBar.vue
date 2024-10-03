<template>
  <div class="sidebar-container" @mouseenter="showSidebar" @mouseleave="hideSidebar">
    <div class="sidebar" :class="{ 'sidebar-visible': isVisible }">
      <div class="menu">
        <b>Vista</b>
        <ul>
          <li><router-link to="/">Home</router-link></li>
          <li><router-link to="/eventos">Listado de Eventos</router-link></li>
          <li><router-link to="/calendario">Calendario</router-link></li>
          <li v-if="role === 'ADMIN'"><router-link to="/ganancias">Calcular Ganancias</router-link></li>
        </ul>
        <hr>
        <b>Listados</b>
        <ul>
          <li><router-link to="/paquetes">Paquetes</router-link></li>
          <li><router-link to="/empleados">Empleados</router-link></li>
          <li><router-link to="/clientes">Clientes</router-link></li>
        </ul>
        <hr>
        <b>Registrar</b>
        <ul>  
          <li><router-link to="/nuevoEvento">Nuevo Evento</router-link></li>
          <li><router-link to="/nuevoPaquete">Nuevo Paquete</router-link></li>
          <li><router-link to="/registrarEmpleado">Registrar Empleado</router-link></li>
        </ul>
        <hr>
        <b>Complementos</b>
        <ul>  
          <li><router-link to="/categorias">Categorias</router-link></li>
          <li><router-link to="/servicios">Servicios</router-link></li>
          <li><router-link to="/materiales">Material de entrega</router-link></li>
          <li><router-link to="/especialidades">Especialidades</router-link></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
const role = ref("");

const isVisible = ref(false);

const showSidebar = () => {
  isVisible.value = true;
};

const hideSidebar = () => {
  isVisible.value = false;
};

const fetchRole = async () =>{
  try {

    const token = ref(localStorage.getItem('token'));
    const resRole = await axios.post(`http://localhost:8080/auth/getRole`, { token: token.value });
    role.value = resRole.data;
    console.log(role.value)
  } catch(error) {
    console.log(error)
  }
}

onMounted(()=>{
  fetchRole();
})

</script>

<style scoped>
.sidebar-container {
  position: fixed;
  height: 100%;
  width: 20px;
  /* Ancho del área activa */
  top: 0;
  left: 0;
  z-index: 1000;
}

.sidebar {
  position: fixed;
  left: -230px;
  /* Desplaza el sidebar fuera de la vista */
  top: 0;
  height: 100%;
  width: 250px;
  background-color: #0e0e10;
  color: white;
  transition: left 0.3s ease;
}

.sidebar-visible {
  left: 0;
  /* Muestra el sidebar */
}

.menu {
  width: 80%;
  margin-left: 10%;
  margin-top: 6vh;
  height: 94vh;
  overflow-y: auto;
  scrollbar-width: none;
}
.menu::-webkit-scrollbar {
  display: none;
}

.menu > hr {
  margin-left: -10%;
  width: 120%;
}
.menu > b {
  align-self: baseline;
}

li {
  padding-top: 10px;
  padding-left: 10px;
}

li::before {
  content: '|';
  font-size: large;
  font-weight: bold;
  color: var(--azulinvisible);
  transition: all 300ms;
}

a {
  color: var(--blanco);
  text-decoration: none;
}

li:hover::before, li:hover a {
  color: var(--azul);
}



</style>
