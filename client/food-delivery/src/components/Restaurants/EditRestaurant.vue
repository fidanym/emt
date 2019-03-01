<template>
    <div class="container restaurant-container">
            <h1 class="text-center m-t-20">Update Restaurant</h1>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label for="restaurant_name">Restaurant name:</label>
                    <input type="text" class="form-control" name="restaurant_name" id="restaurant_name" :value="restaurant.name">
                </div>
                <div class="form-group">
                    <label for="image">Cover image:</label>
                    <input type="file" class="form-control" id="image" @change="fileChanged" accept="image/*">
                </div>
                <img :src="imgUrl" height="300px" alt="placeholder image">
                <button @click="uploadFile" class="btn btn-primary m-t-20">Upload Image</button>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label for="description">Short description:</label>
                    <textarea v-model="restaurant.description" class="form-control" id="description" name="description" rows="5"></textarea>
                </div>
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
    export default {
        name: "EditRestaurant",
        props: {
            restaurant: Object
        },
        data: function () {
            return {
                file: '',
                imgUrl: "http://localhost:9090/api/company/get-image?id=" + this.restaurant.id
            }
        },
        methods: {
            updateRestaurantDetails: function () {
                this.$http.post('/restaurant/update')
                    .then(function () {

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
            },
            uploadFile: function () {
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
                    });
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
</style>
