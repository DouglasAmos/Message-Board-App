import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

// Import components
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import LogoutView from '../views/LogoutView.vue';
import RegisterView from '../views/RegisterView.vue';
import ForumsView from '../views/ForumsView.vue';
import ForumView from '../views/ForumView.vue';
import PostView from '../views/PostView.vue';
import CreateForumView from '../views/CreateForumView.vue'
import PostForm from '../components/PostForm.vue'
import UserView from '../views/UserView.vue'

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/forums",
    name: "forums",
    component: ForumsView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/forums/:forumId",
    name: "forum-view",
    component: ForumView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/forums/:forumId/post/:postId",
    name: "post-view",
    component: PostView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/forums/create",
    name: "forums-create",
    component: CreateForumView,
    meta: {
      requiresAuth: true
    }

  },
  {
    path: "/post-form/:forumId",
    name: 'post-form',
    component: PostForm,
    meta: {
      requiresAuth: true
    }
    },
    {
    path: "/user/:username",
    name: 'user-view',
    component: UserView,
    }
];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

router.beforeEach((to) => {

  // Get the Vuex store
  const store = useStore();

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return {name: "login"};
  }
  // Otherwise, do nothing and they'll go to their next destination
});

export default router;
