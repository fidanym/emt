<template>
    <nav class="navbar fixed-bottom navbar-expand-sm navbar-dark bg-dark">
        <div class="progress">
            <div class="progress-bar bg-success" role="progressbar" style="width: 25%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
        </div>
    </nav>
</template>

<script>
    export default {
        name: "BottomNav"
    }
</script>

<style scoped>
    .navbar {
        background-color: #1a7a15;
        color: white;
        font-size: 1.3em;
    }
    .navbar li{
        margin-left: 10px;
    }
</style>
