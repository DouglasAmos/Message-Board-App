<template>
    <div class="post-list">
        <h2>{{ post.title }}</h2>
        <p>Posted by: {{ post.username }}</p>
    </div>
</template>

<script>
export default {
    props: ['post']
}
</script>