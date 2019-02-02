<template>
    <div class="shopping-cart">
        <div class="row">
            <div class="col">
                <h5 v-if="(this.total > 0)">Your shopping basket:</h5>
                <h5 v-if="(this.total == 0)">Your shopping basket is empty</h5>
                <div class="cart">
                    <cart-item v-for="(item, index) in items" :menu-item="item" @delete-cart-item="deleteCartItem(index)"></cart-item>
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
                items: []
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
            addItemToCart(item, restaurantName) {
                item.quantity = 1;
                item.restaurantName = restaurantName;
                this.items.push(item);
            },
            deleteCartItem: function (index) {
                this.items.splice(index, 1);
            }
        },
        mounted: function () {
            this.$root.$on('addItemToCart', (item, restaurantName) => {
                this.addItemToCart(item, restaurantName);
            })
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
