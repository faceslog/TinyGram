import { createStore } from 'vuex'
import { decodeCredential } from 'vue3-google-login';

// Create a new store instance
const store = createStore({
    state() {
        return {
            token: null
        }
    },
    // ways to change our state (must by synchronous) store.commit("SET_USER", user)
    mutations: {
        SET_TOKEN(state, token) {
            state.token = token;
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
        }
    },
    getters: {
        getUser(state) {
            
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
        }
    }
});

export default store;