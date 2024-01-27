<template>

  <div class="appDisplay" >
    <NavComp class="navDisplay" v-bind:key="$store.state.token"/>
    <div class="main-body">
      <SiteHeader />
      <div class="routing">
        <router-view v-bind:key="$route.fullPath" />
      </div>
    </div>
    
  </div>

</template>


<script>
import NavComp from './components/NavComp.vue'
import SiteHeader from './components/SiteHeader.vue'

export default {
  components: {NavComp, SiteHeader}
}
</script>

