<template>
    <div class="card border-success">
        <img class="card-img-top" :src="restaurant.coverImage" alt="placeholder image">
        <div class="card-body">
            <h5 class="card-title font-weight-bold">{{ restaurant.title }}</h5>
            <p class="card-text">{{ restaurant.description }}</p>
        </div>

        <div class="menu-link">
            <router-link :to="{name: 'restaurantsView', params: { slug: restaurant.slug, restaurantName: restaurant.title }}" :restaurant-name="restaurant.name" class="btn btn-success btn-lg">View menu</router-link>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Restaurant",
        props: {
            restaurant: Object
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
        background: #f7ffe6; /* fallback for old browsers */
        background: -webkit-linear-gradient(to top, #f7ffe6 70%, rgba(255, 0, 0, 0)); /* Chrome 10-25, Safari 5.1-6 */
        background: linear-gradient(to top,  #f7ffe6 70%, rgba(255, 0, 0, 0)); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
    }
    .card:hover {
        background-color: #f7ffe6;
    }
    .card:hover .menu-link {
        visibility: visible;
    }
</style>
