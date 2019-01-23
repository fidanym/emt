<template>
    <div id="sidebar" class="text-center m-b-20">
        <h4>Username</h4>
        <hr>
        <div class="row">
            <div class="col-6">
                <router-link :to="'/profile'" class="btn btn-light"><font-awesome-icon icon="user" /> Profile</router-link>
            </div>
            <div class="col-6">
                <router-link :to="'/'" class="btn btn-light"><font-awesome-icon icon="running" /> Home</router-link>
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
