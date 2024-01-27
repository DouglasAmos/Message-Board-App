<template>
    <div class="site-header">
        <h1 v-on:click="goHome()">Virtual Vibes</h1>
    </div>
</template>

<script>
export default {
    methods: {
        goHome() {
            this.$router.push({name: 'home'})
        }
    }
}
</script>
