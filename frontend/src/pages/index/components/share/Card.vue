<template>
    <div v-if="user.id != null && !(shares && !shares.length)" class="user-card">
        <div class="card-top">

        </div>

        <img :src="user.profile.avatar" class="user-head" alt="头像"/>
        <div class="user-profile">
            <a class="author-name">
                {{user.username}}
            </a>
            <span class="introduction" v-if="user.profile.introduction == null">该用户很懒</span>
            <span v-if="user.profile.introduction != null">{{user.profile.introduction}}</span>
        </div>
    </div>
</template>

<script>
    import {mapState} from "vuex"

    export default{
        name: "userCard",
        props: {
            shares : null,
        },
        computed: {
            ...mapState("user", ["user"])
        },
    }
</script>

<style lang="less" scoped="scoped">
    .user-card {
        width: 240px;
        height: 200px;
        box-shadow: 0 4px 8px 6px rgba(7, 17, 27, 6%);
        border-radius: 4px;
        background-color: #FFFFFF;

        .card-top {
            height: 65px;
            width: 240px;
            background-image: url(../../../../assets/user-header.webp);
            border-radius: 4px;
        }
        .user-profile{
            display: flex;
            flex-direction: column;
            margin-top: -40px;
            .author-name {
                margin-left: 70px;
                color: black;
            }
            .introduction{
                margin-left: 70px;
            }
        }

        img {
            position: relative;
            margin-top: -30px;
            margin-left: 10px;
            height: 48px;
            width: 48px;
            border-radius: 50%;
        }

    }
</style>
