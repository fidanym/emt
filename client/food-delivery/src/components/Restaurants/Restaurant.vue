<template>
    <div class="card border-success">
        <img class="card-img-top" src="https://via.placeholder.com/300x200?text=Placeholder" alt="placeholder image">
        <div class="card-body">
            <h5 class="card-title">{{ title }}</h5>
            <p class="card-text">{{ description }}</p>
        </div>

        <div class="menu-link">
            <button class="btn btn-success btn-lg">View menu</button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Restaurant",
        props: {
            title: String,
            description: String
        }
    }
</script>

<style scoped>
    .menu-link {
        visibility: hidden;
        position: absolute;
        width: 100%;
        height: 40%;
        bottom: 0;
        font-size: 2em;
        padding-top: 20%;
        background: #fff; /* fallback for old browsers */
        background: -webkit-linear-gradient(to top, #fff 70%, rgba(255, 0, 0, 0)); /* Chrome 10-25, Safari 5.1-6 */
        background: linear-gradient(to top,  #fff 70%, rgba(255, 0, 0, 0)); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
    }
    .card:hover .menu-link {
        visibility: visible;
    }
</style>
