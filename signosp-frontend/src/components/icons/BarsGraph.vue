<template>
    <svg :fill="iconColor" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"
        xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 472.615 472.615" xml:space="preserve" :width="width"
        :height="height" :stroke="iconColor">
        <g id="SVGRepo_bgCarrier" stroke-width="0" />
        <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round" />
        <g id="SVGRepo_iconCarrier">
            <g>
                <g>
                    <polygon points="19.692,452.923 19.692,0 0,0 0,472.615 472.615,472.615 472.615,452.923 " />
                </g>
            </g>
            <g>
                <g>
                    <rect x="384" y="177.231" width="88.615" height="236.308" />
                </g>
            </g>
            <g>
                <g>
                    <rect x="275.692" y="217.305" width="88.615" height="196.234" />
                </g>
            </g>
            <g>
                <g>
                    <rect x="167.385" y="329.669" width="88.615" height="83.889" />
                </g>
            </g>
            <g>
                <g>
                    <rect x="59.077" y="281.521" width="88.615" height="132.037" />
                </g>
            </g>
        </g>
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