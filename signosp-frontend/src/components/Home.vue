<template>
    <div class="main-container" >
        <ColumnFilter v-for="categoria in categorias" :categoria="categoria.nombre"/>
    </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import axios from 'axios';

import ColumnFilter from './ColumnFilter.vue';

const categorias = ref([]);

const fetchCategorias = async () => {
    try{
        const fetchcat = await axios.get('http://localhost:8080/api/categoria');
        categorias.value = fetchcat.data;
    } catch(error) {
        console.log(error);
    }
}

onMounted(()=>{
    fetchCategorias()
})



</script>

<style scoped>
.main-container {
    display: flex;
    overflow: auto;
    min-width: 100vw;
    max-width: 100vw;
    padding: 10px;
}

.main-container .column-filter:first-child {
    margin-left: 20px;
}

</style>