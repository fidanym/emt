<template>
    <div class="card">
        <div class="card-body">
            <div class="row">
                <div class="col-md-12">
                    <h5 class="card-title">{{ item.name }} <small class="text-muted">{{ item.company.name }}</small></h5>
                </div>
                <div class="col-md-12 col">
                    <span>{{ item.price | currency}} x {{ menuItem.quantity }}</span>
                </div>
                <div class="cart-item-buttons">
                    <div class="cart-buttons">
                        <button @click="deleteItem" class="btn btn-outline-danger"><font-awesome-icon icon="times"/></button>
                        <button class="btn btn-outline-info" data-toggle="popover" data-trigger="focus" :data-title="item.name" :data-content="item.description"><font-awesome-icon icon="info"/></button>
                    </div>
                    <div class="cart-buttons">
                        <button @click="increaseQuantity" class="btn btn-outline-success"><font-awesome-icon icon="plus"/></button>
                        <button @click="decreaseQuantity" class="btn btn-outline-secondary"><font-awesome-icon icon="minus"/></button>
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
            menuItem: Object,
            index: Number
        },
        data: function () {
            return {
                item: this.menuItem.item
            }
        },
        computed: {
            user: function () {
                return this.$store.state.currentUser;
            }
        },
        methods: {
            deleteItem: function () {
                this.$http.post('/cart/remove-item',{'username': this.user.username, itemId: this.item.id}, {emulateJSON: true})
                    .then(function (res) {
                        this.$store.commit('setShoppingCart', res.body)
                    });
            },
            increaseQuantity: function () {
                this.$http.post('/cart/add-item',{'username': this.user.username, itemId: this.item.id, quantity: 1}, {emulateJSON: true})
                    .then(function (res) {
                        this.$store.commit('setShoppingCart', res.body)
                    });
            },
            decreaseQuantity: function () {
                this.$http.post('/cart/decrease-qty',{'username': this.user.username, itemId: this.item.id, quantity: 1}, {emulateJSON: true})
                    .then(function (res) {
                        this.$store.commit('setShoppingCart', res.body)
                    });
            }
        },
        created: function () {
            $(function () {
                $('[data-toggle="popover"]').popover({
                    container: 'body',
                    placement: 'bottom'
                });
            })
        }
    }
</script>

<style scoped>
    .card {
        padding: 0;
        border-radius: 0;
    }
    .card:first-child {
        border-top-left-radius: 5px;
        border-top-right-radius: 5px;
    }
    .card:last-child {
        border-bottom-left-radius: 5px;
        border-bottom-right-radius: 5px;
    }
    .card:hover {
        background-color: #f7ffe6;
    }
    .btn {
        width: 25px;
        height: 25px;
        line-height: 0;
        display: inline-block;
        padding: 0;
        margin: 0 1px 0 0;
    }
    .cart-item-buttons{
        position: absolute;
        top: 5px;
        right: 5px;
    }
    .cart-buttons {
        display: block;
        margin-top: 5px;
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

    @media only screen and (max-width: 450px) {
        .btn {
            margin-left: 5px;
            font-size: 18px;
            width: 30px;
            height: 30px;
        }
    }
</style>
