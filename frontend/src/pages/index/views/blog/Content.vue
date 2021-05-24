<template>
    <div v-if="blog" class="fixed-box">
        <div style="height: 100%;">
            <div class="content-wrapper content">
                <div class="header-wrapper">
                    <h1 class="title">{{ blog.title }}</h1>
                    <div class="tags">
                        <a-tag v-for="tag in blog.tags" :key="tag" color="blue">
                            {{ tag }}
                        </a-tag>
                    </div>
                    <div class="blog-meta">
                        <span><a-icon type="eye" />{{ blog.readCount }}</span>
                        <span><a-icon type="like" />{{ blog.voteCount }}</span>
                        <span><icon-font type="icon-pinglun" />{{ blog.replyCount }}</span>
                        <span><icon-font type="icon-naozhong_huaban1" />{{ blog.createTime | beautifulTime }}</span>
                    </div>
                    <router-link
                        v-if="user.id && user.id == blog.user.id"
                        class="edit" title="编辑" :to="{name: 'userBlogEdit', params: {'id': user.id, 'blogid': blog.id}}"
                    >
                        <a-icon type="edit" />
                    </router-link>
                </div>
                <a-divider />
                <Preview :html="blog.contentHtml"></Preview>
                <div class="btns">
                    <button class="btn" :class="{'active': blog.isUp == true}" @click="voteBlog(blog.id, true)">
                        <a-icon type="like" theme="filled" />
                        <span class="number">
                            {{ blog.voteCount }}
                        </span>
                    </button>
                    <button class="btn" :class="{'active': blog.isUp == false}" @click="voteBlog(blog.id, false)">
                        <a-icon type="dislike" theme="filled" />
                    </button>
                </div>
             </div>
            <BlogReply class="content-wrapper top-interval content" :blogId="blog.id"></BlogReply>
        </div>
        <div class="right">
            <UserInfo class="content-wrapper" :user="blog.user"></UserInfo>
            <div class="blog-list-block content-wrapper">
                <div class="block-title">他的文章</div>
                <ul class="blog-list">
                    <li v-for="blog in blogs" :key="blog.id" class="blog-item">
                        <router-link :to="{ name: 'blogContent', params:{id: blog.id} }">
                            <div class="blog-info">
                                <div class="blog-title">{{ blog.title }}</div>
                                <div class="blog-meta">
                                    <span><icon-font type="icon-naozhong_huaban1" />{{ blog.createTime | beautifulTime }}</span>
                                    <span><a-icon type="eye" />{{ blog.readCount }}</span>
                                </div>
                            </div>
                            <div class="blog-image" :style="{backgroundImage: 'url('+blog.coverImage+')'}"></div>
                        </router-link>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
    import { mapState } from "vuex"

    import http from "@/api/blog"
    import blogVoteHttp from "@/api/blogVote"

    import Preview from "@/pages/index/components/editor/Preview"
    import UserInfo from "@/pages/index/components/UserInfo"
    import BlogReply from "@/pages/index/components/blog/Reply"

    export default {
        name: "blogContent",
        components: { Preview, UserInfo, BlogReply, },
        data() {
            return {
                blog: null,
                blogs: null,
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
            async init(blogId) {
                let res = await http.getBlogById(blogId, this.user ? this.user.id : null)
                if ( res.code != 200 ) return
                this.blog = res.data
                res = await http.userBlogs(this.blog.user.id)
                if ( res.code == 200 )
                    this.blogs = res.data.data
            },
            async voteBlog(blogId, isUp) {
                const res = await blogVoteHttp.post({ blogId, replyId: -1, isUp, })
                if ( res.code == 200 ) {
                    if ( res.data == 202 ) {
                        this.blog.voteCount += isUp ? -1 : 1
                        this.blog.isUp = null
                    }
                    else {
                        this.blog.voteCount += isUp ? 1 : -1
                        this.blog.isUp = isUp
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
    .content {
        width: 875px;
        height: 100%;

        .header-wrapper {
            position: relative;

            .title {
                font-size: 28px;
                color: #222;
                margin-bottom: 5px;
                font-weight: 700;
                line-height: 1.4;
            }

            .blog-meta {
                margin-top: 5px;
            }
        }
    }

    .right {
        flex-basis: 310px;
        margin-left: 15px;
        height: 100%;
    }

    .blog-list-block {
        margin-top: 15px;

        .block-title {
            font-size: 18px;
            color: #222;
            border-bottom: 1px solid #e5e9ef;
            padding-bottom: 10px;
        }
    }

    .blog-item {
        margin-top: 16px;

        .blog-info {
            display: inline-block;
            width: 200px;
            vertical-align: middle;
        }

        .blog-title {
            font-size: 12px;
            max-height: 32px;
        }

        .blog-image {
            display: inline-block;
            width: 68px;
            height: 50px;
            border-radius: 4px;
            background-size: cover;
            background-position: 50%;
            background-repeat: no-repeat;
            vertical-align: middle;
        }
    }

    .blog-meta {
        font-size: 12px;
        display: inline-block;

        span {
            vertical-align: middle;
            margin-right: 18px;
            color: #999;

            i {
                margin-right: 5px;
                font-size: 14px;
            }
        }
    }

    .btns {
        margin-top: 14px;
        color: #505050;

        .btn {
            background-color: transparent;
            border: none;
            outline: none;
            cursor: pointer;
            font-size: 20px;
            color: #757575;
            transition: color .3s ease;
            margin-right: 24px;

            &:hover {
                color: #00A1D6;
            }

            &.active {
                color: #00A1D6;
            }
        }

        .number {
            font-size: 18px;
        }
    }

    .edit {
        display: inline-block;
        position: absolute;
        right: 15px;
        top: 10px;
        font-size: 24px;

        &:hover {
            cursor: pointer;
        }
    }
</style>
