<template>
    <div v-if="discussion" class="discussion-content">
        <div class="content-top">
            <h3>{{discussion.title}}</h3>
        </div>
        <div class="cnotent">
            <div class="user-info">
                <div class="user-left">
                    <router-link :to="{name: 'userDiscussion',params: {'id': discussion.user.id}}">
                       <img :src="discussion.user.profile.avatar" alt="头像"/>
                    </router-link>
                </div>
                <div class="user-name">
                    <a>{{ discussion.user.username }}</a>
                </div>
            </div>
            <div class="content-body">
                <div class="text-report">
                    <p>
                        <Preview :html="discussion.contentHtml"></Preview>
                    </p>
                </div>

                <div class="content-bottom">
                    <em>{{ discussion.createTime | beautifulTime }}</em>

                    <span class="btn btn-item"><a-icon type="eye" />{{ discussion.readCount }}</span>
                    <span class="btn btn-item" :class="{'active': discussion.isUp == true}" @click="voteDiscussion(discussion.id, true)">
                        <a-icon type="like"/> {{ discussion.voteCount }}
                    </span>
                    <span class="btn btn-item" :class="{'active': discussion.isUp == false}" @click="voteDiscussion(discussion.id, false)"><a-icon type="dislike" /></span>
                    <span class="btn btn-item" ><icon-font type="icon-pinglun" />{{ discussion.replyCount}}</span>
                    <span class="btn btn-item">#楼主</span>
                </div>
            </div>
        </div>
        <!-- 评论 -->
        <DiscussionReply :discussionId="discussion.id"></DiscussionReply>
    </div>
</template>

<script>
    import { mapState } from "vuex"

    import Preview from "@/pages/index/components/editor/Preview"

    import http from "@/api/discussion"

    import DiscussionReply from "@/pages/index/components/discussion/Reply"

    import VoteHttp from "@/api/discussionVote"

    import ReplyHttp from "@/api/discussionReply"

    export default {
        components:{
            Preview,
            DiscussionReply,
        },
        data(){
            return{
                discussion: null,
            }
        },
        computed: {
            ...mapState("user", ["user",]),
        },
        watch: {
            $route(newRoute) {
                this.init(newRoute.params.id)
            }
        },
        mounted() {
            this.init(this.$route.params.id)
        },
        methods: {
            async init(discussionId) {
                let res = await http.getDiscussionById(discussionId,this.user.id);
                console.log(discussionId)
                if ( res.code != 200 ){
                    return
                }
                this.discussion = res.data
                console.log(this.discussion)
            },
            async voteDiscussion(discussionId, isUp) {
                const res = await VoteHttp.post({ discussionId, replyId: -1, isUp, })
                if ( res.code == 200 ) {
                    if(res.data == 202){
                        this.discussion.voteCount += isUp ? -1 : 1
                        this.discussion.isUp = null
                    }
                    else{
                        this.discussion.voteCount += isUp ? 1 : -1
                        this.discussion.isUp = isUp
                    }
                    this.$success(res.message)
                    return
                }
                this.$warning(res.message)
            },
            // async submit(parentId) {
            //     if ( !this.content ) {
            //         this.$warning("先输入内容叭. 😅")
            //         return
            //     }
            //     const contentHtml = markdown.render(this.content)
            //     const res = await ReplyHttp.post({contentHtml, contentMarkdown: this.content, parentId, discussionId,})
            //     if ( res.code == 200 ) {
            //         this.$success(res.message)
            //         this.content = null
            //         this.listByDiscussionId(discussionId)
            //         return
            //     }
            //     this.$error(res.message)
            // },
            // async listByDiscussionId(discussionId){
            //     const res = await ReplyHttp.listByDiscussionId({ discussionId, userId: this.user.id, })
            //     if ( res.code == 200 ) {
            //         this.replys = res.data.data
            //         return
            //     }
            // },
        }
    }
</script>

<style lang="less" scoped="scoped">
    .discussion-content {
        box-shadow: 0 4px 8px 6px rgba(7, 17, 27, 6%);
        border-radius: 4px;
        display: flex;
        flex-direction:column;
        width: 1200px;
        background-color: #FFFFFF;

        .content-top {
            width: 100%;
            font-size: 24px;
            line-height: 36px;
            margin-top: 12px;
            font-weight: 600;
            margin-left: 24px;
            border-bottom: solid 1px #f5f5f5;
        }

        .cnotent {
            display: flex;
            border-bottom: solid 1px #f5f5f5;

            .user-info {
                width: 180px;
                display: flex;
                background-color: #FBFCFD;
                border-right: solid 1px #f5f5f5;

                .user-left {
                    padding: 12px;

                    img {
                        width: 48px;
                        height: 48px;
                        border-radius: 50%;
                    }
                }

                .user-name {
                    font-size: 16px;
                    position: relative;
                    top: 14px
                }
            }

            .content-body {
                width: 1020px;
                display: flex;
                flex-direction: column;

                .quote-report {
                    background-color: #F9F9FB;
                    margin: 20px 10px 10px 20px;
                    padding: 6px;
                    border-left: solid 4px #A8A8A8;
                    color: #999AAA;

                    p {
                        color: #555666;
                    }

                }

                .text-report {
                    margin: 0px 10px 0px 20px;
                    color: #222226;
                    p {
                        margin-top: 7px;
                    }
                }

                .content-bottom {
                    display: flex;

                    em {
                        color: #6b6b6b;
                        white-space: nowrap;
                        display: inline-block;
                        font-style: normal;
                        margin: 0px 700px 10px 20px;
                    }
                    .btn {
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
                    .btn-item{
                        margin-right: 9px;
                    }

                    .share-buttn {
                        display: inline-block;
                        width: 92px;
                        font-size: 12px
                    }

                }

            }
        }
    }

</style>
