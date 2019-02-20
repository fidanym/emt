<template>
    <div>
        <header-jumbo></header-jumbo>
        <nav-top></nav-top>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
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
    import Nav from "../components/Dashboard/Nav";

    export default {
        name: "Dashboard",
        components: {
            sidebar: Sidebar,
            headerJumbo: Header,
            bottomNav: BottomNav,
            navTop: Nav
        },
        created: function () {
            if (this.$auth.loggedIn()) {
                this.$http.get('/user/me')
                    .then(function (res) {
                        this.$store.commit('setCurrentUser', res.body);
                    })
                    .catch(function (res) {
                        this.$store.commit('clearCurrentUser');
                    })
            } else {
                this.$store.commit('clearCurrentUser');
            }
        }
    }
</script>

<style scoped>

</style>
