<template>
    <div class="footer" v-on-clickaway="away">
        <nav class="navbar fixed-bottom navbar-expand-sm navbar-dark bg-dark">
            <div class="container">
                <div class="navbar-nav mr-auto">
                    <div class="btn-group dropup">
                        <button id="cart-show-button" @click="toggle = !toggle" type="button" class="btn btn-outline-light">
                            <font-awesome-icon icon="shopping-basket"/> Order ({{ cartSize }})
                        </button>
                    </div>
                </div>

                <div class="navbar-nav">
                    <button v-if="userIsSuperAdmin || userIsAdmin" @click="goToItemCreate" class="btn btn-outline-light m-r-5"><font-awesome-icon icon="plus"/> Create an item</button>
                    <button v-if="userIsSuperAdmin" @click="goToCreate" class="btn btn-outline-light m-r-5"><font-awesome-icon icon="plus"/> Create company</button>
                    <button v-if="userIsSuperAdmin" @click="goToUsers" class="btn btn-outline-light"><font-awesome-icon icon="users-cog"/> Manage users</button>
                </div>

            </div>
        </nav>
        <div>
            <transition name="shopping-cart">
                <div v-show="toggle" class="container">
                    <div class="cart-container">
                        <shopping-cart></shopping-cart>
                    </div>
                </div>
            </transition>
        </div>
    </div>

</template>

<script>
    import ShoppingCart from "./ShoppingCart";
    import { directive as onClickaway } from 'vue-clickaway';

    export default {
        name: "BottomNav",
        components: {
            shoppingCart: ShoppingCart
        },
        computed: {
            user: function () {
                return this.$store.state.currentUser;
            },
            userIsSuperAdmin: function () {
                return this.user.role === "SUPER_ADMIN";
            },
            userIsAdmin: function () {
                return this.user.role === "ADMIN";
            },
            cartSize: function () {
                return (typeof this.$store.state.shoppingCart.orderItems == 'undefined') ? 0 : this.$store.state.shoppingCart.orderItems.length;
            }
        },
        data: function () {
            return {
                toggle: false,
            }
        },
        methods: {
            away: function() {
                this.toggle = false;
            },
            goToUsers: function () {
                this.$router.push('/users');
            },
            goToCreate: function () {
                this.$router.push('/create');
            },
            goToItemCreate: function () {
                this.$router.push('/create-item');
            }
        },
        created: function () {
            this.$root.$on("closeCart", () => {
                this.toggle = false;
            })
        },
        directives: {
            onClickaway: onClickaway
        }
    }
</script>

<style scoped>
    .navbar {
        background-color: #1a7a15;
        color: white;
        font-size: 1.3em;
        z-index: 1023;
    }
    .cart-container {
        padding: 15px 10px 15px 10px;
        background-color: #FFFFFF;
        position: fixed;
        bottom: 40px;
        width: 350px;
        height: 530px;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
        box-shadow: 1px 10px 8px #888888;
        z-index: 1022;
    }

    .flashing {
        animation: flash .5s;
    }

    @keyframes flash {
        0% {
            background-color: none;
        }
        50% {
            background-color: rgba(74, 250, 50, 0.71);
        }
        100% {
            background-color: none;
        }
    }

    .shopping-cart-enter,
    .shopping-cart-leave-to {
        opacity: 0;
    }

    .shopping-cart-enter-active,
    .shopping-cart-leave-active {
        transition: opacity .2s ease-out;
    }
</style>
