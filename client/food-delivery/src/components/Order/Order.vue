<template>
    <div class="col-md-6 m-t-15">
        <div class="card order">
            <div class="card-header text-left">Order #{{ order.id }}</div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-12">{{ order.status }}</div>
                </div>
                <div class="row">
                    <div class="col-md-3">Address:</div>
                    <div class="col-md-9">{{ order.address }}</div>
                </div>
            </div>
            <div v-if="userIsSuperAdmin" class="card-footer">
                <select class="form-control" v-model="selected" name="order_status" @change="updateStatus" id="order-status">
                    <option value="PENDING">Pending</option>
                    <option value="IN_PROGRESS">In Progress</option>
                    <option value="DELIVERING">Delivering</option>
                    <option value="DELIVERED">Delivered</option>
                </select>
            </div>
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
