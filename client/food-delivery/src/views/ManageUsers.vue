<template>
    <div class="container">
        <div v-for="user in users">
            {{ user.username }}
        </div>
    </div>
</template>

<script>
    export default {
        name: "ManageUsers",
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

</style>
