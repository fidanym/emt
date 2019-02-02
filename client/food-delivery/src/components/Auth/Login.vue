<template>
    <div>
        <h4 class="text-center m-b-20 font-weight-bold">Login</h4>

        <input type="text" class="form-control m-b-15" placeholder="Username">
        <input type="password" class="form-control" placeholder="Password">
        <hr>

        <button @click="login" class="btn btn-lg btn-primary btn-block m-b-15">Sign in</button>

        <p class="text-center">
            Don't have an account? <router-link to="/auth/register">Sign up!</router-link>
        </p>
    </div>
</template>

<script>
    export default {
        name: "Login",
        data: function () {
            return {
                user: {
                    username: "",
                    password: ""
                }
            }
        },
        methods: {
            login: function () {
                this.$http.post("/auth", this.user)
                    .then(function (res) {
                        this.$auth.setToken(res.headers.authorization, Date.now() + 4 * 3600 * 1000); // 4 hours in milliseconds
                        this.$router.push('/');
                    })
            }
        }
    }
</script>

<style scoped>

</style>
