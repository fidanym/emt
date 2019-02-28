import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import Vue from 'vue'
import Router from 'vue-router'
import Auth from "./views/Auth";
import Login from "./components/Auth/Login";
import Register from "./components/Auth/Register";
import Dashboard from "./views/Dashboard";
import Profile from "./views/Profile";
import Restaurants from "./components/Restaurants/Restaurants";
import RestaurantView from "./views/RestaurantView";
import Orders from "./views/Orders";

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'dashboard',
      component: Dashboard,
      redirect: '/restaurants',
      meta: { requiresAuth: true },
      children: [
        {
          path: 'profile',
          name: 'profile',
          component: Profile,
          meta: { requiresAuth: true }
        },
        {
          path: 'restaurants',
          name: 'restaurants',
          component: Restaurants,
          meta: { requiresAuth: true }
        },
        {
          path: 'restaurants/:id',
          name: 'restaurantsView',
          component: RestaurantView,
          props: true,
          meta: { requiresAuth: true }
        },
        {
          path: 'orders',
          name: 'orders',
          component: Orders,
          meta: { requiresAuth: true }
        }
      ]
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
