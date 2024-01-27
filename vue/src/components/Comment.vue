<template>
    <div class="comments">

        <CommentDetails v-bind:comment = "comment" @delete-comment-button-clicked="handleDeleteComment" v-bind:forumId="this.post.forumId"/>

        <CommentDetails v-for="child in comment.children" :key="child.commentId" :comment="child" @delete-comment-button-clicked="handleDeleteComment" v-bind:forumId="this.post.forumId" />

        <div class="reply-box">

            <p class="reply" v-if="!isReply" v-on:click="clickReply()">reply in thread</p>
            <Transition name="fadeComment">
            <form v-if="isReply" v-on:submit.prevent="submitComment()">
                <textarea required v-model="newComment.body"></textarea>
                <br>
                <input type="submit" />
                <button v-on:click="clickReply()">Cancel</button>
            </form>
            </Transition>
        </div>

    </div>
</template>


<script>
import CommentService from '../services/CommentService.js';
import CommentDetails from './CommentDetails.vue';

export default {
    componentsL: {CommentDetails},
    props: ['comment', 'post'],
    data() {
        return {
            isReply: false,
            newComment: {
                postId: this.post.postId,
                replyId: this.comment.commentId
            }
        };
    },
    methods: {
        clickReply() {
            this.isReply = !this.isReply;
        },
        submitComment() {
            if (this.newComment.body) {
                CommentService.createComment(this.newComment).then((response) => {
                    this.$store.commit('ADD_CHILD_COMMENT', response.data);
                    this.resetForm();
                });
            }
        },
        resetForm() {
            this.newComment.body = "";
            this.isReply = false;
        },
        handleDeleteComment(comment) {
            CommentService.deleteComment(comment.commentId).then((response) => {
                if (comment.isChild) {
                    this.$store.commit('DELETE_CHILD_COMMENT', comment);
                } else {
                    this.$store.commit('DELETE_COMMENT', comment);
                }
            });

        }
    },
    components: { CommentDetails }
}
</script>

