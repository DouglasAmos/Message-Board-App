<template>
    <div v-for="post in posts" :key="post.postId">
        <PostListItem class="box" :post="post" v-on:click="clickPost(post)" />
    </div>
</template>
<script>
import PostListItem from '../components/PostListItem.vue'

export default {
    components: {PostListItem},
    props: ['posts'],
    methods: {
        clickPost(post) {
            this.$router.push({ name: 'post-view', params: { forumId: post.forumId, postId: post.postId } })
        }
    }
}
</script>

