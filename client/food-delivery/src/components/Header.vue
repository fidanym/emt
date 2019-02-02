<template>
    <div class="jumbotron jumbotron-fluid">
        <div class="container text-center">
            <router-link :to="'/'"><img class="img-fluid m-b-20" alt="logo" src="../assets/logo.png" width="25%"></router-link>
            <p class="lead">The website that lets you order food from anywhere</p>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Header"
    }
</script>

<style scoped>
    .jumbotron {
        background-color: #fff;
        padding: 20px 0 30px 0;
    }
</style>
