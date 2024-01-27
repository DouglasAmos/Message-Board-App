import axios from "axios";
export default {
    getDisplayUser(username) {
        const url = '/users/' + username;
        return axios.get(url);
    }
}