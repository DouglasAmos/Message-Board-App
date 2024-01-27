<template>
    <div class="new-post-form">
        <form @submit.prevent="createPost()">
            <h1>Make a Post</h1>
            <div class="new-post-sub">
                <label>Title</label>
                <input type="text" v-model="post.title" required/>
            </div>
            <div class="new-post-sub">
                <label>Image</label>
                <input type="text" v-model="post.url"/>
            </div>
            <div class="new-post-sub">
                <label>Comment</label>
            </div>
            <div class="new-post-sub">
                <textarea type="text" v-model="post.body" required />
            </div>
            <input type="submit" />
        </form>
    </div>
</template>

<script>
import PostService from '../services/PostService.js';
export default {
    data(){
        return{
            post: {
                forumId: this.$route.params.forumId
            }
        }
    },
    methods:{
    createPost() {
        PostService.createPost(this.post).then(
            (response) => {
                this.post = response.data;
                this.$router.push({name: 'forum-view', params: {forumId: this.$route.params.forumId}})
                
            }
        )
    
}
    }
}
</script>

