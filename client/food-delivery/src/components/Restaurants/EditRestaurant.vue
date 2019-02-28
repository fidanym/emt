<template>
    <div class="restaurant-container">
        <h1>Update Restaurant</h1>
        <input type="text" class="form-control" name="restaurant_name" :value="restaurant.name" placeholder="Restaurant name">
        <textarea v-model="restaurant.description" name="description" cols="30" rows="10"></textarea>
        <button @click="deleteRestaurant" class="btn btn-danger"><font-awesome-icon icon="trash-alt"/>Delete Restaurant</button>
        <input type="file" @change="fileChanged" accept="image/*">
        <button @click="uploadFile" class="btn btn-primary">Upload Image</button>
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
                file: ''
            }
        },
        methods: {
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
    }
</style>
