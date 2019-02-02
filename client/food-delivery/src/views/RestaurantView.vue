<template>
    <div class="container menu-container m-b-20 text-center">
        <h1 class="m-t-15 font-weight-bold">{{ restaurantName }} Menu</h1>
        <div class="menu-container">
            <div class="card-columns">
                <menu-item v-for="item in menuItems" :item="item" :restaurantName="restaurantName"></menu-item>
            </div>
        </div>
        <div id="menu-loading" v-show="menuLoading">
            <font-awesome-icon icon="spinner" spin/>
        </div>
    </div>
</template>

<script>
    import MenuItem from "../components/RestaurantDetails/MenuItem";

    export default {
        name: "RestaurantView",
        props: {
            restaurantName: String,
            id: String
        },
        components: {
            menuItem: MenuItem
        },
        data: function () {
            return {
                menuItems: [],
                menuLoading: false
            }
        },
        created: function () {
            this.$http.get('/item/byCompanyId', {params: {'companyId': this.id}})
                .then(function (res) {
                    this.menuItems = res.body;
                })
        }
    }
</script>

<style scoped>
    .menu-container {
        background: #fff;
        padding-bottom: 10px;
        padding-top: 10px;
        border-radius: 10px;
    }

    .card-deck {
        column-count: 3;
    }

    #menu-loading {
        font-size: 50px;
        color: #5dc52f;
    }
</style>
