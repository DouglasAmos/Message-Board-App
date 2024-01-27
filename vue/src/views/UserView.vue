<template>
    <h1>Profile: {{ this.$route.params.username }}</h1>

    <LoadingScreen v-if="isLoading" />

    <div v-if="!isLoading">

        <div v-if="canMakeModerator">
            <button v-on:click="displayMakeModeratorSelection">Make viber a moderator!</button>
            <div v-if="showModeratorSelection">
                <div v-if="potentialForums.length == 0">
                    <p>This user is already a moderator on all of your forums. You guys must be vibing!</p>

                </div>
                <div v-if="potentialForums.length > 0">
                    <label for='forumsDropdown'>Please Choose a Forum!</label>
                    <select id='forumsDropdown' name='forums' v-model="moderator.forumId">
                        <option v-for="forum in potentialForums" v-bind:key="forum.forumId" :value=forum.forumId>{{
                            forum.forumName }}</option>
                    </select>
                    <button v-on:click="makeModerator()">Make Moderator</button>
                </div>
            </div>
        </div>
        <div>
            <h2>Favorite Forums</h2>
            <ForumList v-if="favoritedForums.length > 0" :forums="favoritedForums"/>
            <p v-if="favoritedForums.length == 0">This user hasn't favorited any forums. D:</p>
        </div>
    </div>
</template>

<script>
import UserService from '../services/UserService';
import ModeratorService from '../services/ModeratorService';
import LoadingScreen from '../components/LoadingScreen.vue';
import ForumList from '../components/ForumList.vue';
import ForumService from '../services/ForumService';

export default {
    components: { LoadingScreen, ForumList },
    data() {
        return {
            user: {},
            moderator: {},
            isLoading: true,
            moderatedForums: [],
            potentialForums: [],
            showModeratorSelection: false,
            favoritedForums: []
        }
    },
    methods: {
        makeModerator() {

            this.moderator.moderatorId = this.user.userId
            ModeratorService.createModerator(this.moderator).then(
                (response) => {
                    this.potentialForums = this.potentialForums.filter((forum) => {
                        return forum.forumId != this.moderator.forumId
                    })
                    this.showModeratorSelection = false;
                }
            )

        },

        displayMakeModeratorSelection(){
            this.showModeratorSelection = !this.showModeratorSelection;
        }


    },
    created() {
        UserService.getDisplayUser(this.$route.params.username).then((response) => {
            this.user = response.data;
            this.isLoading = false;
            ModeratorService.getAllModeratedForums(this.user.username).then(
                (moderatorResponse) => {
                    this.moderatedForums = moderatorResponse.data
                    this.$store.state.moderatedForums.forEach((forum) => {
                        let bool = this.moderatedForums.some((moderatedForum) => {
                            return moderatedForum.forumId == forum.forumId;
                        })

                        if (!bool) {
                            this.potentialForums.push(forum)
                        }

                    })

                }
            )

            ForumService.getFavoritedForumsByUserId(this.user.username).then((favoritedResponse) => {
                this.favoritedForums = favoritedResponse.data;
                console.log(this.favoritedForums);
            })
        })
    },
    computed: {
        canMakeModerator() {
            return this.$store.state.token !== '' && this.$store.state.user.id != this.user.userId;
        }
    }
}
</script>