<template>
    <div class="card order">
        <div class="card-header">#{{ order.id }}</div>
        <div class="card-body">{{ order.status }}</div>
        <div v-if="userIsSuperAdmin" class="card-footer">
            <select v-model="selected" name="order_status" @change="updateStatus" id="order-status">
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
        data: function () {
            return {
                selected: ""
            }
        },
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
                this.$http.post('/order/changeStatus', {id: this.order.id, status: event.target.value}, { emulateJSON: true })
                    .then(function (res) {
                        this.order.status = event.target.value;
                    })
            }
        },
        created: function () {
            this.selected = this.order.status;
        }
    }
</script>

<style scoped>

</style>
