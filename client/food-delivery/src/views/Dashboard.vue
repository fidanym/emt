<template>
    <div class="container">
        <div class="row">
            <div class="col-md-3">
                <sidebar></sidebar>
            </div>
            <div class="col-md-9">
                <restaurants></restaurants>
            </div>
        </div>
    </div>
</template>

<script>
    import Restaurants from "../components/Restaurants/Restaurants";
    import Sidebar from "../components/Dashboard/Sidebar";

    export default {
        name: "Dashboard",
        components: {
            sidebar: Sidebar,
            restaurants: Restaurants
        }
    }
</script>

<style scoped>

</style>
