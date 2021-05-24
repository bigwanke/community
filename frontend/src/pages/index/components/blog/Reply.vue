<template>
    <div>
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
            <li v-for="reply in replys" :key="reply.id" class="reply-item fixed-box">
                <img :src="reply.user.profile.avatar" alt="" class="user-avatar-image">
                <div class="reply-content">
                    <div class="username">
                        {{ reply.user.username }}
                    </div>
                    <Preview :html="reply.contentHtml"></Preview>
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

    import blogReplyHttp from "@/api/blogReply"
    import blogVoteHttp from "@/api/blogVote"

    import markdown from "@/config/markdown-it"
    import Preview from "@/pages/index/components/editor/Preview"

    export default {
        name: "blogReply",
        props: [ "blogId" ],
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
            this.listByBlogId(this.blogId)
        },
        methods: {
            async submit(parentId) {
                if ( !this.content ) {
                    this.$warning("先输入内容吧. 😅")
                    return
                }
                const contentHtml = markdown.render(this.content)
                const res = await blogReplyHttp.post({ contentHtml, contentMarkdown: this.content, parentId, blogId: this.blogId,})
                if ( res.code == 200 ) {
                    this.$success(res.message)
                    this.content = null
                    this.listByBlogId(this.blogId)
                    return
                }
                this.$error(res.message)
            },
            async listByBlogId(blogId) {
                const res = await blogReplyHttp.listByBlogId({ blogId, userId: this.user.id, })
                if ( res.code == 200 ) {
                    this.replys = res.data.data
                    return 
                }
            },
            async voteReply(replyId, isUp) {
                const res = await blogVoteHttp.post({ blogId: -1, replyId, isUp, })
                if ( res.code == 200 ) {
                    this.$success(res.message)
                    this.replys.forEach(e => {
                        if ( e.id == replyId ) {
                            if ( res.data == 202 ) {
                                e.voteCount += isUp ? -1 : 1
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
                const res = await blogReplyHttp.deleteById(id)
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

</style>
