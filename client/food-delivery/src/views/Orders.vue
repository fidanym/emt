<template>
    <div class="container orders-container m-b-20 text-center">
        <h1>Orders</h1>
        <div id="orders-loading" v-show="ordersLoading">
            <font-awesome-icon icon="spinner" spin/>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Orders",
        data: function () {
            return {
                ordersLoading: true
            }
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
