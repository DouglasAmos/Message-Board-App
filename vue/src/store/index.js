import { createStore as _createStore } from 'vuex';
import axios from 'axios';

export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      comments: [],
      favorites: [],
      upvotes: 0,
      downvotes: 0,
      moderatedForums: []
    },
    mutations: {
      SET_AUTH_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem('token', token);
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      },
      SET_USER(state, user) {
        state.user = user;
        localStorage.setItem('user', JSON.stringify(user));
      },
      LOGOUT(state) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        state.token = '';
        state.user = {};
        axios.defaults.headers.common = {};
      },
      ADD_COMMENT(state, payload) {
        payload.children = [];
        state.comments.push(payload);
      },
      ADD_CHILD_COMMENT(state, payload) {
        state.comments.find(
          (comment) => {
            return comment.commentId == payload.replyId;
          }
        ).children.push(payload);
      },
      DELETE_COMMENT(state, payload) {
        let commentToUpdate = state.comments.find(comment => comment == payload);
        commentToUpdate.body = '[deleted]';
        commentToUpdate.active = false;
      },
      DELETE_CHILD_COMMENT(state, payload) {
        let commentToUpdate = state.comments.find((comment) => {
          return comment.commentId == payload.replyId;
        }).children.find((child) => child == payload);
        commentToUpdate.body = '[deleted]';
        commentToUpdate.active = false;
      },
      LOAD_COMMENTS(state, payload) {
        state.comments = [];
        state.comments = payload;
      },
      LOAD_FAVORITES(state, payload) {
        state.favorites = [];
        state.favorites = payload;
      },
      ADD_FAVORITE(state, payload){
        state.favorites.push(payload);
      },
      DELETE_FAVORITE(state, payload){
        state.favorites = state.favorites.filter(
          (forum) => {
            return forum.forumId != payload.forumId})
          
      },
      SET_UPVOTE(state, payload) {
        state.upvotes = payload;
      },
      SET_DOWNVOTE(state, payload) {
        state.downvotes = payload;
      },
      SET_MODERATED_FORUMS(state, payload) {
        state.moderatedForums = payload;
      }
    },
  });
  return store;
}
