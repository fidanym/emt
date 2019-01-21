
import Vue from 'vue'
import App from './App.vue'
import Notifications from 'vue-notification'
import router from './router'
import store from './store'
import Auth from './plugins/Auth'

import { library } from '@fortawesome/fontawesome-svg-core'
import { faUser, faRunning, faTimes, faPlusSquare, faMinusSquare, faInfo, faCreditCard } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(faUser, faRunning, faTimes, faPlusSquare, faMinusSquare, faInfo, faCreditCard);
Vue.component('font-awesome-icon', FontAwesomeIcon);

Vue.use(Notifications);
Vue.use(Auth);

Vue.config.productionTip = false

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
}).$mount('#app')
