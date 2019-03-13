<template>
    <div class="container restaurant-container">
        <h1 class="text-center m-t-20">Create Item</h1>
        <div class="row">
            <div v-if="userIsSuperAdmin" class="col-md-6">
                <div class="form-group">
                    <label for="restaurant">Restaurant:</label>
                        <select v-model="newItem.companyId" name="restaurant" id="restaurant" class="form-control">
                            <option v-for="restaurant in restaurants" v-bind:value="restaurant.id">{{ restaurant.name }}</option>
                        </select>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label for="item_name">Item name:</label>
                    <input type="text" :class="{error: $v.newItem.name.$invalid && $v.newItem.name.$dirty}" @blur="$v.newItem.name.$touch()" class="form-control" name="item_name" id="item_name" v-model="newItem.name">
                </div>
                <div class="errorText" v-if="!$v.newItem.name.required && $v.newItem.name.$dirty">Item name can not be blank</div>
                <div class="errorText" v-if="!$v.newItem.name.minLength && $v.newItem.name.$dirty">Item name must have
                    at least {{$v.newItem.name.$params.minLength.min}} letters</div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label for="item_price">Price:</label>
                    <input type="text" :class="{error: $v.newItem.price.$invalid && $v.newItem.price.$dirty}" @blur="$v.newItem.price.$touch()" class="form-control" name="item_price" id="item_price" v-model="newItem.price">
                </div>
                <div class="errorText" v-if="!$v.newItem.price.required && $v.newItem.price.$dirty">Price can not be blank</div>
                <div class="errorText" v-if="!$v.newItem.price.decimal && $v.newItem.price.$dirty">Price must be in numbers</div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label for="description">Short description:</label>
                    <textarea :class="{error: $v.newItem.description.$invalid && $v.newItem.description.$dirty}" @blur="$v.newItem.description.$touch()" v-model="newItem.description" class="form-control" id="description" name="description" rows="5" maxlength="200"></textarea>
                    <p class="text-muted m-t-5">{{ 200 - newItem.description.length }} character{{ 200 -
                        newItem.description === 1 ? '' : 's' }} remaining (200 max.)</p>
                </div>
                <div class="errorText" v-if="!$v.newItem.description.required && $v.newItem.description.$dirty">Item description can not be blank</div>
                <div class="errorText" v-if="!$v.newItem.description.minLength && $v.newItem.description.$dirty">Item description must have at least {{$v.newItem.description.$params.minLength.min}} letters</div>
            </div>
        </div>
        <div class="row m-t-15">
            <div class="col-md-3 offset-3">
                <button @click="createItem" class="btn btn-primary">Create Item</button>
            </div>
        </div>
    </div>
</template>

<script>
    import { required, minLength, decimal } from 'vuelidate/lib/validators'
    export default {
        name: "CreateItem",
        data: function () {
            return {
                newItem: {
                    name: "",
                    description: "",
                    price: "",
                    companyId: ""
                },
                restaurants: []
            }
        },
        computed: {
            user: function () {
                return this.$store.state.currentUser;
            },
            userIsSuperAdmin: function () {
                return this.user.role === "SUPER_ADMIN";
            },
            userIsAdmin: function () {
                return this.user.role === "ADMIN";
            }
        },
        methods: {
            createItem: function () {
                if (this.$v.$invalid) {
                    this.$notify({
                        group: 'notifications',
                        type: 'error',
                        title: 'Error',
                        text: 'Please fill all fields correctly'
                    });
                    return;
                }
                this.$http.post('/item/create', this.newItem, { emulateJSON: true })
                    .then(function () {
                        this.$notify({
                            group: "notifications",
                            type: "success",
                            title: "Success!",
                            text: "Item has been created"
                        });
                    })
            },
            getRestaurants: function () {
                this.$http.get('/company/all')
                    .then(function (res) {
                        this.restaurants = res.body;
                        this.newItem.companyId = this.restaurants[0].id;
                    })
            }
        },
        validations: {
            newItem: {
                name: {
                    required,
                    minLength: minLength(3)
                },
                description: {
                    required,
                    minLength: minLength(10)
                },
                price: {
                    required,
                    decimal
                }
            }
        },
        created: function () {
            if (!this.userIsAdmin && !this.userIsSuperAdmin) {
                this.$router.push('/restaurants')
            }

            if (this.userIsAdmin) {
                this.newItem.companyId = this.user.company.id;
            }

            if (this.userIsSuperAdmin)
                this.getRestaurants();
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
