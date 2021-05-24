<template>
    <header class="user-header">
        <div v-if="user.id" class="user-info">
            <div class="user-avatar">
                <img :src="user.profile.avatar" alt="" id="user-avatar">
            </div>
            <div class="user-basic">
                <span id="user-username">{{ user.username }}</span>
                <span v-if="user.profile.introduction" id="user-intro">{{ user.profile.introduction }}</span>
                <span v-else>编辑签名</span>
            </div>
        </div>
    </header>
</template>

<script>
    export default {
        name: "userHeader",
        props: ["user",],
    }
</script>

<style lang="less" scoped="scoped">
    .user-header {
        height: 200px;
        background-image: url(https://api.zhoupb.com/image/bing/);
        background-position: 0 50%;
        padding-top: 112px;
    }

    .user-info {
        margin-left: 20px;
        display: flex;

        #user-avatar {
            width: 64px;
            height: 64px;
            border: 2px solid hsla(0,0%,100%,.4);
            border-radius: 50%;
        }

        .user-basic {
            margin: 10px 0 0 20px;
            display: flex;
            flex-direction: column;
            position: relative;

            #user-username {
                font-size: 18px;
                font-weight: 700;
                color: #fff;
            }

            #user-intro {
                position: absolute;
                bottom: 2px;
                color: #d6dee4;
            }
        }
    }

</style>
