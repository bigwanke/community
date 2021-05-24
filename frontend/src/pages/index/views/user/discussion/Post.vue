<template>
    <DiscussionPostFrom title-placeholder="请输入标题(40字内)" :loading="loading" @submit="submit"></DiscussionPostFrom>
</template>

<script>
    import DiscussionPostFrom from "@/pages/index/components/DiscussionPostFrom"

    import http from "@/api/discussion"

    import {mapMutations} from "vuex"
    export default{
        name:"userDiscussionPost",
        components:{
            DiscussionPostFrom,
        },
        data(){
            return{
                loading: false,
            }
        },
        mounted() {
            this.setNavbarBtnInfo({ text: "返回", routeName: "userShareIndex", isBack: true })
        },
        methods: {
            ...mapMutations("user", ["setNavbarBtnInfo",]),
            async submit(form) {
                this.loading = true
                const res = await http.post(form)
                this.loading = false

                if ( res.code != 200 ) {
                    this.$error(res.message)
                    return
                }

                this.$success(res.message)

                setTimeout(() => {
                    this.$router.go(-1)
                }, 1000)
            },
        },
    }
</script>

<style>

</style>
