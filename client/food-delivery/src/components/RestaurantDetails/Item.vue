<template>
    <div class="item-container">
        <h1>Update Item</h1>
        <input type="text" class="form-control" name="item_name" :value="item.name" placeholder="Item name">
        <input type="number" class="form-control" name="price" :value="item.price" placeholder="Price">
        <textarea v-model="item.description" name="description" cols="30" rows="10"></textarea>
        <button @click="deleteItem" class="btn btn-danger"><font-awesome-icon icon="trash-alt"/>Delete Item</button>
    </div>
</template>

<script>
    export default {
        name: "Item",
        props: {
            item: Object
        },
        methods: {
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
