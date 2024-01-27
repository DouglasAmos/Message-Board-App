<template>

  <div class="home">

    <h1>&#8962; Home</h1>

    <LoadingScreen v-if="isLoading"/>

    <div v-if="!isLoading">

      <div class="activeForums">
        <h2>Active Forums</h2>
        <ForumList v-bind:forums="forums" />
      </div>

      <div class="popularPosts">
        <h2>Popular Posts</h2>
        <p v-if="!arePopularPosts">There are no popular posts for today! D:</p>
        <div v-if="arePopularPosts">
          <div v-for="post in posts" :key="post.postId">
            <PostListItem class="box" :post="post" v-on:click="clickPost(post)" />
          </div>
        </div>
      </div>

    </div>

  </div>

</template>

<script>
import ForumService from '../services/ForumService';
import ForumList from '../components/ForumList.vue';
import PostListItem from '../components/PostListItem.vue';
import PostService from '../services/PostService';
import LoadingScreen from '../components/LoadingScreen.vue';

export default {
  components: { ForumList, PostListItem, LoadingScreen },
  data() {
    return {
      forums: [],
      posts: [],
      isLoading: true,
    }
  },
  created() {
    ForumService.getActiveForums().then((response) => {
      this.forums = response.data;
    })
    PostService.getPopularPosts().then(
      (response) => { this.posts = response.data; this.isLoading = false; }
    )
  },
  computed: {
    arePopularPosts() {
      return this.posts.length > 0
    }
  },
  methods: {
    clickPost(post) {
      this.$router.push({ name: 'post-view', params: { forumId: post.forumId, postId: post.postId } })
    },
  }
};
</script>
