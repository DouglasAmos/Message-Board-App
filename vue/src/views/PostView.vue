<template>
    <div>
    <div>
        <h3 class="backToForum" v-on:click="backToForum()">↩ back to forum</h3>
    </div>

    <LoadingScreen v-if="isLoading"/>

    <div v-if="!isLoading">

        <PostDetails :post="post" @delete-post-button-clicked="handleDeletePost" v-bind:key="post.body"/>

        <div>

            <p class="replyPost" v-if="!isPostReply" v-on:click="clickReply()">reply to post</p>

            <Transition name="fadeComment">
            <form class="postForm" v-if="isPostReply" v-on:submit.prevent="submitComment()">
                <textarea required v-model="newComment.body"></textarea>
                <br>
                <input type="submit" />
                <button v-on:click="clickReply()">Cancel</button>
            </form>
            </Transition>
        </div>

        <div class="noComments" v-if="$store.state.comments.length == 0">
            <p>There are no comments yet, start the conversation!</p>
        </div>

        <div class="commentsBox" v-if="$store.state.comments.length != 0">

            <div>
                <Comment  v-for="comment in $store.state.comments" :key="comment.commentId" :comment="comment" :post="post"/>
            </div>

        </div>

    </div>
    </div>
</template>


<script>
import CommentService from '../services/CommentService.js';
import PostService from '../services/PostService.js';
import Comment from '../components/Comment.vue';
import PostDetails from '../components/PostDetails.vue';
import LoadingScreen from '../components/LoadingScreen.vue';

export default {
    components: {Comment, PostDetails, LoadingScreen},
    data() {
        return {
            post: {},
            isLoading: true,
            isPostReply: false,
            newComment: {
                body: ""
            } 
        }
    },
    created() {
        PostService.getPostById(this.$route.params.postId).then(
            (response) => {
                this.post = response.data;
                this.newComment = {
                    postId: this.post.postId,
                    replyId: this.post.headerCommentId,
                }
                CommentService.getCommentsByPost(this.post.postId).then(
                    (commentResponse) => {
                        this.$store.commit('LOAD_COMMENTS', this.handleThreading(commentResponse));
                        this.isLoading = false;
                    }
                )
            }
        )
    },
    methods: {
        clickReply() {
            this.isPostReply = !this.isPostReply;
        },
        submitComment() {
            if (this.newComment.body) {
                CommentService.createComment(this.newComment).then(
                    (response) => {
                        this.$store.commit('ADD_COMMENT', response.data);
                        this.resetForm();
                        this.clickReply();
                    }
                )
            }  
        },
        resetForm() {
            this.newComment.body = "";
            this.isReply = false;
        },
        backToForum() {
            this.$router.push({name: 'forum-view', params: {forumId: this.post.forumId}})
        },
        handleThreading(response) {

            let bigComments = response.data;
            let childComments = response.data;

            bigComments = bigComments.filter(
                (comment) => {
                    return comment.replyId == this.post.headerCommentId;
                }
            );
            childComments = childComments.filter(
                (comment) => {
                    return comment.replyId != this.post.headerCommentId;
                }
            );

            for (let bigComment of bigComments) {
                bigComment.children = [];
            }
            for (let comment of childComments) {
                comment.isChild = true;
                bigComments.find((bigComment) => bigComment.commentId == comment.replyId).children.push(comment);
            }
            return bigComments;
        },
        handleDeletePost() {
            this.post.body = '[deleted]';
            this.post.url = null;
            this.post.active = false;
            PostService.deletePost(this.post.postId);
        }
    }
}
</script>

