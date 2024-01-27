<template>
    <div>
        <div class="header-forum">
            <h1>Forums</h1>
            <div class="searchBar">
                <form v-on:submit.prevent="filterForums()">
                    <input id="searchBarInput" type="text" v-model="searchTerm" placeholder="Search" />
                    <button type="submit">Search</button>
                    <button v-if="isListFiltered" v-on:click="clearFilter">Clear Filter</button>
                </form>
            </div>
        </div>

        <LoadingScreen v-if="isLoading" />

        

        <ForumList v-bind:forums="filteredForums" />
        </div>

        <div class="create-forum" v-if="!isLoading">
            <div>
                <h3 v-on:click="clickCreateForum()">Create New Forum</h3>
        </div>
    </div>
</template>

<script>
import ForumService from '../services/ForumService.js';
import LoadingScreen from '../components/LoadingScreen.vue';
import ForumList from '../components/ForumList.vue';

export default {
    components: { LoadingScreen, ForumList },
    data() {
        return {
            isLoading: true,
            forums: [],
            filteredForums: [],
            searchTerm: '',
            isListFiltered: false
        }
    },
    created() {
        const promise = ForumService.getForums();
        promise.then(
            (response) => {
                this.forums = response.data;
                this.filteredForums = response.data;
                this.isLoading = false;
            }
        )
    },
    methods: {
        clickCreateForum() {
            console.log('Going to forum form');
            this.$router.push({ name: 'forums-create' });
        },
        filterForums() {
            const lowerCaseTerm = this.searchTerm.toLowerCase();
            this.filteredForums = this.forums.filter(element => element.title.toLowerCase().includes(lowerCaseTerm));
            this.isListFiltered = this.searchTerm === '' ? false: true;
        },
        clearFilter() {
            this.searchTerm = '';
            this.filteredForums = this.forums;
            this.isListFiltered = false;
        }
    }

}
</script>

