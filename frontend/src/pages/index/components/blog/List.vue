<template>
    <div>
        <div v-if="!blogs">
            <a-skeleton active />
        </div>
        <div v-if="blogs && !blogs.length">
            空空如也
        </div>
        <ul class="blog-list">
            <li v-for="blog in blogs" :key="blog.id" class="blog-item">
                <div class="blog-content">
                    <h2 class="blog-title">
                        <router-link class="blog-bgimage" :to="{name: 'blogContent', params: {id: blog.id}}">
                            {{ blog.title }}
                        </router-link>
                    </h2>
                    <p class="blog-description">
                        <router-link class="blog-bgimage" :to="{name: 'blogContent', params: {id: blog.id}}">
                            {{ blog.description }}
                        </router-link>
                    </p>
                    <div class="blog-tags">
                        <a-tag v-for="tag in blog.tags" :key="tag" color="blue">
                            {{ tag }}
                        </a-tag>
                    </div>
                    <div class="blog-meta">
                        <span v-if="blog.user" class="blog-user">
                            <img class="blog-user-avatar" :src="blog.user.profile.avatar" alt="">
                            <span class="blog-user-name">{{ blog.user.username }}</span>
                        </span>
                        <span><a-icon type="eye" />{{ blog.readCount }}</span>
                        <span><a-icon type="like" />{{ blog.voteCount }}</span>
                        <span><icon-font type="icon-pinglun" />{{ blog.replyCount }}</span>
                        <span><icon-font type="icon-naozhong_huaban1" />{{ blog.createTime | beautifulTime }}</span>
                    </div>
                </div>
                <div class="blog-image">
                    <router-link class="blog-bgimage" :to="{name: 'blogContent', params: {id: blog.id}}">
                        <div class="blog-cover" :style="{backgroundImage: 'url('+blog.coverImage+')'}"></div>
                    </router-link>
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
    export default {
        name: "blogList",
        props: {
            blogs: null,
        }
    }
</script>

<style lang="less" scoped="scoped">
    .blog-list {
        .blog-item {
            box-shadow: 0 4px 8px 6px rgba(7, 17, 27, 6%);
            border-radius: 4px;
            display: flex;
            height: 210px;

            .blog-content {
                width: 70%;
                padding: 15px 20px;
                position: relative;

                .blog-title {
                    font-weight: 400;
                    max-height: 26px;
                    font-size: 18px;
                    line-height: 26px;
                    overflow: hidden;
                    display: block;
                }

                .blog-description {
                    font-size: 12px;
                    line-height: 20px;
                    margin: 5px 0 8px 0;

                    a {
                        display: -webkit-box;
                        -webkit-box-orient: vertical;
                        -webkit-line-clamp: 4;
                        overflow: hidden;
                        color: #6d757a;
                    }
                }

                .blog-tags {
                    position: absolute;
                    bottom: 48px;
                }

                .blog-meta {
                    position: absolute;
                    bottom: 15px;
                    height: 30px;
                    padding-top: 6px;

                    .blog-user {
                        display: inline-block;

                        .blog-user-avatar {
                            width: 24px;
                            height: 24px;
                            border-radius: 50%;
                            margin-right: 4px;
                        }
                    }

                    span {
                        vertical-align: middle;
                        margin-right: 18px;
                        color: #999;
                        
                        i {
                            margin-right: 4px;
                        }
                    }
                }
            }

            .blog-image {
                flex: 1;
                height: 100%;

                .blog-bgimage {
                    display: block;
                    width: 100%;
                    height: 100%;
                    padding: 8px;

                    .blog-cover {
                        height: 100%;
                        background-repeat: no-repeat;
                        background-position: 50%;
                        background-size: cover;
                        border-radius: 4px;
                    }
                }
            }
        }

        .blog-item:not(:first-child) {
            margin-top: 14px;
        }

    }
</style>
