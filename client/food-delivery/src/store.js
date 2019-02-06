import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    currentUser: {},
    shoppingCart: []
  },
  mutations: {
    setCurrentUser: function (state, user) {
      state.currentUser = user;
    },
    clearCurrentUser: function (state) {
      state.currentUser = {};
    },

    setShoppingCart: function (state, cart) {
      state.shoppingCart = cart;
    },
    clearShoppingCart: function (state) {
      state.shoppingCart = [];
    },
    addToCart: function (state, item) {
      state.shoppingCart.push(item)
    },
    removeFromCart: function (state, index) {
      state.shoppingCart.splice(index, 1)
    }
  },
  actions: {
    addToCart(context, item) {
      context.commit('addToCart', item)
    },
    removeFromCart(context, index) {
      context.commit('removeFromCart', index)
    }
  }
})
