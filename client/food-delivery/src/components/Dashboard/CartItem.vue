<template>
    <div class="card">
        <div class="card-body">
            <div class="row">
                <div class="col-md-12">
                    <h5 class="card-title">{{ item.name }}</h5>
                    <h6 class="card-subtitle mb-2 text-muted">{{ item.restaurantName }}</h6>
                </div>
                <div class="col-md-8">
                    <span>{{ item.price | currency}} x {{ item.quantity }}</span>
                    <hr>
                    <span>{{ total | currency }}</span>
                </div>
                <div class="col-md-4">
                    <div class="row cart-item-buttons">
                        <div class="col-md-6">
                            <button @click="increaseQuantity" class="btn btn-success"><font-awesome-icon icon="plus-square"/></button>
                            <button @click="decreaseQuantity" class="btn btn-primary"><font-awesome-icon icon="minus-square"/></button>
                        </div>
                        <div class="col-md-6">
                            <button @click="deleteItem" class="btn btn-danger"><font-awesome-icon icon="times"/></button>
                            <button class="btn btn-info"><font-awesome-icon icon="info"/></button>
                        </div>
                    </div>


                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "CartItem",
        props: {
            item: Object
        },
        computed: {
            total: function () {
                return parseFloat(this.item.price) * parseFloat(this.item.quantity);
            }
        },
        methods: {
            increaseQuantity: function () {
                this.item.quantity++;
            },
            decreaseQuantity: function () {
                this.item.quantity--;
                if (this.item.quantity == 0)
                    this.deleteItem();
            },
            deleteItem: function () {
                this.$emit('delete-cart-item');
            }
        }
    }
</script>

<style scoped>
    .card {
        padding: 0;
    }
    .btn {
        width: 25px;
        height: 25px;
        line-height: 0;
        display: inline-block;
        padding: 0;
        margin: 0 0 3px 0;
    }
    .cart-item-buttons > div{
        margin: 0;
        padding: 0;
    }
    hr {
        margin: 0;
    }
    h5 {
        font-size: 1.1em;
        margin: 0 0 5px 0;
    }
    h6 {
        font-size: 0.8em;
        margin: 0 0 3px 0;
    }
</style>
