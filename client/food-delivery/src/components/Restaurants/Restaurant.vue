<template>
    <div class="card border-success">
        <router-link v-if="userIsManager" :to="{ name: 'editRestaurant', params: {id: restaurant.id, restaurant: restaurant} }" class="edit-button align-middle" title="Edit"><font-awesome-icon icon="cog"/></router-link>
        <img class="card-img-top" :src="`http://localhost:9090/api/company/get-image?id=`+restaurant.id" alt="placeholder image">
        <div class="card-body">
            <h5 class="card-title font-weight-bold">{{ restaurant.name }}</h5>
            <p class="card-text">{{ restaurant.description }}</p>
        </div>

        <div class="menu-link">
            <router-link :to="{name: 'restaurantsView', params: { id: restaurant.id, restaurantName: restaurant.name }}" :restaurant-name="restaurant.name" class="btn btn-success btn-lg">View menu</router-link>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Restaurant",
        props: {
            restaurant: Object
        },
        computed: {
            user: function () {
                return this.$store.state.currentUser;
            },
            userIsManager: function () {
                if (this.user.role === "SUPER_ADMIN")
                    return true;
                else return this.user.role === "ADMIN" && (this.user.company.id === this.restaurant.id);
            }
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
    .edit-button {
        position: absolute;
        right: 0;
        top: 0;
        height: 35px;
        width: 60px;
        padding-top: 8px;
        border: 0;
        border-top-right-radius: 2px;
        border-top-left-radius: 0;
        border-bottom-right-radius: 0;
        background-color: #28a745;
        color: #ffffff;
        font-size: 0.9em;
    }
    .edit-button:hover {
        background-color: #2dc74f;
    }
    .edit-button:active {
        background-color: #28a745;
    }
</style>
