<template>
    <div v-if="visible" class="popup-overlay" @click="closePopup">
        <div class="popup-content" @click.stop>
            <button class="close-btn" @click="closePopup">&times;</button>
            <slot></slot>
        </div>
    </div>
</template>

<script setup>
const props = defineProps({
    visible : {
        type: Boolean,
        required: true
    }
})

const emit = defineEmits(['update:visible']);

const closePopup = () => {
    emit('update:visible', false);
}


</script>

<style scoped>
.popup-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background-color: #00000010;
    display: flex;
    justify-content: center;
    z-index: 1000;
}

.popup-content {
    background: white;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    position: relative;
    margin-top: 20px;
    height: fit-content;
}

.close-btn {
    position: absolute;
    top: 10px;
    right: 10px;
    background: none;
    border: none;
    font-size: 1.2rem;
    cursor: pointer;
}
</style>