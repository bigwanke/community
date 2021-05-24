<template>
    <PostForm title-placeholder="请输入标题(40字内)" :loading="loading" @submit="submit"></PostForm>
</template>

<script>
    import { mapMutations } from "vuex"

    import PostForm from "@/pages/index/components/PostForm"

    import http from "@/api/blog"

    export default {
        name: "userBlogPost",
        components: { PostForm, },
        data() {
            return {
                loading: false,
            }
        },
        mounted() {
            this.setNavbarBtnInfo({ text: "返回", routeName: "userBlogIndex", isBack: true })
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
