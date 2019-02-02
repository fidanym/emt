<template>
    <div>
        <h4 class="text-center m-b-20 font-weight-bold">Register</h4>

        <input type="email" class="form-control m-b-15" placeholder="Email address">
        <input type="text" class="form-control m-b-15" placeholder="Username">
        <input type="password" class="form-control" placeholder="Password">

        <hr>

        <button @click="register" class="btn btn-lg btn-primary btn-block m-b-15">Register</button>

        <p class="text-center">
            Already have an account? <router-link to="/auth/login">Sign in!</router-link>
        </p>
    </div>
</template>

<script>
    export default {
        name: "Register",
        data: function () {
            return {
                user: {
                    email: "",
                    username: "",
                    password: ""
                }
            }
        },
        methods: {
            register: function () {
                this.$http.post("/users", this.user)
                    .then(function (res) {
                        this.$notify({
                            group: "notifications",
                            type: "success",
                            title: "Success!",
                            text: "You can now login with your username and password"
                        });
                        this.$router.push('/auth/login');
                    })
            }
        }
    }
</script>

<style scoped>

</style>
