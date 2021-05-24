<template>
    <div class="user-navbar">
        <router-link class="user-navbar-item" :to="{ name: 'user' }">主页</router-link>
        <router-link class="user-navbar-item" :to="{ name: 'userBlog' }">分享</router-link>
        <router-link class="user-navbar-item" :to="{ name: 'userProblem' }">问答</router-link>
        <router-link class="user-navbar-item" :to="{ name: 'userShare' }">圈子</router-link>
        <router-link class="user-navbar-item" :to="{ name: 'userDiscussion' }">讨论</router-link>
        <a-button
            v-if="navbarBtnInfo && user.id && user.id == $route.params.id"
            id="user-navbar-right"
            type="primary"
            @click="toRoute"
        >
            {{ navbarBtnInfo.text }}
        </a-button>
    </div>
</template>

<script>
    import { mapState } from "vuex"

    export default {
        name: "userNavbar",
        computed: {
            ...mapState("user", ["navbarBtnInfo", "user",]),
        },
        methods: {
            toRoute() {
                // 后退操作
                if ( this.navbarBtnInfo.isBack ) this.$router.go(-1)
                else this.$router.push({ name: this.navbarBtnInfo.routeName })
            }
        },
    }
</script>

<style lang="less">
    .user-navbar {
        height: 66px;
        line-height: 66px;
        background-color: #fff;
        padding: 0 20px;
        display: flex;
        border-radius: 0 0 4px 4px;
        display: flex;
        align-items: center;
        position: relative;

        .user-navbar-item {
            display: block;
            padding: 0 20px;
            border-bottom: 3px solid transparent;
            color: rgba(0, 0, 0, 0.65);
            transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);

            &:hover {
                color: #1890ff;
                border-bottom: 3px solid #1890ff;
            }
        }

        .router-link-active {
            color: #1890ff;
            border-bottom: 3px solid #1890ff;
        }

        #user-navbar-right {
            position: absolute;
            right: 40px;
        }
    }
</style>
