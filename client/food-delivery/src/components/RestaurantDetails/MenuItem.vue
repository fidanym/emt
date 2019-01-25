<template>
    <div class="card border-success">
        <span class="badge badge-secondary price-tag align-middle">{{ item.price | currency }}</span>
        <img class="card-img-top" :src="item.coverImage" alt="placeholder image">
        <div class="card-body">
            <h5 class="card-title font-weight-bold">{{ item.title }}</h5>
            <p class="card-text">{{ item.description }}</p>
        </div>

        <div class="menu-link">
            <button @click="addToCart" class="btn btn-success btn-lg">Add to order</button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "MenuItem",
        props: {
            item: Object,
            restaurantName: String
        },
        methods: {
            addToCart: function () {
                this.$root.$emit('addItemToCart', this.item, this.restaurantName);
            }
        }
    }
</script>

<style scoped>
    .menu-link {
        visibility: hidden;
        position: absolute;
        width: 100%;
        height: 40%;
        bottom: 0;
        font-size: 2em;
        padding-top: 20%;
        background: #f7ffe6; /* fallback for old browsers */
        background: -webkit-linear-gradient(to top, #f7ffe6 70%, rgba(255, 0, 0, 0)); /* Chrome 10-25, Safari 5.1-6 */
        background: linear-gradient(to top,  #f7ffe6 70%, rgba(255, 0, 0, 0)); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
    }
    .card:hover {
        background-color: #f7ffe6;
    }
    .card:hover .menu-link {
        visibility: visible;
    }
    .price-tag {
        position: absolute;
        top: 0;
        height: 35px;
        width: 60px;
        padding-top: 10px;
        border-top-left-radius: 2px;
        border-top-right-radius: 0;
        border-bottom-left-radius: 0;
        background-color: #28a745;
    }
</style>
