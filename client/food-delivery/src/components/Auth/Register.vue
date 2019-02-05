<template>
    <div>
        <h4 class="text-center m-b-20 font-weight-bold">Register</h4>
        <div class="form-row">
            <div class="col">
                <div class="errorText" v-if="!$v.user.firstName.required && $v.user.firstName.$dirty">First name is required</div>
                <div class="errorText" v-if="!$v.user.firstName.minLength && $v.user.firstName.$dirty">First name must have at least {{$v.user.firstName.$params.minLength.min}} letters</div>
                <input type="text" :class="{error: $v.user.firstName.$invalid && $v.user.firstName.$dirty }" class="form-control m-b-15" v-model.trim="user.firstName" @blur="$v.user.firstName.$touch()" placeholder="First Name">
            </div>
            <div class="col">
                <div class="errorText" v-if="!$v.user.lastName.required && $v.user.lastName.$dirty">Last name is required</div>
                <div class="errorText" v-if="!$v.user.lastName.minLength && $v.user.lastName.$dirty">Last name must have at least {{$v.user.lastName.$params.minLength.min}} letters</div>
                <input type="text" :class="{error: $v.user.lastName.$invalid && $v.user.lastName.$dirty }" class="form-control m-b-15" v-model.trim="user.lastName" @blur="$v.user.lastName.$touch()" placeholder="Last Name">
            </div>
        </div>

        <div class="errorText" v-if="!$v.user.email.required && $v.user.email.$dirty">Email is required</div>
        <input type="email" :class="{error: $v.user.email.$invalid && $v.user.email.$dirty }" class="form-control m-b-15" v-model.trim="user.email" @blur="$v.user.email.$touch()" placeholder="Email address">

        <div class="errorText" v-if="!$v.user.username.required && $v.user.username.$dirty">Username is required</div>
        <div class="errorText" v-if="!$v.user.username.minLength && $v.user.username.$dirty">Username must have at least {{$v.user.username.$params.minLength.min}} characters</div>
        <input type="text" :class="{error: $v.user.username.$invalid && $v.user.username.$dirty }" class="form-control m-b-15" v-model.trim="user.username" @blur="$v.user.username.$touch()" placeholder="Username">

        <div class="errorText" v-if="!$v.user.password.required && $v.user.password.$dirty">Password is required</div>
        <div class="errorText" v-if="!$v.user.password.minLength && $v.user.password.$dirty">Password must have at least {{$v.user.password.$params.minLength.min}} characters</div>
        <input type="password" :class="{error: $v.user.password.$invalid && $v.user.password.$dirty }" class="form-control" v-model.trim="user.password" @blur="$v.user.password.$touch()" placeholder="Password">

        <hr>

        <button @click="register" class="btn btn-lg btn-primary btn-block m-b-15">Register</button>

        <p class="text-center">
            Already have an account? <router-link to="/auth/login">Sign in!</router-link>
        </p>
    </div>
</template>

<script>
    import { required, minLength, alpha, alphaNum, email } from 'vuelidate/lib/validators'
    export default {
        name: "Register",
        data: function () {
            return {
                user: {
                    firstName: "",
                    lastName: "",
                    email: "",
                    username: "",
                    password: ""
                }
            }
        },
        methods: {
            register: function () {
                if (this.$v.$invalid) {
                    this.$notify({
                        group: 'notifications',
                        type: 'error',
                        title: 'Error',
                        text: 'Please fill all fields correctly'
                    });
                    return;
                }
                this.$http.post("/user/sign-up", this.user)
                    .then(function () {
                        this.$notify({
                            group: "notifications",
                            type: "success",
                            title: "Success!",
                            text: "You can now login with your username and password"
                        });
                        this.$router.push('/auth/login');
                    })
            }
        },
        validations: {
            user: {
                username: {
                    required,
                    minLength: minLength(4),
                    alphaNum
                },
                password: {
                    required,
                    minLength: minLength(6)
                },
                firstName: {
                    required,
                    minLength: minLength(3),
                    alpha
                },
                lastName: {
                    required,
                    minLength: minLength(3),
                    alpha
                },
                email: {
                    required,
                    email
                }
            }
        }
    }
</script>

<style scoped>
    .error {
        border: 1px solid #ff5654;
    }
    .errorText {
        font-size: 0.75em;
        color: #ff5654;
    }

    .form-control:focus {
        border-color: #43b00e;
        box-shadow: none;
    }
</style>
