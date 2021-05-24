<template>
    <SharePostFrom :loading="loading" @submit="submit"></SharePostFrom>
</template>

<script>

    import { mapMutations } from "vuex"

    import SharePostFrom from "@/pages/index/components/SharePostFrom"

    import http from "@/api/share"

    export default{
        name:"userSharePost",
        components:{
            SharePostFrom,
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
