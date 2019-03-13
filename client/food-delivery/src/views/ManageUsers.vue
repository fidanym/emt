<template>
    <div class="container users-container">
        <div v-for="user in users">
            <user :user="user"></user>
        </div>
    </div>
</template>

<script>
    import User from "../components/Users/User";

    export default {
        name: "ManageUsers",
        components: {
          user: User
        },
        data: function () {
            return {
                users: Object
            }
        },
        computed: {
            user: function () {
                return this.$store.state.currentUser;
            },
            userIsSuperAdmin: function () {
                return this.user.role === "SUPER_ADMIN";
            }
        },
        methods: {
            getAllUsers: function () {
                this.$http.get('/user/all')
                    .then(function (res) {
                        this.users = res.body;
                    })
            }
        },
        created: function () {
            if (!this.userIsSuperAdmin) {
                this.$router.push('/restaurants')
            }
            this.getAllUsers();
            this.$root.$on("reloadUsers", () => {
                this.getAllUsers();
            })
        }
    }
</script>

<style scoped>
    .users-container {
        background: #fff;
        padding-bottom: 10px;
        padding-top: 10px;
        margin-bottom: 30px;
        border-radius: 10px;
    }
</style>
