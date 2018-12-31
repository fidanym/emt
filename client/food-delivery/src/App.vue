<template>
  <div id="app">
    <header-jumbo></header-jumbo>
    <router-view/>
  </div>
</template>

<script>
  import Header from "./components/Header";

  export default {
    name: "App",
    components: {
      headerJumbo: Header
    }
  }
</script>

<style src="@/assets/style.css"></style>
