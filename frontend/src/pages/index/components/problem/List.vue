<template>
    <div>
        <div v-if="!problems">
            <a-skeleton active />
        </div>
        <div v-if="problems && !problems.length">
            空空如也
        </div>
        <ul v-if="problems && problems.length" class="list problem-list-wrapper">
            <li v-for="problem in problems" :key="problem.id" class="item fixed-box">
                <div class="info fixed-box">
                    <div class="item fixed-box">
                        <div class="number">{{ problem.voteCount }}</div>
                        <div class="text">支持</div>
                    </div>
                    <div class="item fixed-box" :class="{'solved': problem.isSolved}">
                        <div class="number">{{ problem.answerCount }}</div>
                        <div class="text">回答</div>
                    </div>
                    <div class="item fixed-box">
                        <div class="number">{{ problem.readCount }}</div>
                        <div class="text">浏览</div>
                    </div>
                </div>
                <div class="meta">
                    <h2 class="title">
                        <router-link :to="{name: 'problemContent', params: {id: problem.id}}">
                            {{ problem.title }}
                        </router-link>
                    </h2>
                    <a-tag v-for="tag in problem.tags" :key="tag" color="blue">
                        {{ tag }}
                    </a-tag>
                </div>
                <div class="user-info">
                    <div v-if="problem.user" class="user">
                        {{ problem.user.username }}
                    </div>
                    <span>提问于：</span>
                    <div class="date">{{ problem.createTime | beautifulTime }}</div>
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
    export default {
        name: "problemList",
        props: ["problems"],
    }
</script>

<style lang="less" scoped="scoped">
    .problem-list-wrapper {
        margin: -15px 0;
    }

    .item:not(:last-child) {
        border-bottom: 1px solid #f0f2f7;
    }

    .item {
        box-sizing: content-box;
        padding: 20px 0;
    }

    .title {
        margin-bottom: 0;
        font-size: 18px;
        font-weight: 600;

        a {
            color: #121212;

            &:hover {
                color: #1890ff;
            }
        }
    }

    .info {

        .item:first-child {
            padding-left: 0;
        }

        .item {
            padding: 0 15px;
            border: none;
            color: #6a737c;
            flex-direction: column;
            align-items: center;

            .number {
                font-size: 18px;
                font-weight: 600;
            }

            .text {
                font-size: 12px;
            }

            &.solved {
                background-color: #5FBA7D;
                color: #fff;
            }
        }
    }

    .meta {
        padding: 0 15px;
    }

    .user-info {
        position: absolute;
        right: 0px;
        bottom: 20px;

        .user {
            display: inline-block;
            margin-right: 14px;
        }

        .date {
            display: inline-block;
        }
    }
 
</style>
