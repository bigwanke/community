<template>
    <PostForm
        title-placeholder="请输入标题(40字内)"
        :loading="loading"
        :title="blog.title"
        :content="blog.contentMarkdown"
        :tags="blog.tags"
        @submit="submit"
    ></PostForm>
</template>

<script>
    import { mapMutations } from "vuex"
    import PostForm from "@/pages/index/components/PostForm"
    import blogHttp from "@/api/blog"

    export default {
        name: "userBlogEdit",
        components: { PostForm, },
        data() {
            return {
                loading: false,
                blog: {},
            }
        },
        async mounted() {
            this.setNavbarBtnInfo({ text: "返回", routeName: "userBlogIndex", isBack: true })
            if ( !/^\d+$/.test(this.$route.params.blogid) ) {
                this.$error("请勿修改地址, 即将返回")
                setTimeout(() => {
                    this.$router.go(-1)
                }, 1500)
                return
            }
            const res = await blogHttp.userBlogById(this.$route.params.blogid)
            if ( res.code == 200 ) {
                this.blog = res.data
            }
        },
        methods: {
            ...mapMutations("user", ["setNavbarBtnInfo",]),
            async submit(form) {
                Object.assign(this.blog, form)

                this.loading = true
                const res = await blogHttp.updateBlogById(this.blog)
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
        }
    }

</script>

<style>
</style>
