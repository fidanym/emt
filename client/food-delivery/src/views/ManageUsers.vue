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
        methods: {
            getAllUsers: function () {
                this.$http.get('/user/all')
                    .then(function (res) {
                        this.users = res.body;
                    })
            }
        },
        created: function () {
            this.getAllUsers();
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
