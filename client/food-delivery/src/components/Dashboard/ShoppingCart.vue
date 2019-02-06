<template>
    <div class="shopping-cart">
        <div class="row">
            <div class="col">
                <h5 v-if="(this.total > 0)">Your shopping basket:</h5>
                <h5 v-if="(this.total == 0)">Your shopping basket is empty</h5>
                <div class="cart">
                    <cart-item v-for="(item, index) in cart" :menu-item="item" :index="index" :key="`${index}-${item.id}`"></cart-item>
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
        computed: {
            cart: function () {
                return this.$store.state.shoppingCart;
            },
            total: function () {
                let sum = 0;
                for (let i = 0; i < this.cart.length; i++) {
                    sum += parseFloat(this.cart[i].price);
                }
                console.log(sum);
                return sum;
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
    .cart {
        height: 350px;
        overflow-y: scroll;
    }
    .cart::-webkit-scrollbar {
        display: none;
    }
</style>
