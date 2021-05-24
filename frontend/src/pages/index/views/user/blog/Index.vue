<template>
    <BlogList :blogs="blogs"></BlogList>
</template>

<script>
    import { mapMutations, mapState } from "vuex"

    import BlogList from "@/pages/index/components/blog/List"

    import http from "@/api/blog"

    export default {
        name: "userBlogIndex",
        components:{ BlogList, },
        data() {
            return {
                blogs: null,
            }
        },
        computed: {
            ...mapState("user", ["user"]),
        },
        async mounted() {
            this.setNavbarBtnInfo({ text: "写分享", routeName: "userBlogPost" })
            const res = await http.userBlogs(this.$route.params.id);
            this.blogs = res.data.data;
        },
        methods: {
            ...mapMutations("user", ["setNavbarBtnInfo",]),
        },
    }
</script>
