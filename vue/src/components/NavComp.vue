<template>
    

    <nav :class="isHamburger?'nav-open':'nav-closed'">
        
        <div id="navHeader">
            <div class="container" :class="isHamburger?'change':''" v-on:click="toggleNav()">
                <div class="bar1"></div>
                <div class="bar2"></div>
                <div class="bar3"></div>
            </div>
            <h1 class="title" v-on:click="goForumsTitle()" v-if="isHamburger"></h1>
        </div>

      
        <div class="nav-container">
            <Transition name="fade">
            <div class="nav" v-show="isHamburger">
                <h3 v-on:click="goHome()">Home</h3>
                <h3 v-on:click="goForums()">Forums</h3>
                <h3 v-on:click="goLogin" v-if="this.$store.state.token == ''">Login</h3>
                <h3 v-on:click="goLogout" v-if="this.$store.state.token != ''">Logout</h3>
                <div v-if="this.$store.state.token != ''">
                    <br>
                    <h3 id="favorites">Favorites:</h3>
                <h4 v-for="favorite in $store.state.favorites" :key="favorite.forumId" v-on:click="goForumById(favorite.forumId)">&#9679; {{ favorite.title }}</h4>
                </div>
                <div id="nav-spacer"></div>
                
            </div>
            </Transition>
        
            <Transition name="fade">
            <div v-show="isHamburger" class="nav-footer" v-if="this.$store.state.token != ''">
                <img v-on:click="goProfile()" src="src/img/profile-icon.png" />
                <h3 id="hello-msg" >hello, {{ this.$store.state.user.username }}!</h3>
            </div>
        </Transition>
        </div>
    
    </nav>
    
   

</template>


<script>
import ForumService from '../services/ForumService.js'
import ModeratorService from '../services/ModeratorService'

export default {
    data() {
        return {
            isHamburger: true
        }
    },
    methods: {
        goHome() {
            this.$router.push({ name: 'home' })
        },
        goForums() {
            this.$router.push({ name: 'forums' })
        },
        goForum(forumId) {
            this.$router.push({ name: 'forums', params: {forumId: forumId} })
        },
        goForumById(id){
            this.$router.push({ name: 'forum-view', params: {forumId: id} })
            
        },
        goForumsTitle() {
            this.$router.push({ name: 'forums' })
        },
        goLogin() {
            this.$router.push({ name: 'login' })
        },
        goLogout() {
            this.$router.push({ name: 'logout' })
        },
        goProfile() {
            this.$router.push({name: 'user-view', params: {username: this.$store.state.user.username}})
        },
        toggleNav() {
            this.isHamburger = !this.isHamburger;
        }
    },
    created() {
        if (this.$store.state.token != '') {
            ForumService.getFavoritedForumsByUserId(this.$store.state.user.username).then(
                (response) => {
                    this.$store.commit('LOAD_FAVORITES', response.data)
                }
            );
            ModeratorService.getAllModeratedForums(this.$store.state.user.username).then(
                (response) => {
                    this.$store.commit('SET_MODERATED_FORUMS', response.data)
                }
            )
        } 
        
    }
}
</script>

