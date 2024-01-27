<template>
    <div class="wrapped">
        <div class="postBody">
            <div class="postHeader">
                <div>
                    <h1>{{ post.title }}</h1>
                    <div class="subHeader">
                        <p id="authorName" v-on:click="goToUserView">{{ post.username }} </p>
                        <p> &#9679; {{ this.date }}</p>
                    </div>
                </div>
                <p v-if="isDeletable" class="deletePost" v-on:click="deletePost">&#x1F5D1;</p>
            </div>
            <div class="postContent">
                <div class="voteCount" :class="canVote ? 'enableVote' : 'disableVote'">
                    <h3 v-on="canVote ? { click: castVote } : {}" id="upvote" :class="upvoteStatus ? 'upvoted' : ''">&#8593;
                        {{
                            $store.state.upvotes
                        }}</h3>
                    <h3 v-on="canVote ? { click: castVote } : {}" id="downvote" :class="downvoteStatus ? 'downvoted' : ''">
                        &darr; {{
                            $store.state.downvotes * -1 }}</h3>
                </div>

                <div class="post">
                    <img class="url-image" v-if="post.url" v-bind:src="post.url" />
                    <p>{{ post.body }}</p>
                </div>
            </div>
        </div>
    </div>
</template>


<script>
import UtilityService from '../services/UtilityService.js';
import VoteService from '../services/VoteService.js';

export default {
    props: ['post'],
    data() {
        return {
            date: UtilityService.formatDate(this.post.postDate),
            vote: { commentId: this.post.headerCommentId }
        }
    },
    methods: {
        castVote($event) {
            if (this.vote.vote == 1 || this.vote.vote == -1) {
                if ($event.target.id == "upvote") {
                    let flag = false;
                    if (this.vote.vote == 1) {
                        flag = true;
                        this.vote.vote = 0;
                        this.votePut();
                        this.modifyUpvotes(-1);
                    }
                    if (this.vote.vote != 1 && !flag) {
                        this.vote.vote = 1;
                        this.votePut();
                        this.modifyUpvotes(1);
                        this.modifyDownvotes(-1);
                    }
                }
                else if ($event.target.id == "downvote") {
                    let flag = false;
                    if (this.vote.vote == -1) {
                        flag = true;
                        this.vote.vote = 0;
                        this.votePut();
                        this.modifyDownvotes(-1);
                    }
                    if (this.vote.vote != -1 && !flag) {
                        this.vote.vote = -1;
                        this.votePut();
                        this.modifyDownvotes(1);
                        this.modifyUpvotes(-1);
                    }
                }
            }
            else {
                if ($event.target.id == "upvote") {
                    this.vote.vote = 1;
                    this.votePut();
                    this.modifyUpvotes(1);
                }
                if ($event.target.id == "downvote") {
                    this.vote.vote = -1;
                    this.votePut();
                    this.modifyDownvotes(1);
                }
            }
        },
        votePost() {
            VoteService.createVote(this.vote).then(
                (response) => {
                    this.vote = response.data;
                }
            )
        },
        votePut() {
            VoteService.updateVote(this.vote).then(
                (response) => {
                    this.vote = response.data;
                }
            )
        },
        modifyUpvotes(int) {
            this.$store.commit('SET_UPVOTE', this.$store.state.upvotes + int);
        },
        modifyDownvotes(int) {
            this.$store.commit('SET_DOWNVOTE', this.$store.state.downvotes + int);
        },
        deletePost() {
            this.$emit('delete-post-button-clicked', 'Delete post was clicked');
        },
        goToUserView() {
            this.$router.push({ name: 'user-view', params: { username: this.post.username } })
        }
    },
    created() {
        this.$store.commit('SET_UPVOTE', this.post.upvotes);
        this.$store.commit('SET_DOWNVOTE', this.post.downvotes);
        if (this.$store.state.token !== '') {
            VoteService.getVote(this.post.headerCommentId).then(
                (response) => {
                    if (response.data) {
                        this.vote = response.data;
                    }
                    if (this.vote.vote == null) {
                        this.vote.vote = 0;
                        this.votePost();
                        this.hasChanged = false;
                    }
                }
            );
        }
    },
    computed: {
        upvoteStatus() {
            if (this.vote.vote == 1) {
                return true;
            } else {
                return false;
            }
        },
        downvoteStatus() {
            if (this.vote.vote == -1) {
                return true;
            } else {
                return false;
            }
        },
        isDeletable() {
            return this.post.active && this.$store.state.token !== '' && (this.$store.state.user.authorities[0].name === 'ROLE_ADMIN' ||
                this.$store.state.moderatedForums.some((forum) => {
                    return forum.forumId == this.post.forumId;
                }));
        },
        canVote() {
            return this.$store.state.token !== '';
        }
    }
}
</script>


