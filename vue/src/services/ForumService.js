import axios from 'axios';


export default {

    getForums() {
        return  axios.get("/forums");
    },
    getForumById(forumId) {
        const url = "/forums/" + forumId;
        return  axios.get(url);
    },
    createForum(forum){
        const url = "/forums";
        return axios.post(url, forum);
    },
    getFavoritedForumsByUserId(username) {
        return axios.get('/forums/favorites?username=' + username)
    },
    addToFavorites(forumId){
        return axios.post('/forums/favorite/' + forumId)
    },
    deleteFavorites(forumId){
        return axios.delete('/forums/favorite/' + forumId)
    },
    getActiveForums() {
        return axios.get('/forums/active')
    }

}