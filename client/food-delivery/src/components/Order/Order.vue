<template>
    <div class="card order">
        <div class="card-header">#{{ order.id }}</div>
        <div class="card-body">{{ order.status }}</div>
        <div v-if="userIsSuperAdmin" class="card-footer">
            <select name="order_status" @change="updateStatus" id="order-status">
                <option value="PENDING">Pending</option>
                <option value="IN_PROGRESS">In Progress</option>
                <option value="DELIVERING">Delivering</option>
                <option value="DELIVERED">Delivered</option>
            </select>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Order",
        props: {
            order: Object
        },
        computed: {
            user: function () {
                return this.$store.state.currentUser;
            },
            userIsSuperAdmin: function () {
                return this.user.role === "SUPER_ADMIN";
            }
        },
        methods: {
            updateStatus: function (event) {
                this.$http.post('/order/changeStatus', {id: this.order.id, status: event.target.value})
                    .then(function (res) {
                        console.log(res);
                    })
            }
        }
    }
</script>

<style scoped>

</style>
