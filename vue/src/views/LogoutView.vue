<template>
  <div class="logBox-container">
    <div class="logBox">
      <h1>Logout?</h1>
      <h2 v-on:click="goLogout()">Logout</h2>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    goLogout() {
      this.$store.commit("LOGOUT");
      this.$router.push("/");
    }
  }
};
</script>

