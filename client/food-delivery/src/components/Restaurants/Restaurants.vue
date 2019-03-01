<template>
    <div class="container restaurants-container m-b-20 text-center">
        <h1 class="m-t-15 font-weight-bold">List of restaurants</h1>
        <div class="restaurants-container">
            <div class="card-columns">
                <restaurant v-for="restaurant in restaurants" :restaurant="restaurant"></restaurant>
            </div>
        </div>
        <div id="restaurants-loading" v-show="restaurantsLoading">
            <font-awesome-icon icon="spinner" spin/>
        </div>
    </div>
</template>

<script>
    import Restaurant from "./Restaurant";

    export default {
        name: "Restaurants",
        components: {
            restaurant: Restaurant
        },
        data: function () {
            return {
                restaurants: [],
                restaurantsLoading: false
            }
        },
        created: function () {
            this.$http.get('/company/all')
                .then(function (res) {
                    this.restaurants = res.body;
                })
        }
    }
</script>

<style scoped>
    .restaurants-container {
        background: #fff;
        padding-bottom: 10px;
        padding-top: 10px;
        border-radius: 10px;
        margin-bottom: 30px;
    }

    .card-deck {
        column-count: 3;
    }

    #restaurants-loading {
        font-size: 50px;
        color: #5dc52f;
    }
</style>
