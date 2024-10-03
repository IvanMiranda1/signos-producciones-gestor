<template>
  <div v-if="!sesion" class="login">
    <InicioSesion />
  </div>


  <div v-else class="app">
    <NavBar :title="currentTitle" />
    <div class="app-container">
      <SideBar />
      <div class="main-container">
        <RouterView v-slot="{ Component }" :style="{minHeight: '93vh'}">
          <template v-if="Component">
            <Transition mode="fade">
              <KeepAlive>
                <Suspense>
                  <!-- main content -->
                  <component :is="Component"></component>

                  <!-- loading state -->
                  <template #fallback>
                    <div class="loading">Cargando...</div>
                  </template>
                </Suspense>
              </KeepAlive>
            </Transition>
          </template>
        </RouterView>
      </div>
    </div>
  </div>

</template>

<script setup>
//componentes
import InicioSesion from "./components/InicioSesion.vue";
import SideBar from "./components/SideBar.vue";
import NavBar from "./components/NavBar.vue";
//script
import { RouterView, useRoute } from 'vue-router';
import { onMounted, ref, computed } from 'vue';
import axios from 'axios';

const token = ref(localStorage.getItem('token'));
const sesion = ref(false);

const route = useRoute();
const currentTitle = computed(() => route.meta.title);

onMounted(() => {
  token.value = localStorage.getItem('token');
  if (token.value) {
    isValid(token.value);
  } else {
    sesion.value = false;
  }
})

const isValid = async (token) => {
  try {
    const res = await axios.post("http://localhost:8080/auth/validate", { token: token });
    sesion.value = true;
  } catch (error) {
    sesion.value = false;
    console.log("token no valido: ", error);
  }
}
</script>

<style scoped>

.app-container {
  height: 90vh;
  width: 100vw;
}

.main-content {
  flex: 1;
  overflow: auto;
}

.login {
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
