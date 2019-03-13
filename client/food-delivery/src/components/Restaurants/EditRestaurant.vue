<template>
    <div class="container restaurant-container">
            <h1 class="text-center m-t-20">Update Restaurant</h1>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label for="restaurant_name">Restaurant name:</label>
                    <input type="text" :class="{error: $v.newRestaurant.name.$invalid && $v.newRestaurant.name.$dirty}" @blur="$v.newRestaurant.name.$touch()" class="form-control" name="restaurant_name" id="restaurant_name" v-model="newRestaurant.name">
                </div>
                <div class="errorText" v-if="!$v.newRestaurant.name.required && $v.newRestaurant.name.$dirty">Restaurant name can not be blank</div>
                <div class="errorText" v-if="!$v.newRestaurant.name.minLength && $v.newRestaurant.name.$dirty">Restaurant name must
                    have at least {{$v.newRestaurant.name.$params.minLength.min}} letters</div>
                <div class="form-group">
                    <label for="image">Cover image:</label>
                    <input type="file" class="form-control" id="image" @change="fileChanged" accept="image/*">
                </div>
                <img v-if="imgUploaded" :src="imgUrl" class="img-fluid" alt="placeholder image"><br>
                <button @click="uploadFile" class="btn btn-primary m-t-20" :disabled="imgBtnDisabled">Upload Image</button>
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
                <button @click="updateRestaurantDetails" class="btn btn-primary">Update Restaurant Details</button>
            </div>
            <div class="col-md-3">
                <button @click="deleteRestaurant" class="btn btn-danger"><font-awesome-icon icon="trash-alt"/> Delete Restaurant</button>
            </div>
        </div>
    </div>
</template>

<script>
    import { required, minLength } from 'vuelidate/lib/validators'
    export default {
        name: "EditRestaurant",
        props: {
            restaurant: Object
        },
        data: function () {
            return {
                newRestaurant: {
                    id: "",
                    name: "",
                    description: ""
                },
                file: '',
                imgUrl: "http://localhost:9090/api/company/get-image?id=" + this.restaurant.id,
                imgUploaded: true,
                imgBtnDisabled: true
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
            updateRestaurantDetails: function () {
                this.$http.post('/company/update', this.newRestaurant, { emulateJSON: true })
                    .then(function () {
                        this.$notify({
                            group: "notifications",
                            type: "success",
                            title: "Success!",
                            text: "Restaurant details have been updated"
                        });
                    })
            },
            deleteRestaurant: function () {
                this.$http.post('/restaurant/delete', { id: this.restaurant.id }, { emulateJSON: true })
                    .then(function () {
                        this.$router.push('/restaurants');
                    })
            },
            fileChanged: function (event) {
                this.file = event.target.files[0];
                if (this.file != "")
                    this.imgBtnDisabled = false;
            },
            uploadFile: function () {
                this.imgUploaded = false;
                let formData = new FormData();
                formData.append('image', this.file);
                formData.append('id', this.restaurant.id);
                this.$http.post('/company/add-image', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                    })
                    .then(function (res) {
                        console.log(res);
                        this.imgUploaded = true;
                        this.imgBtnDisabled = true;
                    });
            }
        },
        mounted: function () {
            this.newRestaurant.id = this.restaurant.id;
            this.newRestaurant.name = this.restaurant.name;
            this.newRestaurant.description = this.restaurant.description;
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
            if (!this.userIsAdmin && !this.userIsSuperAdmin) {
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
