<template>
    <svg :width="width" :height="height" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M9 9L15 15" :stroke="iconColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
        <path d="M15 9L9 15" :stroke="iconColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
    </svg>
</template>

<script>
export default {
    props: {
        width: {
            type: [Number, String],
            default: 18
        },
        height: {
            type: [Number, String],
            default: 18
        },
        iconColor: {
            type: String,
            default: 'currentColor'
        }
    }
}
</script>