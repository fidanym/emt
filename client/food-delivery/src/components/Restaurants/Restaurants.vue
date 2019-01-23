<template>
    <div class="container restaurants-container m-b-20 text-center">
        <h1 class="m-t-15 font-weight-bold">List of restaurants</h1>
        <div class="restaurants-container">
            <div class="card-columns">
                <restaurant v-for="restaurant in restaurants" :title="restaurant.title" :description="restaurant.description" :cover-image="restaurant.coverImage"></restaurant>
            </div>
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
                restaurants: [
                    { title: 'Good Restaurant', coverImage: require('@/assets/images/food-1.jpg'), description: `Strip steak sirloin kielbasa drumstick pork belly. Rump pancetta buffalo,
                        capicola tail picanha short loin kevin doner andouille pork belly landjaeger. Shankle venison leberkas,
                        hamburger capicola swine shank picanha pork loin. Pancetta bacon ground round alcatra prosciutto jowl sirloin
                        pork chop sausage tenderloin swine buffalo.` },
                    { title: 'Luigi', coverImage: require('@/assets/images/food-2.jpg'), description: `Bacon ipsum dolor amet shoulder spare ribs ham,
                        tri-tip drumstick shank burgdoggen bresaola turducken. Flank ground round rump, jerky drumstick short
                        loin meatball ribeye bacon. ` },
                    { title: 'The Best Restaurant', coverImage: require('@/assets/images/food-3.jpg'), description: `Meatball chicken spare ribs buffalo, sausage bacon venison.
                        Kevin tongue t-bone doner leberkas shank pancetta landjaeger tail ground round ham hock drumstick.` },
                    { title: 'Excellent Restaurant', coverImage: require('@/assets/images/food-4.jpg'), description: `Corned beef biltong kevin, venison prosciutto ground round kielbasa jowl.
                        Fatback shank pork alcatra. Kielbasa chicken cupim strip steak hamburger, leberkas ham hock biltong
                        spare ribs bacon.` },
                    { title: 'Really Good Restaurant', coverImage: require('@/assets/images/food-5.jpg'), description: `Flank ground round rump, jerky drumstick short
                        Shankle venison leberkas, hamburger capicola swine shank picanha pork loin.` }
                ]
            }
        }
    }
</script>

<style scoped>
    .restaurants-container {
        background: #fff;
        padding-bottom: 10px;
        padding-top: 10px;
        border-radius: 10px;
    }

    .card-deck {
        column-count: 3;
    }
</style>
