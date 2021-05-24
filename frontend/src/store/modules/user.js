import http from "@/api/user"

const state = () => ({
    user: {},
    navbarBtnInfo: null,
})

const mutations = {
    setUser(state, user) {
        state.user = user
    },
    
    initUser(state) {
        state.user = {}
    },

    setNavbarBtnInfo(state, info) {
        if ( !info ) {
            state.navbarBtnInfo = null
            return
        }

        const { text, routeName, isBack } = info
        if ( text && routeName )
            if ( isBack )
                state.navbarBtnInfo = { text, routeName, isBack }
            else
                state.navbarBtnInfo = { text, routeName, isBack: false }
    },
}

const actions = {
    async getUser({ commit }) {
        const data = await http.getUser()
        if ( data.code == 200 )
            commit("setUser", data.data)
	},
}

export default {
    namespaced: true,
    state,
    mutations,
	actions,
}
