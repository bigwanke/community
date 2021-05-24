<template>
    <div v-if="share" class="share-item">
        <div class="li-left">
            <!-- 作者头像 -->
            <router-link :to="{name: 'userShare',params: {'id': share.user.id}}">
                <img :src="share.user.profile.avatar" alt="头像" />
            </router-link>
        </div>

        <div class="li-body">
            <div class="author-name">
                 <!-- 作者名 -->
                <a href="#">{{share.user.username}}</a>
            </div>
            <router-link
                v-if="user.id && user.id == share.user.id"
                class="edit" title="编辑" :to="{name: 'userShareEdit', params: {'id': user.id, 'shareId': share.id}}"
            >
                <a-icon type="edit" />
            </router-link>
            <div class="top-left"></div>
            <!-- 圈子发布日期 -->
            <div class="create-date">{{share.createTime | beautifulTime}}</div>

            <!-- 文章简略 -->
            <div class="li-content">
                <Preview :html="share.contentHtml"></Preview>
            </div>
            <div class="li-bottom">
                <span class="share-buttn"><a-icon type="eye" />{{share.readCount}}</span>
                <span class="share-buttn"><a-icon type="message"/> {{share.replyCount}}</span>
                <span class="share-buttn" @click="voteShare(share.id,true)" :class="{'active': share.isUp == true}"><a-icon type="like" />{{share.voteCount}}</span>
                <span class="share-buttn" @click="voteShare(share.id,false)" :class="{'active': share.isUp == false}"><a-icon type="dislike" /></span>
            </div>
            <!-- 评论区-->
           <ShareReply class="content-wrapper top-interval content" :shareId="share.id"></ShareReply>

        </div>
    </div>
</template>

<script>
    import {mapState} from "vuex"

    import shareHttp from "@/api/share"

    import Preview from "@/pages/index/components/editor/Preview"

    import ShareReply from "@/pages/index/components/share/Reply"

    import voteHttp from "@/api/shareVote"
    export default{
        name:"shareContent",
        components:{
            Preview,
            ShareReply,
        },
		data() {
			return{
				share: null,
			}
		},
        computed: {
            ...mapState("user", ["user"])
        },
        watch: {
            $route(newRoute) {
                this.init(newRoute.params.id)
            }
        },
        mounted(){
            this.init(this.$route.params.id);
        },
        methods:{
            async init(id){
                console.log(this.user)
                let res = await shareHttp.getShareById(id,this.user.id);
                if(res.code != 200){
                    return;
                }
                this.share = res.data;
            },
            // async voteDiscussion(discussionId, isUp) {
            //     const res = await VoteHttp.post({ discussionId, replyId: -1, isUp, })
            //     if ( res.code == 200 ) {
            //         if(res.data == 202){
            //             this.discussion.voteCount += isUp ? -1 : 1
            //             this.discussion.isUp = null
            //         }
            //         else{
            //             this.discussion.voteCount += isUp ? 1 : -1
            //             this.discussion.isUp = isUp
            //         }
            //         this.$success(res.message)
            //         return
            //     }
            //     this.$warning(res.message)
            // },
            async voteShare(shareId, isUp) {
                const res = await voteHttp.post({ shareId, replyId: -1, isUp, })
                if ( res.code == 200 ) {
                    console.log(res.data)
                    if(res.data == 202){
                        this.share.voteCount += isUp ? -1 : 1
                        this.share.isUp = null
                    }
                    else{
                        this.share.voteCount += isUp ? 1 : -1
                        this.share.isUp = isUp
                    }
                    this.$success(res.message)
                    return
                }
                this.$warning(res.message)
            }
        }
    }
</script>

<style lang="less" scoped="scoped">
    .share-item {
        box-shadow: 0 4px 8px 6px rgba(7, 17, 27, 6%);
        border-radius: 4px;
        display: flex;
        width: 700px;
        background-color: #FFFFFF;
        margin-left: 250px;

        .li-left {

            img {
                width: 48px;
                height: 48px;
                border-radius: 50%;
                position: relative;
                left: 24px;
                top: 24px;
            }
        }

        .li-body {
            display: flex;
            -webkit-flex-direction: column;
            flex-direction: column;
            position: relative;
            left: 50px;
            margin-top: 24px;

            .create-date {
                font-size: 12px;
                color: #99a2aa;
            }
            .author-name {
                font-size: 16px;
                a {
                    color: black;
                }

            }
            .edit{
                display: inline-block;
                position: absolute;
                right: 30px;
                top: 0px;
                font-size: 24px;

                &:hover {
                    cursor: pointer;
                }
            }

            .li-content {
                position: relative;
                margin-top: 14px;
                width: 530px;
            }

            .li-bottom {
                display: flex;
                flex-wrap: wrap;
                justify-content: space-evenly;
                .share-buttn {
                    display: inline-block;
                    line-height: 48px;
                    font-size: 16px;
                    background-color: transparent;
                    border: none;
                    outline: none;
                    cursor: pointer;
                    color: #757575;
                    transition: color .3s ease;

                    &:hover {
                        color: #00A1D6;
                    }

                    &.active {
                        color: #00A1D6;
                    }
                }
            }
        }
    }

</style>
