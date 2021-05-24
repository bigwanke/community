<template>
    <div>
       <div v-if="!shares">
            <a-skeleton active />
        </div>
        <div v-if="shares && !shares.length">
            空空如也
        </div>
        <div class="share-body">
            <ul class="share-list">
                <li v-for="share in shares" class="share-item">
                    <div  class="li-left">
                        <!-- 作者头像 -->
                        <router-link :to="{name: 'userShare',params: {'id': share.user.id}}">
                            <img :src="share.user.profile.avatar" alt="没有图" />
                        </router-link>
                    </div>
                    <div class="li-body">
                        <div  class="author-name">
                            <router-link :to="{name: 'userShare',params: {'id': share.user.id}}">{{share.user.username}}</router-link>
                        </div>
                        <!-- 圈子发布日期 -->
                        <div class="create-date">{{share.createTime | beautifulTime}}</div>
                        <!-- 文章简略 -->
                        <div class="li-content">
                            <router-link :to="{name:'shareContent', params: {id: share.id}}" title="点击进入">
                                <p>
                                    {{share.description}}
                                </p>
                            </router-link>
                        </div>
                        <div class="li-bottom">
                            <div class="share-buttn"><a-icon type="eye" />{{share.readCount}}</div>
                            <div class="share-buttn"><a-icon type="message" />{{share.replyCount}}</div>
                            <div class="share-buttn"><a-icon type="like" />{{share.voteCount}}</div>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
    import {mapState} from "vuex"
    export default{
        name: "shareList",
        props: {
            shares : null,
        },
        computed: {
            ...mapState("user", ["user"])
        },
    }
</script>

<style lang="less" scoped="scoped">
.share-body{

    display: flex;
    
    .share-list {
        margin-left: 70px;
        margin-right: 70px;
        .share-item {
            box-shadow: 0 4px 8px 6px rgba(7, 17, 27, 6%);
            border-radius: 4px;
            display: flex;
            width: 700px;
            margin-bottom: 20px;

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

                .author-name {
                    font-size: 16px;

                }

                .create-date {
                    font-size: 12px;
                    color: #99a2aa;
                }

                .li-content {
                    position: relative;
                    margin-top: 14px;
                    width: 530px;

                    a {
                        color: #98a1a3;
                        display: -webkit-box;
                        -webkit-box-orient: vertical;
                        -webkit-line-clamp: 4;
                        overflow: hidden;
                    }
                }

                .li-bottom {
                    display: flex;
                    margin-left: -20px;
                    flex-wrap: wrap;
                    justify-content: space-evenly;
                    .share-buttn {
                        display: inline-block;
                        line-height: 48px;
                        font-size: 13px;
                    }
                }
            }
        }
    }
}

</style>
