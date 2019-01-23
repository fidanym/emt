<template>
    <div class="shopping-cart">
        <div class="row">
            <div class="col-md-12">
                <h5 v-if="(this.total > 0)">Your shopping basket:</h5>
                <h5 v-if="(this.total == 0)">Your shopping basket is empty</h5>
                <div class="cart">
                    <cart-item v-for="(item, index) in items" :item="item" @delete-cart-item="deleteCartItem(index)"></cart-item>
                </div>
                <h5 v-if="(this.total > 0)" class="m-t-15"><span class="font-weight-bold">Total: </span> {{ this.total | currency }}</h5>
            </div>
        </div>

        <div class="row">
            <button v-if="(this.total > 0)" id="checkout-button" class="btn btn-primary btn-block"><font-awesome-icon icon="credit-card"></font-awesome-icon><span class="font-weight-bold"> Checkout</span></button>
        </div>
    </div>
</template>

<script>
    import CartItem from "./CartItem";

    export default {
        name: "ShoppingCart",
        components: {
            cartItem: CartItem
        },
        data: function () {
            return {
                items: [
                    { restaurantName: 'Good restaurant inc.', name: 'Big Pizza', price: 15.85, quantity: 1},
                    { restaurantName: 'Another restaurant', name: 'Cheeseburger', price: 8.95, quantity: 2},
                    { restaurantName: 'Luigi', name: 'Tortellini with bacon', price: 12.50, quantity: 1}
                ]
            }
        },
        computed: {
            total: function () {
                let sum = 0;
                for (let i = 0; i < this.items.length; i++) {
                    sum += parseFloat(this.items[i].price) * parseFloat(this.items[i].quantity);
                }
                return sum;
            }
        },
        methods: {
            deleteCartItem: function (index) {
                this.items.splice(index, 1);
            }
        }
    }
</script>

<style scoped>
    #checkout-button {
        width: 90%;
        margin: 0 auto;
        margin-top: 20px;
    }
</style>
