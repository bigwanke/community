<template>
    <div class="wrapper top-interval">
        <div v-show="$route.name == 'blog'" class="blog-problem-header">
            <h1 class="title">{{ config.title }}技术分享</h1>
            <a-button 
                class="btn" 
                type="primary" 
                size="large"
                :disabled="!user.id"
                :title="user.id ? '写分享' : '先登录吧'"
                @click="$router.push({ name: 'userBlogPost', params: {id: user.id} })"
            >
                <a-icon type="edit" />
                写分享
            </a-button>
        </div>
        <router-view></router-view>
    </div>
</template>

<script>
import { mapState } from "vuex"
import config from "@/config"

export default {
    name: "BlogHome",
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
