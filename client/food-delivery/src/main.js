
import Vue from 'vue'
import App from './App.vue'
import Notifications from 'vue-notification'
import router from './router'
import store from './store'
import Auth from './plugins/Auth'
import VueResource from 'vue-resource'
import Vue2Filters from 'vue2-filters'

import { library } from '@fortawesome/fontawesome-svg-core'
import { faUser, faRunning, faTimes, faPlusSquare, faMinusSquare, faInfo, faCreditCard, faSpinner } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(faUser, faRunning, faTimes, faPlusSquare, faMinusSquare, faInfo, faCreditCard, faSpinner);
Vue.component('font-awesome-icon', FontAwesomeIcon);

Vue.use(Notifications);
Vue.use(Auth);
Vue.use(VueResource);
Vue.use(Vue2Filters);

Vue.config.productionTip = false;

Vue.http.interceptors.push(function(request, next) {
  let self = this;
  if (request.url[0] === '/') {
    request.url = process.env.API + request.url;

    let token = Vue.auth.getToken();
    if (token)
      request.headers.set('Authorization', 'Bearer ' + token);
  }
  next(function(response) {
    if (response.status == 422) {
      response.body.errors.forEach(function (e) {
        self.$notify({
          group: 'foo',
          type: 'error',
          title: 'Error',
          text: e
        });
      })
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
