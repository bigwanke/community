<template>
    <div>
        <div v-for="reply in replys" class="reply-content">
            <div class="user-info">
                <div class="user-left">
                    <router-link :to="{name: 'userDiscussion',params: {'id': reply.user.id}}">
                        <img :src="reply.user.profile.avatar" alt="头像"/>
                    </router-link>
                </div>
                <div class="user-name">
                    <a>{{ reply.user.username }}</a>
                </div>
            </div>
            <div class="content-body">
                 <div class="quote-report" v-if="reply.parentId != -1">
                     <span>
                         引用 2楼 某某某 的回复:
                         <p>
                             <Preview :html="reply.contentHtml"></Preview>
                             现在jdk同一种类、方法、字段反射一次以后，已经默认帮你缓存了
                         </p>
                     </span>
                 </div>
                 <div class="text-report">
                     <p>
                         <Preview :html="reply.contentHtml"></Preview>
                     </p>
                 </div>

                 <div class="content-bottom">
                     <em>{{reply.createTime | beautifulTime}}</em>
                     <div class="btns">
                         <span class="btn" :class="{'active': reply.isUp == true}" @click="voteReply(reply.id, true)">
                             <a-icon type="like"/> {{ reply.voteCount }}
                         </span>
                         <span class="btn btn-item" :class="{'active': reply.isUp == false}" @click="voteReply(reply.id, false)"><a-icon type="dislike" /></span>
                         <span v-if="user.id && reply.user.id == user.id" class="btn btn-item" @click="deleteById(reply.id)">删除</span>
                     </div>
                 </div>
             </div>
        </div>
        <div class="reply">
            <div class="reply-title">
                {{ replys.length }}
                条回复
            </div>
            <div v-if="user.id" class="reply-send">
                <div class="fixed-box">
                    <div class="user-avatar">
                        <img :src="user.profile.avatar" alt="" class="user-avatar-image">
                    </div>
                    <a-textarea
                        v-model="content"
                        placeholder="输入一条友善的评论叭"
                        class="reply-input-text"
                    />
                </div>
                <a-button type="primary" class="reply-submit-btn" @click="submit(-1)">
                    发布评论
                </a-button>
            </div>
        </div>
    </div>
</template>

<script>
    import Preview from "@/pages/index/components/editor/Preview"

    import ReplyHttp from "@/api/discussionReply"

    import VoteHttp from "@/api/discussionVote"

    import markdown from "@/config/markdown-it"

    import {mapState} from "vuex"
    export default{
        name: "discussionReply",
        props:[ "discussionId" ],
        components: { Preview, },
        data(){
            return{
                content: null,
                replys: [],
            }
        },
        computed: {
            ...mapState("user", ["user",],),
        },
        mounted() {

            this.listByDiscussionId(this.discussionId)
        },
        methods: {
            async submit(parentId) {
                if ( !this.content ) {
                    this.$warning("先输入内容叭. 😅")
                    return
                }
                console.log(this.content)
                const contentHtml = markdown.render(this.content)
                console.log(contentHtml)
                console.log(parentId,this.discussionId)
                const res = await ReplyHttp.post({contentHtml: contentHtml, contentMarkdown: this.content, parentId, discussionId: this.discussionId,})
                if ( res.code == 200 ) {
                    console.log(res.message)
                    this.$success(res.message)
                    this.content = null
                    this.listByDiscussionId(this.discussionId)
                    return
                }
                this.$error(res.message)
            },
            async listByDiscussionId(discussionId) {
                const res = await ReplyHttp.listByDiscussionId({ discussionId, userId: this.user.id })
                if ( res.code == 200 ) {
                    this.replys = res.data.data
                    this.replys.forEach(e => {
                    })
                    return
                }
            },
            async voteReply(replyId, isUp) {
                const res = await VoteHttp.post({ discussionId: -1, replyId, isUp, })
                if ( res.code == 200 ) {
                    console.log(res)
                    this.$success(res.message)
                    this.replys.forEach(e => {
                        if ( e.id == replyId ) {
                            if(res.data == 202){
                                e.voteCount += isUp ? -1 : 1
                                console.log(e.voteCount)
                                e.isUp = null
                            }
                            else {
                                e.voteCount += isUp ? 1 : -1
                                e.isUp = isUp
                            }
                        }

                    })
                    return
                }
                this.$error(res.message)
            },
            async deleteById(id) {
                const res = await ReplyHttp.deleteById(id)
                if ( res.code == 200 ) {
                    this.$success(res.message)
                    const tmp = this.replys.filter(e => e.id != id)
                    this.replys = tmp
                    return
                }
                this.$warning(res.message)
            }
        }
    }
</script>

<style lang="less" scoped="scoped">
    .reply-content{
        display: flex;
    }
    .user-info {
        width: 180px;
        display: flex;
        background-color: #FBFCFD;
        border-right: solid 1px #f5f5f5;
        border-bottom: solid 1px #f5f5f5;

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
        border-bottom: solid 1px #f5f5f5;

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
                margin: 0px 0px 10px 20px;
            }
            .btns{
                   position: relative;
                   margin-left: 700px;
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
            }
        }
    }
    .reply{
        margin-left: 20px;
        margin-right: 10px;
        .reply-title {
            font-size: 18px;
        }

        .reply-input-text {
            width: 100%;
            height: 65px;

        }

        .reply-send {
            margin-top: 14px;
            margin-top: 20px;
            position: relative;
            padding-bottom: 40px;
        }

        .reply-submit-btn {
            position: absolute;
            right: 0;
            bottom: 0;
        }
        .user-avatar-image {
            width: 48px;
            height: 48px;
            border-radius: 50%;
            margin-right: 18px;
        }

        .username {
            color: #6d757a;
        }
    }

</style>
