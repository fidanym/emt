<template>
    <div class="container menu-container m-b-20 text-center">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><router-link :to="'/restaurants'">Restaurants</router-link></li>
                <li class="breadcrumb-item active" aria-current="page">{{ restaurantName }}</li>
            </ol>
        </nav>
        <h1 class="m-b-15 font-weight-bold">{{ restaurantName }} Menu</h1>
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

    .breadcrumb {
        background-color: #ffffff;
        padding: .75rem 1rem 0 1rem;
        margin-bottom: 0;
    }

    .breadcrumb-item > a {
        color: #39a903;
    }
</style>
