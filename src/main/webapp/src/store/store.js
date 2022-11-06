import { createStore } from 'vuex'
import { decodeCredential } from 'vue3-google-login';

// Create a new store instance
const store = createStore({
    state() {
        return {
            token: null,
            id: null
        }
    },
    // ways to change our state (must by synchronous) store.commit("SET_USER", user)
    mutations: {
        SET_TOKEN(state, token) {
            state.token = token;
        },
        SET_ID(state, id) {
            state.id = id;
        }
    },
    // ways to call mutations (can be asynchronous) store.dispatch("setUser", user)
    actions: {
        setToken(context, token) {
            sessionStorage.setItem("user_token", token);
            context.commit('SET_TOKEN', token);
        },
        findToken(context) {
            let token = sessionStorage.getItem("user_token");
            context.commit('SET_TOKEN', token);
        },
        // User ID (since the user id is != from the google ID) to avoid making multiple get to the api
        setId(context, id) {
            sessionStorage.setItem("user_id", id);
            context.commit('SET_ID', id);
        },
        findId(context) {
            let id = sessionStorage.getItem("user_id");
            context.commit('SET_ID', id);
        }
    },
    getters: {
        getDecodedToken(state) {
            
            if(state.token == null)
                store.dispatch('findToken');

            if(state.token == null)
                return null;
    
            const userData = decodeCredential(state.token);
            
            return userData;
        },
        getToken(state) {

            if(state.token == null)
                store.dispatch('findToken');

            return state.token;
        },
        getId(state) {

            if(state.id == null)
                store.dispatch('findId');

            return state.id;
        }
    }
});

export default store;