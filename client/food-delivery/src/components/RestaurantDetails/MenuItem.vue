<template>
    <div class="card border-success">
        <span class="badge badge-secondary price-tag align-middle">{{ item.price | currency }}</span>
        <router-link v-if="userIsManager" :to="{ name: 'item', params: {id: item.id, item: item} }" class="edit-button align-middle" title="Edit"><font-awesome-icon icon="cog"/></router-link>
        <div class="card-body">
            <h5 class="card-title font-weight-bold m-t-15">{{ item.name }}</h5>
            <p class="card-text">{{ item.description }}</p>
        </div>
        <div class="menu-link text-center">
            <div @click="addToCart" class="transparent-button "><font-awesome-icon icon="plus-circle"></font-awesome-icon></div>
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
        computed: {
            user: function () {
                return this.$store.state.currentUser;
            },
            userIsManager: function () {
                if (this.user.role === "SUPER_ADMIN")
                    return true;
                else return this.user.role === "ADMIN" && (this.user.company.id === this.item.company.id);
            }
        },
        methods: {
            addToCart: function () {
                this.$http.post('/cart/add-item',{'username': this.user.username, itemId: this.item.id, quantity: 1}, {emulateJSON: true})
                    .then(function (res) {
                        this.$store.commit('setShoppingCart', res.body);
                        this.toggleClass('cart-show-button', 'flashing')
                    })
            },
            toggleClass: function (id, className) {
                const el = document.getElementById(id)
                el.classList.add(className)
                setTimeout(() => {
                    el.classList.remove(className)
                }, 300)
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
        bottom: 20%;
        font-size: 3em;
    }
    .transparent-button {
        color: #28a745;
        width: 100%;
        height: 100%;
        cursor: pointer;
    }
    .transparent-button:active {
        color: #248f3d;
        width: 100%;
        height: 100%;
        cursor: pointer;
    }
    .card:hover {
        background-color: #f7ffe6;
        color: #d6d6d6;
    }
    .card:hover .menu-link {
        visibility: visible;
    }
    .price-tag {
        position: absolute;
        left: 0;
        top: 0;
        height: 35px;
        width: 60px;
        padding-top: 10px;
        border-top-left-radius: 2px;
        border-top-right-radius: 0;
        border-bottom-left-radius: 0;
        background-color: #28a745;
    }
    .edit-button {
        position: absolute;
        right: 0;
        top: 0;
        height: 35px;
        width: 60px;
        padding-top: 8px;
        border: 0;
        border-radius: 0 2px 0 2px;
        background-color: #28a745;
        color: #ffffff;
        font-size: 0.9em;
    }
    .edit-button:hover {
        background-color: #2dc74f;
    }
    .edit-button:active {
        background-color: #28a745;
    }
</style>
