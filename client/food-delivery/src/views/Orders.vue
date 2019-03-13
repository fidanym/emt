<template>
    <div class="container orders-container m-b-20 text-center">
        <h1>Orders</h1>
        <div id="orders-loading" v-show="ordersLoading">
            <font-awesome-icon icon="spinner" spin/>
        </div>
        <div class="row">
            <order v-for="order in orders" :order="order"></order>
        </div>
    </div>
</template>

<script>
    import Order from "../components/Order/Order";

    export default {
        name: "Orders",
        components: {
            order: Order
        },
        computed: {
            user: function () {
                return this.$store.state.currentUser;
            }
        },
        data: function () {
            return {
                ordersLoading: true,
                orders: [],
                timer1: "",
                timer2: ""
            }
        },
        methods: {
            getMyOrders: function () {
                this.$http.get("/order/my")
                    .then(function (res) {
                        this.ordersLoading = false;
                        this.orders = res.body;
                    })
            },
            getAllOrders: function () {
                this.$http.get("/order/all")
                    .then(function (res) {
                        this.ordersLoading = false;
                        this.orders = res.body;
                    })
            }
        },
        mounted: function () {
            if (this.user.role === "SUPER_ADMIN") {
                this.getAllOrders();
                this.timer1 = setInterval(() => {
                    this.getAllOrders();
                }, 15000);
            } else {
                this.getMyOrders();
                this.timer2 = setInterval(() => {
                    this.getMyOrders();
                }, 15000);
            }
        },
        beforeDestroy() {
            clearInterval(this.timer1);
            clearInterval(this.timer2);
        }
    }
</script>

<style scoped>
    .orders-container {
        background: #fff;
        padding-bottom: 10px;
        padding-top: 10px;
        border-radius: 10px;
    }

    #orders-loading {
        font-size: 50px;
        color: #5dc52f;
    }
</style>
