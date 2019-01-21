import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import Vue from 'vue'
import Router from 'vue-router'
import Auth from "./views/Auth";
import Login from "./components/Auth/Login";
import Register from "./components/Auth/Register";
import Dashboard from "./views/Dashboard";

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'dashboard',
      component: Dashboard,
      meta: { requiresAuth: true }
    },
    {
      path: '/auth',
      name: 'auth',
      component: Auth,
      redirect: '/auth/login',
      children: [
        {
          path: 'login',
          name: 'login',
          component: Login,
          meta: { requiresGuest: true }
        },
        {
          path: 'register',
          name: 'register',
          component: Register,
          meta: { requiresGuest: true }
        }
      ]
    }
  ]
})
