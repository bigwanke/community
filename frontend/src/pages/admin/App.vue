<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>

<script>
    import http from "@/api/user"
    import "@/style/common.less"
    import {mapMutations} from "vuex"
export default {
    async mounted() {
        const data = await http.getUser();
        if(data.code == 200)
        this.setUser(data.data);
    },
    methods:{
        ...mapMutations("user",["setUser"]),
    }
}
</script>


