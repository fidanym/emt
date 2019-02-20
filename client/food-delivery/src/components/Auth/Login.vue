<template>
    <div>
        <h4 class="text-center m-b-20 font-weight-bold">Login</h4>

        <div class="errorText" v-if="!$v.user.username.required && $v.user.username.$dirty">Username is required</div>
        <div class="errorText" v-if="!$v.user.username.minLength && $v.user.username.$dirty">Username must have at least {{$v.user.username.$params.minLength.min}} characters</div>
        <input type="text" :class="{error: $v.user.username.$error }" class="form-control m-b-15" v-model.trim="user.username" @input="$v.user.username.$touch()" placeholder="Username">
        <div class="errorText" v-if="!$v.user.password.required && $v.user.password.$dirty">Password is required</div>
        <div class="errorText" v-if="!$v.user.password.minLength && $v.user.password.$dirty">Password must have at least {{$v.user.password.$params.minLength.min}} characters</div>
        <input type="password" :class="{error: $v.user.password.$invalid && $v.user.password.$dirty }" class="form-control" v-model.trim="user.password" @input="$v.user.password.$touch()" v-on:keyup.enter="login" placeholder="Password">
        <hr>
        <button @click="login" class="btn btn-lg btn-primary btn-block m-b-15">Sign in</button>
        <p class="text-center">
            Don't have an account? <router-link to="/auth/register">Sign up!</router-link>
        </p>
    </div>
</template>

<script>
    import { required, minLength, alphaNum } from 'vuelidate/lib/validators'
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
                let self = this;
                if (this.$v.$invalid) {
                    self.$notify({
                        group: 'notifications',
                        type: 'error',
                        title: 'Error',
                        text: 'Please fill all fields correctly'
                    });
                    return;
                }
                this.$http.post("/login", this.user)
                    .then(function (res) {
                        self.$notify({
                            group: 'notifications',
                            type: 'success',
                            title: 'Logged in',
                            text: 'Welcome ' + self.user.username
                        });
                        this.$auth.setToken(res.headers.get('Authorization'), Date.now() + 4 * 3600 * 1000); // 4 hours in milliseconds
                        this.$router.push('/');

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
