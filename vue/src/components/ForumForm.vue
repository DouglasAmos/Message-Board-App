<template>
  <h1>Create Your Forum: </h1>
  <form v-on:submit.prevent="createForum()">
    <div>
      <label>Forum Name: </label>
      <input type="text" v-model="forum.title" />
    </div>

    <input type="submit" />

  </form>
</template>

<script>
import ForumService from '../services/ForumService.js'
export default {
  data() {
    return {
      forum: {}
    }
  },
  methods: {
    createForum() {
      ForumService.createForum(this.forum)
        .then(
          (response) => {
            this.forum = response.data;
            let arr = this.$store.state.moderatedForums;
            arr.push({moderatorId: this.forum.userId, forumId: this.forum.forumId});
            this.$store.commit('SET_MODERATED_FORUMS', arr);
            this.$router.push({ name: "forum-view", params: { forumId: this.forum.forumId } });
          }
        )
    }
  }


}
</script>

