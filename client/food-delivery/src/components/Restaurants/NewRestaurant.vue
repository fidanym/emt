<template>
    <div class="container restaurant-container">
        <h1 class="text-center m-t-20">Create Restaurant</h1>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label for="restaurant_name">Restaurant name:</label>
                    <input type="text" :class="{error: $v.newRestaurant.name.$invalid && $v.newRestaurant.name.$dirty}" @blur="$v.newRestaurant.name.$touch()" class="form-control" name="restaurant_name" id="restaurant_name" v-model="newRestaurant.name">
                </div>
                <div class="errorText" v-if="!$v.newRestaurant.name.required && $v.newRestaurant.name.$dirty">Restaurant name can not be blank</div>
                <div class="errorText" v-if="!$v.newRestaurant.name.minLength && $v.newRestaurant.name.$dirty">Restaurant name must
                    have at least {{$v.newRestaurant.name.$params.minLength.min}} letters</div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label for="description">Short description:</label>
                    <textarea :class="{error: $v.newRestaurant.description.$invalid && $v.newRestaurant.description.$dirty}" @blur="$v.newRestaurant.description.$touch()" v-model="newRestaurant.description" class="form-control" id="description" name="description" rows="5" maxlength="200"></textarea>
                    <p class="text-muted m-t-5">{{ 200 - newRestaurant.description.length }} character{{ 200 - newRestaurant.description === 1 ? '' : 's' }} remaining (200 max.)</p>
                </div>
                <div class="errorText" v-if="!$v.newRestaurant.description.required && $v.newRestaurant.description.$dirty">Restaurant name can not be blank</div>
                <div class="errorText" v-if="!$v.newRestaurant.description.minLength && $v.newRestaurant.description.$dirty">Restaurant
                    name must have at least {{$v.newRestaurant.description.$params.minLength.min}} letters</div>
            </div>
        </div>
        <div class="row m-t-15">
            <div class="col-md-3 offset-3">
                <button @click="updateRestaurantDetails" class="btn btn-primary">Create Restaurant</button>
            </div>
        </div>
    </div>
</template>

<script>
    import { required, minLength } from 'vuelidate/lib/validators'
    export default {
        name: "NewRestaurant",
        data: function () {
            return {
                newRestaurant: {
                    name: "",
                    description: ""
                }
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
            updateRestaurantDetails: function () {
                if (this.$v.$invalid) {
                    this.$notify({
                        group: 'notifications',
                        type: 'error',
                        title: 'Error',
                        text: 'Please fill all fields correctly'
                    });
                    return;
                }
                this.$http.post('/company/create', this.newRestaurant, { emulateJSON: true })
                    .then(function () {
                        this.$notify({
                            group: "notifications",
                            type: "success",
                            title: "Success!",
                            text: "Restaurant has been created"
                        });
                        this.$router.push('/restaurants')
                    })
            }
        },
        validations: {
            newRestaurant: {
                name: {
                    required,
                    minLength: minLength(3)
                },
                description: {
                    required,
                    minLength: minLength(10)
                }
            }
        },
        created: function () {
            if (!this.userIsSuperAdmin) {
                this.$router.push('/restaurants')
            }
        }
    }
</script>

<style scoped>
    .restaurant-container {
        background: #fff;
        padding-bottom: 10px;
        padding-top: 10px;
        border-radius: 10px;
        margin-bottom: 30px;
    }
    .text-muted {
        font-size: .8em;
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
