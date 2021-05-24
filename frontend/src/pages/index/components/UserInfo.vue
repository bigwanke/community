<template>
    <div class="user-info-block fixed-box">
        <div class="user-avatar">
            <img :src="user.profile.avatar" alt="" class="user-avatar-image">
        </div>
        <div class="user-profile">
            <router-link :to="{ name: 'user', params: { id: user.id } }">
                <div class="username">{{ user.username }}</div>
            </router-link>
            <div v-if="user.profile.introduction" class="introduction">{{ user.profile.introduction }}</div>
            <div v-else class="introduction">该用户很懒</div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "userInfo",
        props: ["user"],
    }
</script>

<style lang="less" scoped="scoped">
    .user-info-block {
        .user-avatar {
            .user-avatar-image {
                width: 64px;
                height: 64px;
                border-radius: 50%;
            }
        }

        .user-profile {
            margin-left: 24px;
        }
    }
</style>
