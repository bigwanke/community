<template>
    <ProblemList :problems="problemList"></ProblemList>
</template>

<script>
    import { mapMutations, mapState } from "vuex"

    import ProblemList from "@/pages/index/components/problem/List"

    import http from "@/api/problem"

    export default {
        name: "userProblemIndex",
        components: { ProblemList, },
        data() {
            return {
                problemList: null,
            }
        },
        computed: {
            ...mapState("user", ["user"]),
        },
        async mounted() {
            this.setNavbarBtnInfo({ text: "提问", routeName: "userProblemPost" })
            const res = await http.listByUserId(1, 5, this.$route.params.id)
            this.problemList = res.data.data
        },
        methods: {
            ...mapMutations("user", ["setNavbarBtnInfo",]),
        },
    }
</script>

<style>
</style>
