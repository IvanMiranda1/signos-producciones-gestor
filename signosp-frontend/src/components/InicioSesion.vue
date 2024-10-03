<template>
    <div class="container">
        <div v-if="login && !forgotPassword">
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
            <Button @click="mostrarOlvidePassword">Olvidé mi contraseña</Button>
        </div>

        <div v-else-if="!login && !forgotPassword">
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
            <Button @click="cambiarLogin">Iniciar sesión</Button>
        </div>

        <div v-else-if="forgotPassword">
            <h1>Olvidé mi contraseña</h1>
            <form @submit.prevent="RequestForgotPassword">
                <div>
                    <label for="username">Username: </label>
                    <input type="text" id="username" v-model="ForgotPasswordRequest.username" required>
                </div>
                <div>
                    <label for="newPassword">Nueva Contraseña: </label>
                    <input type="password" id="newPassword" v-model="ForgotPasswordRequest.password" required>
                </div>
                <button>Restablecer contraseña</button>
            </form>
            <Button @click="cancelarOlvidePassword">Cancelar</Button>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import axios from 'axios';
const login = ref(true);
const forgotPassword = ref(false);

const LoginRequest = ref({
    username: "",
    password: ""
});

const RegisterRequest = ref({
    nomyape: "",
    username: "",
    password: ""
});

const ForgotPasswordRequest = ref({
    username: "",
    password: ""
});

onMounted(() => {
    login.value = true;
});

const cambiarLogin = () => {
    login.value = !login.value;
    forgotPassword.value = false;
};

// Mostrar formulario de "Olvidé mi contraseña"
const mostrarOlvidePassword = () => {
    forgotPassword.value = true;
    login.value = false;
};

// Cancelar "Olvidé mi contraseña"
const cancelarOlvidePassword = () => {
    forgotPassword.value = false;
    login.value = true;
};

// Guardar token en localStorage y recargar la página
const handleToken = async (token) => {
    try {
        localStorage.setItem('token', token);
        window.location.reload();
    } catch (error) {
        console.error('Error al guardar el token:', error);
        localStorage.removeItem('token');
        window.location.reload(); // Solo recargar si es necesario
    }
};

// Iniciar sesión o registrar usuario
const Request = async () => {
    try {
        const url = `http://localhost:8080/auth/${login.value ? 'login' : 'register'}`;
        const requestData = login.value ? LoginRequest.value : RegisterRequest.value;

        const req = await axios.post(url, requestData);

        if (login.value && req.data.token) {
            await handleToken(req.data.token);
        }
        login.value = true;
        window.location.reload();
    } catch (error) {
        console.error('Error en la solicitud:', error);
        localStorage.removeItem('token');
        window.location.reload();
    }
};

// Restablecer contraseña
const RequestForgotPassword = async () => {
    try {
        const url = 'http://localhost:8080/auth/forgot-password';
        const req = await axios.post(url, ForgotPasswordRequest.value);

        alert('Contraseña restablecida con éxito');
        forgotPassword.value = false;
        login.value = true;
    } catch (error) {
        console.error('Error al restablecer la contraseña:', error);
    }
};

</script>

<style scoped>
.container { 
    border: 5px solid black;
    padding: 20px;
    width: 350px;
    height: 400px;
    border-radius: 5px;
}

.container > div {
    display: flex;
    flex-direction: column;
    align-items: center
}
form {
    display: flex;
    flex-direction: column;
    align-items: center;
}
form > div {
    display: flex;
    flex-direction: column;
    margin-bottom: 10px;
}
button { 
    margin-bottom: 10px;
}
input {
    border: 1px solid black;
    padding: 10px;
    border-radius: 5px;
    font-size: medium;
}
</style>
