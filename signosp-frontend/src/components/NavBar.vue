<template>
    <nav class="navbar">
        <h1>{{ title }}</h1>
        <span @click="cerrarSesion">Cerrar sesion</span>
    </nav>
</template>

<script setup>
import { useRouter } from 'vue-router'
const router = useRouter();

defineProps({
    title: {
        type: String,
        required: true
    }
});
const cerrarSesion = () => {
    localStorage.setItem('token', null);
    window.location.reload();
}
</script>

<style scoped>
.navbar {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #0e0e10;
    color: #FFFAF0;
    min-height: 5vh;
    position: relative;
}

h1 {
    flex-grow: 1;
    text-align: center;
}

span {
    position: absolute;
    right: 20px;
}
span:hover{
    color: var(--azul);
}
</style>