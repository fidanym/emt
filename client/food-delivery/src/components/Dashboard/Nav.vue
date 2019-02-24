<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-light sticky-top">
        <div class="container">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav mr-auto">
                    <router-link class="nav-item nav-link active" :to="'/profile'"><font-awesome-icon icon="user" />&nbsp; {{ user.firstName }} {{ user.lastName }}</router-link>
                    <router-link :to="'/restaurants'" class="nav-item nav-link"><font-awesome-icon icon="utensils" />&nbsp; Restaurants <span class="sr-only">(current)</span></router-link>
                </div>
                <div class="navbar-nav">
                    <button @click="logout" class="btn btn-light"><font-awesome-icon icon="power-off" /> Logout</button>
                </div>
            </div>
        </div>
    </nav>
</template>

<script>
    export default {
        name: "Nav",
        data: function () {
          return {
              loggedIn: this.$auth.loggedIn()
          }
        },
        computed: {
            user: function () {
                return this.$store.state.currentUser;
            }
        },
        methods: {
            logout: function () {
                this.$auth.destroyToken();
                this.$router.push('/auth/login');
            }
        },

    }
</script>

<style scoped>
    nav {
        margin-bottom: 30px;
        padding-bottom: 0;
    }
    .navbar-light .navbar-nav a.router-link-active {
        color: #39a903;
        padding-bottom: 0;
        margin-bottom: 0;
        border-bottom: 4px solid;
        border-color: #6dc559;
        border-radius: 3px;
    }
    .navbar-toggler {
        margin-bottom: 10px;
    }

    a {
        cursor: pointer;
    }
</style>
