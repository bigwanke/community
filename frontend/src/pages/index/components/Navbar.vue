<template>
    <header class="header">
        <div class="navbar wrapper">
            <div class="logo">不知社区</div>
            <div class="menu">
                <router-link :to="{ name: 'index' }" class="menu-item" exact>
                    <a-icon type="home" />
                    首页
                </router-link>
                <router-link :to="{ name: 'blog' }" class="menu-item" :class="{ 'router-link-exact-active': current == 'blog' }">
                    <icon-font type="icon-fenxiang" />
                    分享
                </router-link>
                <router-link :to="{ name: 'problem' }" class="menu-item" :class="{ 'router-link-exact-active': current == 'problem' }">
                    <a-icon type="question" />
                    问答
                </router-link>
                <router-link :to="{ name: 'share' }" class="menu-item" :class="{ 'router-link-exact-active': current == 'share' }">
                    <a-icon type="profile" />
                    圈子
                </router-link>
                <router-link :to="{ name: 'discussion' }" class="menu-item" :class="{ 'router-link-exact-active': current == 'discussion' }">
                    <a-icon type="mail" />
                    讨论
                </router-link>
            </div>
            <div v-if="user.id" class="navbar-right">
                <a-dropdown :trigger="['click']" class="navbar-dropdown-title">
                    <a class="ant-dropdown-link">
                        {{ user.username }}
                        <a-icon type="down" />
                    </a>
                    <a-menu slot="overlay" class="navbar-dropdown-menu">
                        <a-menu-item>
                            <router-link :to="{ name: 'user', params: { id: user.id } }">
                                个人中心
                            </router-link>
                        </a-menu-item>

                        <a-menu-divider />
                        <a-menu-item @click="logout">
                            <span>退出</span>
                        </a-menu-item>
                    </a-menu>
                </a-dropdown>
            </div>
            <div v-else class="navbar-right">
                <a-button type="link" @click="showModal('login')">
                    登录
                </a-button>
                <a-button type="link" @click="showModal('register')">
                    注册
                </a-button>
            </div>
        </div>
        <a-modal
            class="navbar-modal"
            width="400px"
            :title="title"
            :visible="modalVisible"
            :footer="null"
            @cancel="modalVisible = false"
        >
            <component :is="modalStatus.who"></component>
        </a-modal>
    </header>
</template>

<script>
    import { mapState, mapMutations, mapActions } from "vuex"
    import Login from "@/pages/index/components/Login"
    import Register from "@/pages/index/components/Register"

    import http from "@/api/user"

    export default {
        name: "navbar",
        components:{ Register, Login, },
        data() {
            return {
                current: "index",
            }
        },
        computed: {
            ...mapState(["modalStatus",]),
            ...mapState("user", ["user",]),
            modalVisible: {
                get() {
                    return this.modalStatus.visible
                },
                set(visible) {
                    this.changeModalStatus({ visible })
                }
            },
            title() {
                return this.modalStatus.who == "login" ? "登录" : "注册"
            },
        },
        watch: {
            $route(toRouter, fromRouter) {
                let name = toRouter.name
                if ( !name ) return
                for ( let i = 0; i < name.length; i++ ) {
                    if ( name.charAt(i) >= 'A' && name.charAt(i) <= 'Z' ) {
                        name = name.substring(0, i);
                        break;
                    }
                }
                this.current = name
            },
        },
        mounted() {
            this.getUser()
        },
        methods: {
            ...mapMutations(["changeModalStatus",]),
            ...mapMutations("user", ["initUser",]),
            ...mapActions("user", ["getUser",]),
            showModal(who) {
                this.changeModalStatus({ who, visible: true })
            },
            async logout() {
                const res = await http.logout()
                if ( res.code != 200 ) this.$error(res.message)
                else {
                    this.$success(res.message)

                    setTimeout(() => {
                        if ( this.$route.name != "index" )
                            this.$router.push({ name: "index" })
                    }, 100)

                    setTimeout(() => {
                        // 从vuex删除信息
                        this.initUser()
                    }, 200)
                }
            },
        },
    }
</script>

<style lang="less">
    .header {
        background-color: #fff;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 15%);
        border-bottom: 1px solid #e8e8e8;
        position: relative;
        z-index: 1;
    }

    .navbar {
        display: flex;
        height: 60px;
        line-height: 60px;
        position: relative;

        .logo {
            font-size: 20px;
            font-weight: bold;
            margin-right: 2%;
        }

        .menu {
            display: flex;

            .menu-item {
                display: block;
                padding: 0 20px;
                color: rgba(0, 0, 0, 0.65);
                border-bottom: 2px solid transparent;
                transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);

                &:hover {
                    color: #1890ff;
                    border-bottom: 2px solid #1890ff;
                }

                .anticon {
                    margin-right: 10px;
                }
            }

            .router-link-exact-active {
                color: #1890ff;
                border-bottom: 2px solid #1890ff;
            }

        }

        .navbar-right {
            position: absolute;
            right: 0;
        }
    }

    .navbar-modal {
        .ant-modal-body {
            padding: 18px;

            .modal-footer .ant-form-item-control {
                line-height: 24px;
            }

            .modal-footer .ant-form-item-control .ant-btn-link {
                height: 24px;
                padding: 0;
            }
        }

        .ant-form-item {
            margin-bottom: 10px;

            &:last-child {
                margin-bottom: 0;
            }
        }
    }

    .navbar-dropdown-title {
        font-size: 18px;
        .anticon-down {
            font-size: 15px !important;
        }
    }

    .navbar-dropdown-menu {
        margin-top: 23px;
    }
</style>
