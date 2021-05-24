<template>
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
        <ul class="reply-list">
            <li v-for="reply in replys" class="reply-item fixed-box">
                <router-link :to="{name: 'userShare',params: {'id': reply.user.id}}">
                    <img :src="reply.user.profile.avatar" alt="" class="user-avatar-image">
                </router-link>
                <div class="reply-content">
                    <div class="username">
                        {{ reply.user.username }}
                    </div>
                    <p>
                        <Preview :html="reply.contentHtml"></Preview>
                    </p>
                    <div class="info">
                        <span>{{ reply.createTime | dateFormat }}</span>
                        <span class="btn" :class="{'active': reply.isUp == true}" @click="voteReply(reply.id, true)">
                            <a-icon type="like" />
                            {{ reply.voteCount }}
                        </span>
                        <span class="btn" :class="{'active': reply.isUp == false}" @click="voteReply(reply.id, false)">
                            <a-icon type="dislike" />
                        </span>
                        <span v-if="user.id && reply.user.id == user.id" class="btn" @click="deleteById(reply.id)">删除</span>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
    import { mapState } from "vuex"

    import shareReplyHttp from "@/api/shareReply"

    import shareVoteHttp from "@/api/shareVote"

    import markdown from "@/config/markdown-it"

    import Preview from "@/pages/index/components/editor/Preview"

    export default {
        name: "shareReply",
        props: [ "shareId" ],
        components: { Preview, },
        data() {
            return {
                content: null,
                replys: [],
            }
        },
        computed: {
            ...mapState("user", ["user",],),
        },
        mounted() {

            this.listByShareId(this.shareId)
        },
        methods: {
            async submit(parentId) {
                if ( !this.content ) {
                    this.$warning("先输入内容吧")
                    return
                }
                console.log(this.content)
                const contentHtml = markdown.render(this.content)
                console.log(contentHtml)
                console.log(parentId,this.shareId)
                const res = await shareReplyHttp.post({contentHtml: contentHtml, contentMarkdown: this.content, parentId, shareId: this.shareId,})
                if ( res.code == 200 ) {
                    console.log(res.message)
                    this.$success(res.message)
                    // this.$success("评论发布成功")
                    this.content = null
                    this.listByShareId(this.shareId)
                    return
                }
                this.$error(res.message)
            },
            async listByShareId(shareId) {
                const res = await shareReplyHttp.listByShareId({ shareId, userId: this.user.id })
                if ( res.code == 200 ) {
                    this.replys = res.data.data
                    this.replys.forEach(e => {
                       console.log(e.voteCount)
                    })
                    return
                }
            },
            async voteReply(replyId, isUp) {
                const res = await shareVoteHttp.post({ shareId: -1, replyId, isUp, })
                if ( res.code == 200 ) {
                    console.log(res)
                    this.$success(res.message)
                    this.replys.forEach(e => {
                        if ( e.id == replyId ) {
                            if(res.data == 202){
                                e.voteCount += isUp ? -1 : 1
                                e.isUp = null
                            }
                            else{
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
                const res = await shareReplyHttp.deleteById(id)
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
    .reply{
        width: 701px;
        margin-left: -98px;
        border-radius: none !important;
        border-top: 1px solid #e5e9ef;
        border-radius: none;
        .reply-title {
            font-size: 18px;
        }

        .reply-input-text {
            width: 100%;
            height: 65px;
        }

        .reply-send {
            margin-top: 14px;
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

        .reply-content {
            width: calc(100% - 66px);
        }

        .reply-item {
            border-top: 1px solid #e5e9ef;
            padding: 15px 0;

            &:first-child {
                margin-top: 20px;
            }

            &:last-child {
                padding-bottom: 0;
            }
        }

        .info {
            color: #99a2aa;
            line-height: 26px;
            font-size: 12px;

            span {
                margin-right: 12px;
            }

            i {
                font-size: 14px;
                margin-right: 5px;
            }
        }

        .btn {
            cursor: pointer;
            transition: color .3s ease;

            &:hover {
                color: #1dacdb;
            }

            &.active {
                color: #1dacdb;
            }
        }
    }



</style>
