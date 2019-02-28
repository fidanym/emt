<template>
    <div class="card order">
        <div class="card-header">{{ order.id }}</div>
        <div class="card-body">{{ order.status }}</div>
    </div>
</template>

<script>
    export default {
        name: "Order",
        props: {
            order: Object
        }
    }
</script>

<style scoped>

</style>
