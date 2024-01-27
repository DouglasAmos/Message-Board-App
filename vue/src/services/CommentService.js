import axios from 'axios';

export default {
    getCommentsByPost(postId) {
        const url = '/comments?postId=' + postId;
        return axios.get(url);
    },
    createComment(comment) {
        return axios.post("/comments", comment);
    },
    deleteComment(commentId) {
        return axios.put(`/comments/setActive?commentId=${commentId}`);
    }
}