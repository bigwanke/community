<template>
    <ProblemList class="content-wrapper" :problems="problems"></ProblemList>
</template>

<script>
    import ProblemList from "@/pages/index/components/problem/List"

    import http from "@/api/problem"

    export default {
        name: "problemIndex",
        components: { ProblemList, },
        data() {
            return {
                problems: null,
            }
        },
        async mounted() {
            const res = await http.list()
            if ( res.code == 200 ) this.problems = res.data.data
        },
    }
</script>

<style>
</style>
