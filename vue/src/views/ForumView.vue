<template>
    <div v-bind:key="$route.params.forumId">
    <LoadingScreen v-if="isLoading"/>

    <div v-if="!isLoading">

        <div class="header-forum">
            <div>
                <h3 v-if="!isFavorited" v-on:click="favoriteForum()" class="favorite">&#9734;</h3>
                <h3 v-if="isFavorited" v-on:click="unfavoriteForum()" class="unfavorite">&#9734;</h3>
                <div>&nbsp;&nbsp;</div>
                <h1>{{ forum.title }}</h1>
            </div>

            <div>
                Sort by: <button class="sortButton" v-bind:class="{ 'active-button': sortBy === 'date' }" type="button"
                    v-on:click="sortByMostRecent()">Most Recent</button>
                <button class="sortButton" v-bind:class="{ 'active-button': sortBy === 'popularity' }" type="button"
                    v-on:click="sortByPopularity">Popularity</button>
            </div>
        </div>
        
        <PostList v-bind:posts="posts" />

    </div>
    <div class="create-forum">
        <h3 v-on:click="toCreateForum()">Make Post</h3>
    </div>
    </div>
</template>


<script>
import ForumService from '../services/ForumService.js';
import PostService from '../services/PostService.js';
import PostList from '../components/PostList.vue';
import LoadingScreen from '../components/LoadingScreen.vue';


export default {
    components: { PostList, LoadingScreen },
    data() {
        return {
            isLoading: true,
            forum: {},
            posts: [],
            sortBy: "popularity",
            isFavorited: null
        }
    },
    created() {
        ForumService.getForumById(this.$route.params.forumId).then(
            (response) => {
                this.forum = response.data;
                this.isFavorited = this.$store.state.favorites.some((forum) => forum.forumId == this.forum.forumId)
            }
        );
        PostService.getAllPostsByForumId(this.$route.params.forumId).then(
            (response) => {
                this.posts = response.data;
                this.sortByPopularity();
                this.isLoading = false;
            }
        );
    },
    methods: {
        sortByPopularity() {
            this.posts.sort((a, b) => {
                return b.engagement - a.engagement;
            });
            this.sortBy = 'popularity';
        },
        sortByMostRecent() {
            this.posts.sort((a, b) => {
                return new Date(b.postDate) - new Date(a.postDate);
            });
            this.sortBy = 'date';
        },
        favoriteForum() {
            ForumService.addToFavorites(this.forum.forumId).then(
                (response) => {
                    this.$store.commit('ADD_FAVORITE', this.forum)
                    this.isFavorited = true;
                }
            )
            
        },
        unfavoriteForum() {
            ForumService.deleteFavorites(this.forum.forumId).then(
                (response) => {
                    this.$store.commit('DELETE_FAVORITE', this.forum)
                    this.isFavorited = false;
                }
            )

        },
        toCreateForum() {
            this.$router.push({ name: 'post-form', params: { forumId: this.$route.params.forumID } })
        }
    }
    
}
</script>


