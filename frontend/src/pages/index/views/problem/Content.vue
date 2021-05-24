<template>
    <div>
        <div v-if="problem" class="fixed-box">
            <div class="content content-wrapper">
                <div class="tags">
                    <a-tag v-for="tag in problem.tags" :key="tag" color="blue">
                        {{ tag }}
                    </a-tag>
                </div>
                <Preview :html="problem.contentHtml"></Preview>
                <div class="btns">
                    <a-button v-if="user.id" type="primary" ghost @click="showAnswerEdit = !showAnswerEdit">
                        <a-icon type="edit" />写回答
                    </a-button>
                    <a-button type="link" :class="{'active': problem.isUp == true}" @click="voteProblem(problem.id, true)">
                        <a-icon type="like" theme="filled" />
                        好问题
                        {{ problem.voteCount }}
                    </a-button>
                    <a-button type="link" :class="{'active': problem.isUp == false}" @click="voteProblem(problem.id, false)">
                        <a-icon type="dislike" theme="filled" />
                        差问题
                    </a-button>
                    <a-button type="link">
                        <a-icon type="message" theme="filled" />
                        {{ problem.answerCount }}
                        条答案
                    </a-button>
                </div>
            </div>
            <div class="right">
                <UserInfo class="content-wrapper" :user="problem.user"></UserInfo>
            </div>
        </div>
        <transition name="edit">
            <div v-if="showAnswerEdit" class="top-interval content-wrapper content">
                <Editor v-model="answerContent" height="200px"></Editor>
                <a-button type="primary" ghost @click="submitProblemAnswer" style="margin-top: 10px;">
                    提交答案
                </a-button>
            </div>
        </transition>
        <div v-if="answers" class="answer-list content-wrapper top-interval">
            <div class="answer-header">
                {{ answers.length }}
                个回答
            </div>
            <ul>
                <li v-for="answer in answers" :key="answer.id" class="item fixed-box">
                    <div class="answer-content">
                        <Preview :html="answer.contentHtml"></Preview>
                        <div class="answer-create-time">发布于 {{ answer.createTime | beautifulTime }}</div>
                        <div class="btns">
                            <a-button type="primary" class="answer-btn" :class="{'active': answer.isUp == true}" @click="voteAnswer(answer.id, true)">
                               <a-icon type="caret-up" /> 赞同
                               {{ answer.voteCount }}
                            </a-button>
                            <a-button class="answer-btn" :class="{'active': answer.isUp == false}" @click="voteAnswer(answer.id, false)">
                                <a-icon type="caret-down" />
                            </a-button>
                            <a-button v-if="problem && user && user.id == problem.user.id" :class="{accepted : answer.isAccept }" @click="accept(answer.id)">
                                <a-icon type="check" /> 解决
                            </a-button>
                            <a-button v-if="user.id == answer.user.id" @click="deleteById(answer.id)">
                                删除
                            </a-button>
                        </div>
                    </div>
                    <div class="answer-user-info">
                        <UserInfo :user="answer.user"></UserInfo>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
    import { mapState } from "vuex"

    import problemHttp from "@/api/problem"
    import probelmAnswerHttp from "@/api/problemAnswer"
    import voteHttp from "@/api/problemVote"

    import Preview from "@/pages/index/components/editor/Preview"
    import UserInfo from "@/pages/index/components/UserInfo"
    import Editor from "@/pages/index/components/editor/Editor"

    export default {
        name: "problemContent",
        components: { Preview, UserInfo, Editor, },
        data() {
            return {
                problem: null,
                showAnswerEdit: false,
                answerContent: null,
                answers: null,
            }
        },
        computed: {
            ...mapState("user", ["user",],),
        },
        mounted() {
            this.initProblem(this.$route.params.id)
            this.initProblemAnswers(this.$route.params.id)
        },
        methods: {
            async initProblem(problemId) {
                const res = await problemHttp.getById(problemId, this.user.id)
                if ( res.code != 200 ) return
                this.problem = res.data
            },
            async initProblemAnswers(problemId) {
                const res = await probelmAnswerHttp.listByProblemId(problemId, this.user.id);
                if ( res.code != 200 ) return
                this.answers = res.data.data
            },
            async submitProblemAnswer() {
                if ( !this.answerContent ) {
                    this.$warning("先输入内容吧. 😅")
                    return
                }
                const contentHtml = Editor.getMarkdownIt().render(this.answerContent)
                const res = await probelmAnswerHttp.post({ contentMarkdown: this.answerContent, contentHtml, problemId: this.problem.id })
                if ( res.code == 200 ) {
                    this.$success(res.message)
                    this.showAnswerEdit = false
                    this.problem.answerCount ++
                    this.initProblemAnswers(this.problem.id)
                    this.answerContent = null
                    return
                }
                this.$error(res.message)
            },
            async voteProblem(problemId, isUp) {
                const res = await voteHttp.post({ problemId, answerId: -1, isUp })
                if ( res.code == 200 ) {
                    if ( res.data == 202 ) {
                        this.problem.voteCount += isUp ? -1 : 1
                        this.problem.isUp = null
                    }
                    else {
                        this.problem.voteCount += isUp ? 1 : -1
                        this.problem.isUp = isUp
                    }
                    this.$success(res.message)
                    return
                }
                this.$error(res.message)
            },
            async voteAnswer(answerId, isUp) {
                const res = await voteHttp.post({ problemId: -1, answerId, isUp })
                if ( res.code == 200 ) {
                    this.answers.forEach(e => {
                        if ( e.id == answerId ) {
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
                    this.$success(res.message)
                    return
                }
                this.$error(res.message)
            },
            async accept(answerId) {
                for ( let i of this.answers )
                    if ( i.id == answerId && i.isAccept ) {
                        this.$warning("已经标记解决啦. 🐷")
                        return
                    }
                const res = await probelmAnswerHttp.put({ id: answerId, problemId: this.problem.id, isAccept :true, })
                if ( res.code == 200 ) {
                    this.answers.forEach(e => {
                        if ( e.id == answerId )
                            e.isAccept = true
                    })
                    this.$success(res.message)
                    return
                }
                this.$error(res.message)
            },
            async deleteById(id) {
                const res = await probelmAnswerHttp.deleteById(id)
                if ( res.code == 200 ) {
                    const t = this.answers.filter(e => e.id != id)
                    this.answers = t
                    this.problem.answerCount --
                    this.$success(res.message)
                    return
                }
                this.$error(res.message)
            }
        }
    }
</script>

<style lang="less" scoped="scoped">
    .content {
        width: 875px;
        height: 100%;
    }

    .tags {
        margin-bottom: 15px;
    }

    .right {
        margin-left: 15px;
        flex-basis: 310px;
        height: 100%;

        .user-info-block {
            height: 100%;
        }
    }

    .ant-btn-link {
        color: #8590a6;
        border: none;

        &:hover {
            color: #76839b;
        }

        &.active {
            color: #1890ff;
        }
    }

    .answer-header {
        font-weight: bold;
        padding-bottom: 15px;
    }

    .item {
        padding: 15px 0;
        border-top: 1px solid #e5e9ef;
    }

    .item:last-child {
        padding-top: 14px;
        padding-bottom: 0;
    }

    .answer-content {
        width: 882px;
        padding-right: 20px;
    }

    .answer-user-info {
        flex: 1;
        height: 100%;
        box-shadow: 0 4px 8px 6px rgba(7, 17, 27, 6%);
        padding: 8px;
        border-radius: 4px;
    }

    .answer-create-time {
        margin-top: 10px;
        color: #8590a6;
    }

    .btns {
        margin-top: 8px;

        .ant-btn {
            margin-right: 8px;
        }
    }

    .edit-enter-active, .edit-leave-active {
        transition: opacity .5s;
    }
    .edit-enter, .edit-leave-to {
        opacity: 0;
    }

    .answer-btn {
        background-color: transparent;
        color: #1890ff;

        &.active {
            background-color: #1890ff;
            color: #fff;
            border-color: #1890ff;
        }
    }
    
    .ant-btn.accepted {
        background-color: #5FBA7D;
        color: #fff;
        border-color: #5FBA7D;
    }

</style>
