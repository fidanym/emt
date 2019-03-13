<template>
    <transition name="modal">
        <div class="modal-mask">
            <div class="modal-wrapper">
                <div class="modal-container">

                    <div class="modal-header">
                        <h3>Update {{user.firstName + " " + user.lastName}}'s settings</h3>
                    </div>

                    <div class="modal-body">
                        <div class="row">

                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="first_name" class="col-form-label text-right">First Name:</label>
                                    <div class="col-sm-12">
                                        <input type="text" :class="{error: $v.newUser.firstName.$invalid && $v.newUser.firstName.$dirty}" @blur="$v.newUser.firstName.$touch()" v-model="newUser.firstName" class="form-control" id="first_name">
                                    </div>
                                </div>
                                <div class="errorText" v-if="!$v.newUser.firstName.required && $v.newUser.firstName.$dirty">First name is required</div>
                                <div class="errorText" v-if="!$v.newUser.firstName.minLength && $v.newUser.firstName.$dirty">First name must have at least {{$v.newUser.firstName.$params.minLength.min}} letters</div>

                                <div class="form-group">
                                    <label for="last_name" class="col-form-label text-right">Last Name:</label>
                                    <div class="col-sm-12">
                                        <input type="text" :class="{error: $v.newUser.lastName.$invalid && $v.newUser.lastName.$dirty}" @blur="$v.newUser.lastName.$touch()" v-model="newUser.lastName" class="form-control" id="last_name">
                                    </div>
                                </div>
                                <div class="errorText" v-if="!$v.newUser.lastName.required && $v.newUser.lastName.$dirty">First name is required</div>
                                <div class="errorText" v-if="!$v.newUser.lastName.minLength && $v.newUser.lastName.$dirty">First name must have at least {{$v.newUser.lastName.$params.minLength.min}} letters</div>

                                <div class="form-group">
                                    <label for="address" class="col-form-label text-right">Address:</label>
                                    <div class="col-sm-12">
                                        <input type="text" :class="{error: $v.newUser.address.$invalid && $v.newUser.address.$dirty}" @blur="$v.newUser.address.$touch()" v-model="newUser.address" class="form-control" id="address">
                                    </div>
                                </div>
                                <div class="errorText" v-if="!$v.newUser.address.required && $v.newUser.address.$dirty">First name is required</div>
                                <div class="errorText" v-if="!$v.newUser.address.minLength && $v.newUser.address.$dirty">First name must have at least {{$v.newUser.address.$params.minLength.min}} letters</div>

                                <div class="form-group">
                                    <label for="phone" class="col-form-label text-right">Phone:</label>
                                    <div class="col-sm-12">
                                        <input type="tel" :class="{error: $v.newUser.phone.$invalid && $v.newUser.phone.$dirty}" @blur="$v.newUser.phone.$touch()" v-model="newUser.phone" class="form-control" id="phone">
                                    </div>
                                </div>
                                <div class="errorText" v-if="!$v.newUser.phone.required && $v.newUser.phone.$dirty">First name is required</div>
                                <div class="errorText" v-if="!$v.newUser.phone.minLength && $v.newUser.phone.$dirty">First name must have at least {{$v.newUser.phone.$params.minLength.min}} letters</div>

                                <div class="form-group">
                                    <label for="restaurant">Manages restaurant:</label>
                                    <div class="col-sm-12">
                                        <select v-model="newUser.company" @change="companyChanged" name="restaurant" id="restaurant" class="form-control">
                                            <option value="0">None</option>
                                            <option v-for="restaurant in restaurants" v-bind:value="restaurant.id">{{ restaurant.name }}</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button class="btn btn-light" @click="$emit('closeUserModal')">Cancel</button>
                        <button @click="updateUserDetails" class="btn btn-primary">Update Details</button>
                    </div>
                </div>

            </div>
        </div>
    </transition>
</template>

<script>
    import { required, minLength, alpha, numeric } from 'vuelidate/lib/validators'
    export default {
        name: "UserSettings",
        props: {
            user: Object
        },
        data: function () {
            return {
                newUser: {
                    firstName: "",
                    lastName: "",
                    username: "",
                    address: "",
                    phone: "",
                    company: "",
                    role: ""
                },
                restaurants: []
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
                    .then(function () {
                        self.$notify({
                            group: 'notifications',
                            type: 'success',
                            title: 'Success',
                            text: 'User details updated'
                        });
                        this.$root.$emit('reloadUsers');
                        this.closeModal();
                    })
            },
            closeModal: function () {
                this.$emit("closeUserModal")
            },
            getRestaurants: function () {
                this.$http.get('/company/all')
                    .then(function (res) {
                        this.restaurants = res.body;
                    })
            },
            companyChanged: function () {
                if (this.newUser.company == 0) {
                    this.newUser.role = "CLIENT";
                }
                else
                    this.newUser.role = "ADMIN";
            }
        },
        mounted: function () {
            this.newUser.firstName = this.user.firstName;
            this.newUser.lastName = this.user.lastName;
            this.newUser.username = this.user.username;
            this.newUser.address = this.user.address;
            this.newUser.phone = this.user.phone;
            this.newUser.role = this.user.role;
            this.newUser.company = (typeof this.user.company == "object" && this.user.company != null) ? this.user.company.id : 0; // pls no, change so that user has only id, not entire company object
            this.getRestaurants();
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
    .modal-mask {
        position: fixed;
        z-index: 9998;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, .5);
        display: table;
        transition: opacity .2s ease;
    }

    .modal-wrapper {
        display: table-cell;
    }

    .modal-container {
        width: 800px;
        max-height: 90%;
        margin: 0 auto;
        padding: 20px 30px;
        background-color: #fff;
        border-radius: 2px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
        transition: all .2s ease;
        font-family: Helvetica, Arial, sans-serif;
        overflow-y: scroll;
    }

    .modal-body {
        margin: 20px 0;
    }

    .modal-enter {
        opacity: 0;
    }

    .modal-leave-active {
        opacity: 0;
    }

    .modal-enter .modal-container,
    .modal-leave-active .modal-container {
        -webkit-transform: scale(1.02);
        transform: scale(1.02);
    }

    .items-list {
        max-height: 400px;
        overflow-x: hidden;
        overflow-y: scroll;
    }

    @media only screen and (max-width: 700px) {
        .modal-container {
            width: 95%;
        }
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
