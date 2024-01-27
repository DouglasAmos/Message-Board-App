<template>
  <ForumForm />
</template>

<script>
import ForumService from '../services/ForumService.js';
import ForumForm from '../components/ForumForm.vue';

export default {
  components: { ForumForm },
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
            this.$router.push({ name: "forum-view", params: { forumId: this.forum.forumId } })
          }
        )
    }
  }

}
</script>

