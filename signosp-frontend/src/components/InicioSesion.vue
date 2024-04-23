<template>
    <div class="container">
        <div v-if="login">
            <h1>Login</h1>
            <form @submit.prevent="Request">
                <div>
                    <label for="username">Username: </label>
                    <input type="text" id="username" v-model="LoginRequest.username" required>
                </div>
                <div>
                    <label for="password">Password: </label>
                    <input type="password" id="password" v-model="LoginRequest.password" required>
                </div>
                    <button>Ingresar</button>
            </form>
            <Button @click="cambiarLogin">Registrar usuario</Button>
        </div>
        <div v-else>
            <h1>Registrar usuario</h1>
            <form @submit.prevent="Request">
                <div>
                    <label for="nomyape">Nombre y Apellido: </label>
                    <input type="text" id="nomyape" v-model="RegisterRequest.nomyape" required>
                </div>
                <div>
                    <label for="username">Username: </label>
                    <input type="text" id="username" v-model="RegisterRequest.username" required>
                </div>
                <div>
                    <label for="password">Password: </label>
                    <input type="password" id="password" v-model="RegisterRequest.password" required>
                </div>
                    <button>Registrar</button>
            </form>
            <Button @click="cambiarLogin">Iniciar sesion</Button>

        </div>
    </div>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import axios from 'axios';
const login = ref(true);

const LoginRequest = ref({
    username:"",
    password:""
})

const RegisterRequest = ref({
    nomyape:"",
    username:"",
    password:""
})


onMounted(()=>{
    login.value = true;

})

const cambiarLogin = () =>{
    login.value = !login.value
}

//Registrar Usuario
const Request = async () =>{

    try {
        const req = await axios.post(`http://localhost:8080/auth/${login.value?'login':'register'}`,login.value?LoginRequest.value:RegisterRequest.value)
        if(login.value){
            try{
                if(localStorage.getItem('token') !== null ){
                localStorage.removeItem('token')
                localStorage.setItem('token',req.data.token);
                window.location.reload();
                } else {
                    localStorage.setItem('token',req.data.token);
                    window.location.reload();
                }
            }catch(error) {
                localStorage.removeItem('token');
                window.location.reload();
            }
        }
    }catch(error) {
        localStorage.removeItem('token');
        window.location.reload();
    }
}


</script>