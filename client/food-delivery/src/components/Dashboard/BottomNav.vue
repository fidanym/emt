<template>
    <div class="footer">
        <nav class="navbar fixed-bottom navbar-expand-sm navbar-dark bg-dark">
            <div class="container">
                <div class="btn-group dropup">
                    <button @click="toggle = !toggle" type="button" class="btn btn-secondary">
                        Order
                    </button>
                </div>
            </div>
        </nav>
        <div>
            <div v-show="toggle" class="container">
                <div class="cart-container">
                    <shopping-cart></shopping-cart>
                </div>
            </div>
        </div>
    </div>

</template>

<script>
    import ShoppingCart from "./ShoppingCart";

    export default {
        name: "BottomNav",
        components: {
            shoppingCart: ShoppingCart
        },
        data: function () {
            return {
                toggle: false
            }
        }
    }
</script>

<style scoped>
    .navbar {
        background-color: #1a7a15;
        color: white;
        font-size: 1.3em;
    }
    .cart-container {
        padding: 15px 10px 15px 10px;
        background-color: #FFFFFF;
        position: absolute;
        bottom: 40px;
        width: 350px;
        height: 530px;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
        box-shadow: 1px 10px 8px #888888;
    }
</style>
