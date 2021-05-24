<template>
    <div class="background">
        <div v-if="!discussions">
            <a-skeleton active />
        </div>
        <div v-if="discussions && !discussions.length">
            空空如也
        </div>
        <table width="100%" border="0" cellpadding="0" cellspacing="0"
            v-if="discussions && discussions.length">
            <thead>
                <tr>
                    <td>主题</td>
                    <td>作者</td>
                    <td>回复/查看</td>
                    <td>最后发表</td>
                </tr>
            </thead>
            <tbody>
                <tr v-for="discussion in discussions">
                    <td class="discussion-title">
                        <router-link :to="{name:'discussionContent',params: {id: discussion.id}}">{{discussion.title}}</router-link>
                    </td>
                    <td v-if="discussion.user" class="last-post">
                        <a>{{discussion.user.username}}</a>
                        <em>{{discussion.createTime | beautifulTime}}</em>
                    </td>
                    <td class="report-count">
                        <span>{{discussion.replyCount}}/{{discussion.readCount}}</span>
                    </td>
                    <td v-if="discussion.reply.id != null" class="last-post">
                        <a>{{discussion.reply.user.username}}</a>
                        <em>{{discussion.reply.createTime | beautifulTime}}</em>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    export default {
        name: "discussionTable",
        props: {
            discussions: null
        },
        // watch:{
        //     'discussion.reply.id':function(){
        //         window.location.reload();
        //     }
        // }
    }
</script>

<style lang="less" scoped="scoped">
    td {
        padding-top: 8px;
        padding-bottom: 8px;
        padding-right: 16px;
        font-size: 14px;
        border-bottom: solid 1px #f5f5f5;
        line-height: 18px;
        vertical-align: top;
    }

    em {
        color: #6b6b6b;
        white-space: nowrap;
        display: inline-block;
        font-style: normal;
    }

    .background {

        width: 1200px;

        .discussion-title {
            width: 700px;
        }

        .last-post {
            display: flex;
            flex-direction:column;
        }

    }
</style>
