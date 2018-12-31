<template>
    <div class="shopping-cart">
        <div class="row">
            <div class="col-md-12">
                <cart-item v-for="item in items" :restaurant-name="item.restaurantName" :name="item.name" :price="item.price" :quantity="item.quantity" :total="item.total"></cart-item>
            </div>
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
                    { restaurantName: 'Good restaurant inc.', name: 'Big Pizza', price: 15.85, quantity: 1, total: 15.85},
                    { restaurantName: 'Another restaurant', name: 'Cheeseburger', price: 8.95, quantity: 2, total: 17.90},
                    { restaurantName: 'Luigi', name: 'Tortellini with bacon', price: 12.50, quantity: 1, total: 12.50}
                ]
            }
        }
    }
</script>

<style scoped>

</style>
