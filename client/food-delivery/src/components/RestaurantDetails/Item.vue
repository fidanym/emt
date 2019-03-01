<template>
    <div class="container item-container">
        <h1 class="text-center m-t-20">Update Item</h1>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label for="item_name">Name:</label>
                    <input type="text" class="form-control" id="item_name" name="item_name" :value="item.name" placeholder="Item name">
                </div>
                <div class="form-group">
                    <label for="price">Price:</label>
                    <input type="number" class="form-control" id="price" name="price" :value="item.price" placeholder="Price">
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label for="description">Short description:</label>
                    <textarea v-model="item.description" class="form-control" id="description" name="description" rows="5"></textarea>
                </div>
            </div>
        </div>
        <div class="row m-t-20">
            <div class="col-md-2 offset-4">
                <button @click="updateItem" class="btn btn-primary">Update Item</button>
            </div>
            <div class="col-md-2">
                <button @click="deleteItem" class="btn btn-danger"><font-awesome-icon icon="trash-alt"/> Delete Item</button>
            </div>
        </div>

    </div>
</template>

<script>
    export default {
        name: "Item",
        props: {
            item: Object
        },
        methods: {
            updateItem: function () {
                this.$http.post('/item/save')
                    .then(function () {

                    })
            },
            deleteItem: function () {
                this.$http.post('/item/delete', { id: this.item.id }, { emulateJSON: true })
                    .then(function () {
                        this.$router.push('/restaurants/' + this.item.company.id);
                    })
            }
        }
    }
</script>

<style scoped>
    .item-container {
        background: #fff;
        padding-bottom: 10px;
        padding-top: 10px;
        border-radius: 10px;
    }
</style>
