<template>
    <div class="card border-success">
        <span class="badge badge-secondary price-tag align-middle">{{ item.price | currency }}</span>
        <div class="card-body">
            <h5 class="card-title font-weight-bold m-t-15">{{ item.name }}</h5>
            <p class="card-text">{{ item.description }}</p>
        </div>
        <div class="menu-link text-center">
            <div @click="addToCart" class="transparent-button "><font-awesome-icon icon="plus-circle"></font-awesome-icon></div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "MenuItem",
        props: {
            item: Object,
            restaurantName: String
        },
        methods: {
            addToCart: function () {
                this.$http.post('/cart/add-item',{'username':"fidanym", itemId: this.item.id, quantity: 1}, {emulateJSON: true})
                    .then(function (res) {
                        this.$store.commit('setShoppingCart', res.body);
                    })
            }
        }
    }
</script>

<style scoped>
    .menu-link {
        visibility: hidden;
        position: absolute;
        width: 100%;
        height: 40%;
        bottom: 20%;
        font-size: 3em;
    }
    .transparent-button {
        color: #28a745;
        width: 100%;
        height: 100%;
        cursor: pointer;
    }
    .transparent-button:active {
        color: #248f3d;
        width: 100%;
        height: 100%;
        cursor: pointer;
    }
    .card:hover {
        background-color: #f7ffe6;
        color: #d6d6d6;
    }
    .card:hover .menu-link {
        visibility: visible;
    }
    .price-tag {
        position: absolute;
        left: 0;
        top: 0;
        height: 35px;
        width: 60px;
        padding-top: 10px;
        border-top-left-radius: 2px;
        border-top-right-radius: 0;
        border-bottom-left-radius: 0;
        background-color: #28a745;
    }
</style>
