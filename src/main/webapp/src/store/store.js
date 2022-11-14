import { createStore } from 'vuex'
import { decodeCredential } from 'vue3-google-login';

// Create a new store instance
const store = createStore({
    state() {
        return {
            token: null,
            user: null // { image: "", key: "ahJnfnRpbnlpbnN0YS0zNjYzMTRyHwsSBFVzZXIiFTExNzU0MDQzMzk4NjU5MTM4ODQ4Nww", name: "Kyle Crane" }
        }
    },
    // ways to change our state (must by synchronous) store.commit("SET_USER", user)
    mutations: {
        SET_TOKEN(state, token) {
            state.token = token;
        },
        SET_USER(state, user) {
            state.user = user;
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
        // User to avoid making multiple get to the api
        setUser(context, user) {
            sessionStorage.setItem("user", JSON.stringify({ name: user.name, image: user.image, key: user.key }));
            context.commit('SET_USER', user);
        },
        findUser(context) {
            let user = sessionStorage.getItem("user");
            context.commit('SET_USER', JSON.parse(user));
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
        getUserId(state) {

            if(state.user == null)
                store.dispatch('findUser');

            return state.user.key;
        },
        getUser(state) {

            if(state.user == null)
                store.dispatch('findUser');

            return state.user;
        }
    }
});

export default store;