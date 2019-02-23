
import Vue from 'vue'
import App from './App.vue'
import Notifications from 'vue-notification'
import router from './router'
import store from './store'
import Auth from './plugins/Auth'
import VueResource from 'vue-resource'
import Vue2Filters from 'vue2-filters'
import Vuelidate from 'vuelidate'
import VueStripeCheckout from 'vue-stripe-checkout'

import { library } from '@fortawesome/fontawesome-svg-core'
import { faUser, faRunning, faTimes, faPlus, faMinus, faInfo, faCreditCard, faSpinner, faPlusCircle, faPowerOff, faUtensils, faShoppingBasket } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(faUser, faRunning, faTimes, faPlus, faMinus, faInfo, faCreditCard, faSpinner, faPlusCircle, faPowerOff, faUtensils, faShoppingBasket);
Vue.component('font-awesome-icon', FontAwesomeIcon);

Vue.use(Notifications);
Vue.use(Auth);
Vue.use(VueResource);
Vue.use(Vue2Filters);
Vue.use(Vuelidate);
Vue.use(VueStripeCheckout, 'pk_test_7Iwat1JRpC2I5SpVzlc3V4L8')

Vue.config.productionTip = false;

window.$ = require('jquery');
window.JQuery = require('jquery');

Vue.http.interceptors.push(function(request, next) {
  let self = this;
  if (request.url[0] === '/') {
    request.url = 'http://localhost:9090/api' + request.url;

    let token = Vue.auth.getToken();
    if (token)
      request.headers.set('Authorization', token);
  }
  next(function(response) {
    if (response.status == 403) {
      self.$notify({
        group: 'notifications',
        type: 'error',
        title: 'Error',
        text: response.body.message
      });
      /*response.body.errors.forEach(function (e) {
        self.$notify({
          group: 'notifications',
          type: 'error',
          title: 'Error',
          text: 'Invalid credentials'
        });
      })*/
    }
  });
});

router.beforeEach(function (to, from, next) {
  if (to.matched.some(function (record) { return record.meta.requiresGuest }) && Vue.auth.loggedIn()) {
    next({
      path: '/'
    });
  } else if (to.matched.some(function (record) { return record.meta.requiresAuth }) && !Vue.auth.loggedIn()) {
    next({
      path: '/auth/login',
      query: { redirect: to.fullPath }
    })
  } else {
    next();
  }
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
