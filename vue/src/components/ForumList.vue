<template>
    <div v-for="forum in forums" :key="forum.forumId" class="list">
        <div class="forum" v-on:click="clickForum(forum)">
            <h2>{{ forum.title }}</h2>
        </div>
    </div>
</template>
<script>
export default {
    props: ['forums'],
    methods: {
        clickForum(forum) {
            this.$router.push({ name: 'forum-view', params: { forumId: forum.forumId } })
        }
    }
}
</script>

