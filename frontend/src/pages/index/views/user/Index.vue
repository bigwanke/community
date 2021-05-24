<template>
    <span>用户首页</span>
</template>

<script>
    import { mapMutations } from "vuex"
    
    export default {
        name: "userIndex",
        methods: {
            ...mapMutations("user", ["setNavbarBtnInfo",]),
        },
        mounted() {
            this.setNavbarBtnInfo(null)
        }
    }
</script>

<style>
</style>
