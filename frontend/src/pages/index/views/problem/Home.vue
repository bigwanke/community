<template>
    <div class="wrapper top-interval">
        <div v-show="$route.name == 'problem'" class="blog-problem-header">
            <h1 class="title">{{ config.title }}技术问答</h1>
            <a-button 
                class="btn" 
                type="primary" 
                size="large"
                :disabled="!user.id"
                :title="user.id ? '提问' : '先登录吧'"
                @click="$router.push({ name: 'userProblemPost', params: {id: user.id} })"
            >
                <a-icon type="edit" />
                提问
            </a-button>
        </div>
        <router-view></router-view>
    </div>
</template>

<script>
import { mapState } from "vuex"
import config from "@/config"

export default {
    name: "ProblemHome",
    computed: {
        ...mapState("user", ["user",])
    },
    data() {
        return {
            config
        }
    }
}
</script>

<style>
</style>
