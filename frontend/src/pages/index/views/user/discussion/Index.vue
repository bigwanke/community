<template>
    <DiscussionTable :discussions="discussions"></DiscussionTable>
</template>

<script>
    import { mapMutations, mapState } from "vuex"
    import DiscussionTable from "@/pages/index/components/discussion/Table"
    import http from "@/api/discussion"

    export default {
        name:"userDiscussionIndex",
        components:{
            DiscussionTable,
        },
        data(){
            return{
                discussions: null,
            }
        },
        computed: {
            ...mapState("user", ["user"]),
        },
        async mounted() {
            this.setNavbarBtnInfo({ text: "写讨论", routeName: "userDiscussionPost" });
            const res = await http.userDiscussions(1, 10, this.$route.params.id);
            this.discussions = res.data.data;
        },
        methods: {
            ...mapMutations("user", ["setNavbarBtnInfo",]),
        },
    }
</script>

<style>
</style>
