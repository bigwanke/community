<template>
    <BlogList class="content-wrapper" :blogs="blogs"></BlogList>
</template>

<script>
    import BlogList from "@/pages/index/components/blog/List"

    import http from "@/api/blog"

    export default {
        name: "blogIndex",
        components:{ BlogList, },
        data() {
            return {
                blogs: null,
            }
        },
        async mounted() {
            const res = await http.getBlogList();
            this.blogs = res.data.data;
        }
    }
</script>

<style>
</style>
