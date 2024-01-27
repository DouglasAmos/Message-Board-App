import axios from 'axios';

export default {
    getVote(commentId) {
        const url = "/votes?commentId=" + commentId;
        return axios.get(url);
    },
    createVote(vote) {
        const url = "/votes";
        return axios.post(url, vote);
    },
    updateVote(vote) {
        const url = "/votes";
        return axios.put(url, vote);
    },
    deleteVote(commentId) {
        const url = "/votes?commentId=" + commentId;
        return axios.delete(url);
    }
}