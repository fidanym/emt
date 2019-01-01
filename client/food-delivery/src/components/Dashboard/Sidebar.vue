<template>
    <div id="sidebar" class="text-center m-b-20">
        <h4>Username</h4>
        <hr>
        <div class="row">
            <div class="col-sm-6">
                <button class="btn btn-light"><font-awesome-icon icon="user" /> Profile</button>
            </div>
            <div class="col-sm-6">
                <button class="btn btn-light"><font-awesome-icon icon="running" /> Other</button>
            </div>
        </div>

        <div class="row cart">
            <shopping-cart></shopping-cart>
        </div>

        <div class="row">
            <button id="checkout-button" class="btn btn-primary btn-block"><font-awesome-icon icon="credit-card"></font-awesome-icon><span class="font-weight-bold"> Checkout</span></button>
        </div>
    </div>
</template>

<script>
    import ShoppingCart from "./ShoppingCart";

    export default {
        name: "Sidebar",
        components: {
            shoppingCart: ShoppingCart
        }
    }
</script>

<style scoped>
    #sidebar {
        background: #fff;
        padding: 20px;
        border-radius: 6px;
    }
    .cart {
        margin: 0 auto;
        margin-top: 10px;
    }
    #checkout-button {
        width: 90%;
        margin: 0 auto;
        margin-top: 20px;
    }
</style>
