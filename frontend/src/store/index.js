import Vue from 'vue'
import Vuex from 'vuex'

import user from "./modules/user"

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        modalStatus: {
            who: "login",
            visible: false,
        }
    },
    mutations: {
        changeModalStatus(state, { who, visible }) {
            if ( who !== undefined ) state.modalStatus.who = who
            if ( visible !== undefined ) state.modalStatus.visible = visible
        }
    },
    actions: {},
    modules: {
        user,
    },
})
