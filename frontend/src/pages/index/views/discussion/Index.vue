<template>
    <div>
        <DiscussionTable class="content-wrapper" :discussions="discussions"></DiscussionTable>
        <div class="plus">
                <a
                    :disabled="!user.id"
                    :title="user.id ? '发讨论' : '先登录吧'"
                    @click="$router.push({ name: 'userDiscussionPost', params: {id: user.id} })">
                    <a-icon type="plus-circle" title="发讨论" class="icon"/>
                </a>
            </div>
        </div>
    </div>
</template>

<script>
    import {mapState} from "vuex"

    import DiscussionTable from "@/pages/index/components/discussion/Table"

    import http from "@/api/discussion"

    export default {
        name: "discussionIndex",
        components: {
            DiscussionTable,
        },
        data() {
            return {
                discussions: null,
            }
        },
        computed: {
            ...mapState("user", ["user"]),
        },
        async mounted() {
            const res = await http.getDiscussionList();
            this.discussions = res.data.data;
        },
    }
</script>

<style lang="less" scoped="scoped">
    .plus{
        width: 40px;
        height: 40px;
        font-size: 40px;
        position: fixed;
        right: 50px;
        top: 600px;
        display: -webkit-box;

            .icon{
                margin-left: 5px;
                background-color: white;
                border-radius: 50%;
            }
    }
</style>
