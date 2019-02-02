<template>
  <div id="app">
    <transition name="fade" mode="out-in">
      <router-view/>
    </transition>
    <notifications group="notifications"/>
  </div>
</template>

<script>
  export default {
    name: "App"
  }
</script>

<style src="@/assets/style.css"></style>
