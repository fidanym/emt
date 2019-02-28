<template>
    <transition name="modal">
        <div class="modal-mask">
            <div class="modal-wrapper">
                <div class="modal-container">

                    <div class="modal-header">
                        <h3>Order Summary</h3>
                    </div>

                    <div class="modal-body">
                        <div class="items-list">
                            <div v-for="(orderItem, index) in items">
                                <div class="row">
                                    <div class="col-md-1 my-auto">
                                        {{index + 1}} .
                                    </div>
                                    <div class="col-md-5 my-auto">
                                        <h5>{{ orderItem.item.name }} <small>{{ orderItem.item.company.name }}</small></h5>
                                    </div>
                                    <div class="col-md-4 my-auto">
                                        {{ orderItem.item.price | currency }} x {{ orderItem.quantity }}
                                    </div>
                                    <div class="col-md-2 my-auto">
                                        {{ (orderItem.item.price * orderItem.quantity) | currency }}
                                    </div>
                                </div>
                                <hr>
                            </div>
                        </div>
                        <div class="row m-t-15">
                            <div class="col-md-4">
                                Items: {{ orderItems.length }}
                            </div>
                            <div class="col-md-4 offset-md-4">
                                <h4>Total: {{ total | currency }}</h4>
                            </div>
                        </div>

                        <h5 class="m-t-15">Delivery information</h5>
                        <hr>

                        <div class="row">
                            <div class="col-md-12 m-t-15">
                                <input type="checkbox" value="1" name="defaultAddress" v-model="checkedAddress" @click="toggleAddressInput" checked> Use address from my profile information
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <input type="text" class="form-control m-t-15" :value="address" name="address" :disabled="checkedAddress">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12 m-t-15">
                                <input type="checkbox" value="1" name="defaultPhone" v-model="checkedPhone" @click="togglePhoneInput" checked> Use phone number from my profile information
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <input type="text" class="form-control m-t-15" :value="phone" name="phone" :disabled="checkedPhone">
                            </div>
                        </div>

                    </div>

                    <div class="modal-footer">
                        <button class="btn btn-light" @click="$emit('cancelOrder')">Cancel</button>
                        <button class="btn btn-primary" @click="checkout"><font-awesome-icon icon="credit-card"></font-awesome-icon> Pay</button>
                    </div>
                </div>

            </div>
            <vue-stripe-checkout
                    ref="checkoutRef"
                    name="Food Delivery"
                    currency="$"
                    :allow-remember-me="false"
                    @done="done"
                    @opened="opened"
                    @closed="closed"
                    @canceled="canceled"
            ></vue-stripe-checkout>
        </div>
    </transition>
</template>

<script>
    export default {
        name: "Checkout",
        props: {
            orderItems: Array
        },
        data: function () {
            return {
                items: this.orderItems,
                address: "",
                checkedAddress: true,
                checkedPhone: true,
                modalState: true
            }
        },
        computed: {
            user: function () {
                return this.$store.state.currentUser;
            },
            total: function () {
                let sum = 0;
                for (let i = 0; i < this.items.length; i++) {
                    sum += this.items[i].item.price * this.items[i].quantity;
                }
                return sum;
            }
        },
        created: function () {
            this.setUserAddress();
            this.setUserPhone();
        },
        methods: {
            setUserAddress: function () {
                this.address = this.user.address;
            },
            setUserPhone: function () {
                this.phone = this.user.phone;
            },
            toggleAddressInput: function () {
                if (this.checkedAddress === true) {
                    this.checkedAddress = false;
                }
                else {
                    this.checkedAddress = true;
                    this.setUserAddress();
                }
            },
            togglePhoneInput: function () {
                if (this.checkedPhone === true) {
                    this.checkedPhone = false;
                }
                else {
                    this.checkedPhone = true;
                    this.setUserPhone();
                }
            },
            async checkout () {
                // token - is the token object
                // args - is an object containing the billing and shipping address if enabled
                const { token, args } = await this.$refs.checkoutRef.open({
                    amount: this.total * 100
                });
            },
            done ({token, args}) {
                // Fires when the stripe transaction is successfully completed
                this.$http.post("/order/checkout", {stripeToken: token.id, stripeEmail: token.email}, { emulateJSON:true });
                this.$emit('cancelOrder');
                this.$root.$emit('closeCart');
                this.$store.commit('clearShoppingCart');
                this.$router.push('/orders');
            },
            opened () {
                // Fires when the stripe window opens
            },
            closed () {
                // Fires when the stripe window closes for any reason
            },
            canceled () {
                // Fires when the stripe window is closed through the close button
            }
        }
    }
</script>

<style scoped>
    .modal-mask {
        position: fixed;
        z-index: 9998;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, .5);
        display: table;
        transition: opacity .2s ease;
    }

    .modal-wrapper {
        display: table-cell;
    }

    .modal-container {
        width: 800px;
        max-height: 90%;
        margin: 0 auto;
        padding: 20px 30px;
        background-color: #fff;
        border-radius: 2px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
        transition: all .2s ease;
        font-family: Helvetica, Arial, sans-serif;
        overflow-y: scroll;
    }

    .modal-body {
        margin: 20px 0;
    }

    .modal-enter {
        opacity: 0;
    }

    .modal-leave-active {
        opacity: 0;
    }

    .modal-enter .modal-container,
    .modal-leave-active .modal-container {
        -webkit-transform: scale(1.05);
        transform: scale(1.05);
    }

    .items-list {
        max-height: 400px;
        overflow-x: hidden;
        overflow-y: scroll;
    }

    small {
        display: block;
    }

    .my-auto {
        margin-top: auto;
        margin-bottom: auto;
    }

    ::-webkit-scrollbar {
        display: none;
    }

    @media only screen and (max-width: 700px) {
        .modal-container {
            width: 95%;
        }
    }

</style>
