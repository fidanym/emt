<template>
    <div>
        <header-jumbo></header-jumbo>
        <div class="container">
            <div class="row">
                <div class="col-md-3">
                    <sidebar></sidebar>
                </div>
                <div class="col-md-9">
                    <router-view></router-view>
                </div>
            </div>
        </div>
        <bottom-nav></bottom-nav>
    </div>
</template>

<script>
    import Restaurants from "../components/Restaurants/Restaurants";
    import Sidebar from "../components/Dashboard/Sidebar";
    import Header from "../components/Header";
    import BottomNav from "../components/Dashboard/BottomNav";

    export default {
        name: "Dashboard",
        components: {
            sidebar: Sidebar,
            headerJumbo: Header,
            bottomNav: BottomNav
        }
    }
</script>

<style scoped>

</style>
