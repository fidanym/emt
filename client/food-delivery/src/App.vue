<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
  import Header from "./components/Header";
  import BottomNav from "./components/Dashboard/BottomNav";

  export default {
    name: "App"
  }
</script>

<style src="@/assets/style.css"></style>
