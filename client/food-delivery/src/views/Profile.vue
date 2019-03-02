<template>
    <div class="profile-container">
        <h1 class="text-center font-weight-bold m-t-20">Your profile</h1>
        <div class="m-t-30">
            <div class="row">

                <div class="col-md-6">
                    <div class="form-group row">
                        <label for="first_name" class="col-sm-2 col-form-label text-right">Name:</label>
                        <div class="col-sm-5">
                            <input type="text" :class="{error: $v.newUser.firstName.$invalid  && $v.newUser.firstName.$dirty }" @blur="$v.newUser.firstName.$touch()" v-model="newUser.firstName" class="form-control" id="first_name" placeholder="First Name">
                        </div>
                        <div class="col-sm-5">
                            <input type="text" :class="{error: $v.newUser.lastName.$invalid  && $v.newUser.lastName.$dirty }" @blur="$v.newUser.lastName.$touch()" v-model="newUser.lastName" class="form-control" id="last_name" placeholder="Last Name">
                        </div>
                    </div>
                    <div class="errorText" v-if="!$v.newUser.firstName.required  && $v.newUser.firstName.$dirty">First name is required</div>
                    <div class="errorText" v-if="!$v.newUser.firstName.minLength  && $v.newUser.firstName.$dirty">First name must have at least {{$v.newUser.firstName.$params.minLength.min}} letters</div>
                    <div class="errorText" v-if="!$v.newUser.lastName.required  && $v.newUser.lastName.$dirty">Last name is required</div>
                    <div class="errorText" v-if="!$v.newUser.lastName.minLength  && $v.newUser.lastName.$dirty">Last name must have at least {{$v.newUser.lastName.$params.minLength.min}} letters</div>
                </div>
                <div class="col-md-6">
                    <div class="form-group row">
                        <label for="address" class="col-sm-2 col-form-label text-right">Address:</label>
                        <div class="col-sm-10">
                            <input type="text" :class="{error: $v.newUser.address.$invalid  && $v.newUser.address.$dirty }" @blur="$v.newUser.address.$touch()" v-model="newUser.address" class="form-control" id="address">
                        </div>
                        <div class="errorText" v-if="!$v.newUser.address.required && $v.newUser.address.$dirty">Address is required</div>
                        <div class="errorText" v-if="!$v.newUser.address.minLength && $v.newUser.address.$dirty">Address must have at least {{$v.newUser.address.$params.minLength.min}} characters</div>
                    </div>

                    <div class="form-group row">
                        <label for="phone" class="col-sm-2 col-form-label text-right">Phone:</label>
                        <div class="col-sm-10">
                            <input type="tel" :class="{error: $v.newUser.phone.$invalid && $v.newUser.phone.$dirty }" @blur="$v.newUser.phone.$touch()" v-model="newUser.phone" class="form-control" id="phone">
                        </div>
                    </div>
                    <div class="errorText" v-if="!$v.newUser.phone.required && $v.newUser.phone.$dirty">Phone number is required</div>
                    <div class="errorText" v-if="!$v.newUser.phone.numeric && $v.newUser.phone.$dirty">Phone number must be with numbers</div>
                    <div class="errorText" v-if="!$v.newUser.phone.minLength && $v.newUser.phone.$dirty">Phone number must have at least {{$v.newUser.phone.$params.minLength.min}} digits</div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-2 offset-md-5">
                    <button @click="updateUserDetails" class="btn btn-primary btn-block m-t-20">Update Details</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import { required, minLength, alpha, numeric } from 'vuelidate/lib/validators'
    export default {
        name: "Profile",
        data: function () {
            return {
                newUser: {
                    firstName: "",
                    lastName: "",
                    username: "",
                    address: "",
                    phone: ""
                }
            }
        },
        computed: {
            user: function () {
                return this.$store.state.currentUser;
            }
        },
        methods: {
            updateUserDetails: function () {
                if (this.$v.$invalid) {
                    this.$notify({
                        group: 'notifications',
                        type: 'error',
                        title: 'Error',
                        text: 'Please fill all fields correctly'
                    });
                    return;
                }

                let self = this;
                this.$http.post("/user/update", this.newUser, {emulateJSON: true})
                    .then(function (res) {
                        self.$notify({
                            group: 'notifications',
                            type: 'success',
                            title: 'Success',
                            text: 'User details updated'
                        });
                        this.$store.commit('setCurrentUser', res.body);
                    })
            }
        },
        mounted: function () {
            this.newUser.firstName = this.user.firstName;
            this.newUser.lastName = this.user.lastName;
            this.newUser.username = this.user.username;
            this.newUser.address = this.user.address;
            this.newUser.phone = this.user.phone;
        },
        validations: {
            newUser: {
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
                address: {
                    required,
                    minLength: minLength(4)
                },
                phone: {
                    required,
                    numeric,
                    minLength: minLength(9)
                }
            }
        }
    }
</script>

<style scoped>
    .profile-container {
        background: #fff;
        padding: 10px 30px 10px 30px;
        border-radius: 10px;
    }

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
