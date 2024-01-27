import axios from "axios";

export default {
    getModeratorById(forumId){
        const url = '/moderators?forumId=' + forumId;
        return axios.get(url);
    },
    getAllModeratedForums(username){
        const url = '/moderators/forums?username=' + username;
        return axios.get(url);
    },
    createModerator(moderator){
        const url = '/moderators';
        return axios.post(url, moderator); 
    },
    deleteModerator(moderator){
        const url = '/moderators';
        return axios.delete(url, moderator); 
    }
}