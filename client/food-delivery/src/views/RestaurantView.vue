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
          restaurantName: String
        },
        components: {
            menuItem: MenuItem
        },
        data: function () {
            return {
                menuItems: [
                    { name: 'Hamburger', coverImage: require('@/assets/images/food-1.jpg'), price: "9.99", description: `Strip steak sirloin kielbasa drumstick pork belly. Rump pancetta buffalo,
                        capicola tail picanha short loin kevin doner andouille pork belly landjaeger. Shankle venison leberkas,
                        hamburger capicola swine shank picanha pork loin. Pancetta bacon ground round alcatra prosciutto jowl sirloin
                        pork chop sausage tenderloin swine buffalo.` },
                    { name: 'Chicken Burger', coverImage: require('@/assets/images/food-2.jpg'), price: "12.49", description: `Bacon ipsum dolor amet shoulder spare ribs ham,
                        tri-tip drumstick shank burgdoggen bresaola turducken. Flank ground round rump, jerky drumstick short
                        loin meatball ribeye bacon. ` },
                    { name: 'Crispy Chicken', coverImage: require('@/assets/images/food-3.jpg'), price: "10.99", description: `Meatball chicken spare ribs buffalo, sausage bacon venison.
                        Kevin tongue t-bone doner leberkas shank pancetta landjaeger tail ground round ham hock drumstick.` },
                    { name: 'Bacon Burger', coverImage: require('@/assets/images/food-4.jpg'), price: "13.25", description: `Corned beef biltong kevin, venison prosciutto ground round kielbasa jowl.
                        Fatback shank pork alcatra. Kielbasa chicken cupim strip steak hamburger, leberkas ham hock biltong
                        spare ribs bacon.` },
                    { name: 'French Fries', coverImage: require('@/assets/images/food-5.jpg'), price: "3.50", description: `Flank ground round rump, jerky drumstick short
                        Shankle venison leberkas, hamburger capicola swine shank picanha pork loin.` }
                ],
                menuLoading: false
            }
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
