<template>

    <div :class="showLoading?'show-div':'hide-div'">
        <h1>Loading...</h1>
    </div>

</template>


<script>
export default {
    data() {
        return {
            showLoading: false
        }
    },
    created() {
        this.sleep(6000).then(
            () => this.showLoading = true
        )
    },
    methods: {
        sleep(ms) {
            return new Promise(resolve => setTimeout(resolve, ms));
        }
    }
}
</script>


