<template>
    <div>
        <header-jumbo></header-jumbo>
        <nav-top v-if="loaded"></nav-top>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <transition name="fade">
                        <router-view></router-view>
                    </transition>
                    <div id="content-loading" v-if="!loaded">
                        <font-awesome-icon icon="spinner" spin/>
                    </div>
                </div>
            </div>
        </div>
        <bottom-nav v-if="loaded"></bottom-nav>
    </div>
</template>

<script>
    import Header from "../components/Header";
    import BottomNav from "../components/Dashboard/BottomNav";
    import Nav from "../components/Dashboard/Nav";

    export default {
        name: "Dashboard",
        components: {
            headerJumbo: Header,
            bottomNav: BottomNav,
            navTop: Nav
        },
        data: function () {
            return {
                loaded: false
            }
        },
        created: function () {
            if (this.$auth.loggedIn()) {
                this.$http.get('/user/me')
                    .then(function (res) {
                        this.$store.commit('setCurrentUser', res.body);
                        this.loaded = true;
                    })
                    .catch(function () {
                        this.$store.commit('clearCurrentUser');
                        this.loaded = false;
                    })
            } else {
                this.$store.commit('clearCurrentUser');
            }
        }
    }
</script>

<style scoped>
    #content-loading {
        font-size: 50px;
        color: #5dc52f;
    }

    .fade-enter,
    .fade-leave-to {
        opacity: 0;
    }

    .fade-enter-active,
    .fade-leave-active {
        transition: opacity .3s ease-out;
    }
</style>
