<template>
    <div class="content-wrapper">
        <div class="index-content">
            <ShareList :shares="shares"></ShareList>
            <UserCard :shares="shares"></UserCard>
        </div>
        <div class="plus">
            <a
                :disabled="!user.id"
                :title="user.id ? '写圈子' : '先登录吧'"
                @click="$router.push({ name: 'userSharePost', params: {id: user.id} })">
                <a-icon type="plus-circle" title="写圈子" class="icon"/>
            </a>
        </div>
    </div>
</template>

<script>
    import {mapState} from "vuex"

    import ShareList from "@/pages/index/components/share/List"

    import shareHttp from "@/api/share"

    import UserCard from "@/pages/index/components/share/Card"

    export default{
        name:"shareIndex",
        components:{ ShareList,UserCard ,},
        data(){
            return{
                shares : null,
            }
        },
        computed: {
            ...mapState("user", ["user"]),
        },
        async mounted() {
            const res = await shareHttp.getShareList();
            this.shares = res.data.data;
        },
    }
</script>

<style lang="less" scoped="scoped">
    .index-content{
        display: flex;
    }
    .plus{
        width: 40px;
        height: 40px;
        font-size: 40px;
        position: fixed;
        right: 50px;
        top: 600px;
        display: -webkit-box;

            .icon{
                margin-left: 5px;
                background-color: white;
                border-radius: 50%;
            }
    }
</style>
