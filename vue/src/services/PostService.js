import axios from 'axios';



export default {
    getAllPostsByForumId(forumId) {
        const url = '/posts/?forumId=' + forumId;
        return axios.get(url);
    },
    getPostById(postId) {
        const url = '/posts/' + postId;
        return axios.get(url);
    },
    createPost(post){
        return axios.post('/posts',post);
    },
    getPopularPosts(){
        return axios.get('/posts/popular');
    },
    deletePost(postId) {
        return axios.put(`/posts/${postId}/remove`);
    }
}