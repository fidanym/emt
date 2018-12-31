<template>
    <div id="sidebar" class="text-center">
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
</style>
