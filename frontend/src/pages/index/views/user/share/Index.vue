<template>
    <div class="share-conten">
        <ShareList :shares="shares" class="list"></ShareList>
        <div class="count-card" v-if="!(shares && !shares.length)">
            <span>
                <div>{{this.share.shareVoteCount}}</div>
                <div>点赞</div>
            </span>
            <span>
                <div>{{this.share.shareReadCount}}</div>
                <div>阅读量</div>
            </span>
            <span>
                <div>{{this.share.shareReplyCount}}</div>
                <div>回复</div>
            </span>
        </div>
    </div>
</template>

<script>
    import { mapMutations, mapState } from "vuex"
    import ShareList from "@/pages/index/components/share/List"
    import http from "@/api/share"

    export default {
        name:"userSahreIndex",
        components:{
            ShareList,
        },
        data(){
            return{
                shares: null,
                share:{
                    shareVoteCount: 0,
                    shareReplyCount: 0,
                    shareReadCount: 0,
                },
            }
        },
        computed: {
            ...mapState("user", ["user"]),
        },
        async mounted() {
            this.setNavbarBtnInfo({ text: "发圈子", routeName: "userSharePost" });
            const res = await http.userShares(this.$route.params.id);
            console.log(res);
            this.shares = res.data.data;
            let voteCount = 0;
            let readCount = 0;
            let replyCount = 0;
            for(var i = 0; i < this.shares.length;i++){
                // alert(this.shares[i].voteCount);
                voteCount += this.shares[i].voteCount;
                readCount += this.shares[i].readCount;
                replyCount += this.shares[i].replyCount;
                console.log(voteCount);
            }

            console.log(voteCount);
            this.$set(this.share,"shareVoteCount",voteCount);
            this.$set(this.share,"shareReadCount",readCount);
            this.$set(this.share,"shareReplyCount",replyCount);
            console.log(this.shareVoteCount);
            console.log(res.data);
        },
        methods: {
            ...mapMutations("user", ["setNavbarBtnInfo",]),
        },
    }
</script>

<style lang="less" scoped="scoped">
    .share-conten{
        display: flex;

        .list {
            padding-left: -50px;
        }
        .count-card{
            width: 200px;
            height: 100px;
            display: flex;
            box-shadow: 0 4px 8px 6px rgba(7, 17, 27, 6%);
            flex-wrap: wrap;
            border-radius: 10px;
            justify-content: space-evenly;

            span{
                padding-top: 25px;
                text-align: center;
            }
        }
    }

</style>
