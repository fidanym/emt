<template>
    <div class="col-md-6 m-t-15">
        <div class="card order" :class="borderClass">
            <div class="card-header text-left">Order #{{ order.id }}</div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-12 font-weight-bold" :class="textClass">{{ order.status }}</div>
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
                selected: "",
                borderClass: "border-secondary",
                textClass: "text-secondary",
                status: [
                    {
                        code: "PENDING",
                        text: "Pending",
                        border: "border-danger",
                        font: "text-danger"
                    },
                    {
                        code: "IN_PROGRESS",
                        text: "In progress",
                        border: "border-warning",
                        font: "text-warning"
                    },
                    {
                        code: "DELIVERING",
                        text: "Delivering",
                        border: "border-primary",
                        font: "text-primary"
                    },
                    {
                        code: "DELIVERED",
                        text: "Delivered",
                        border: "border-success",
                        font: "text-success"
                    }
                ]
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
                        this.updateClasses();
                    })
            },
            updateClasses: function () {
                let self = this;
                this.status.filter(function (elem) {
                    if (elem.code === self.order.status) {
                        self.borderClass = elem.border;
                        self.textClass = elem.font;
                    }
                })
            }
        },
        created: function () {
            this.selected = this.order.status;
            this.updateClasses();
        },
        watch: {
            order: function () {
                this.updateClasses();
            }
        }
    }
</script>

<style scoped>

</style>
