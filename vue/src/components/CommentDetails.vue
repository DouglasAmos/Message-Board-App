<template>
    <div class="comment" v-bind:key="comment.active">
        <div>
            <p><router-link :to="{ name: 'user-view', params: {username: comment.username}}">{{ comment.username }}</router-link> | {{ commentDate }}</p>
            <p>{{ comment.body }}</p>
        </div>
        <p v-if="isDeletable" class="deleteComment" v-on:click="deleteComment">&#x1F5D1;</p>
    </div>
</template>
<script>
import UtilityService from '../services/UtilityService.js';

export default {
    props: ['comment', 'forumId'],
    computed: {
        commentDate() {
            return UtilityService.formatDate(this.comment.postDate);
        },
        isDeletable() {
            return this.comment.active && this.$store.state.token !== '' &&
                (this.$store.state.user.authorities[0].name === 'ROLE_ADMIN' ||
                    this.$store.state.moderatedForums.some((forum) => {
                        return forum.forumId == this.forumId;
                    }));
        }
    },
    methods: {
        deleteComment() {
            this.$emit('delete-comment-button-clicked', this.comment);
        }
    }
}
</script>

