<template>
    <div>
        <h4 class="text-center m-b-20 font-weight-bold">Register</h4>

        <input type="email" class="form-control m-b-15" placeholder="Email address">
        <input type="text" class="form-control m-b-15" placeholder="Username">
        <input type="password" class="form-control" placeholder="Password">

        <hr>

        <button class="btn btn-lg btn-primary btn-block m-b-15">Register</button>

        <p class="text-center">
            Already have an account? <router-link to="/auth/login">Sign in!</router-link>
        </p>
    </div>
</template>

<script>
    export default {
        name: "Register"
    }
</script>

<style scoped>

</style>
