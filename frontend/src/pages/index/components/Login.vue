<template>
    <a-form-model ref="loginForm" :rules="rules" :model="loginForm">
        <a-form-model-item prop="username">
            <a-input v-model="loginForm.username" placeholder="用户名">
                <a-icon slot="prefix" type="user" style="color:rgba(0,0,0,.25)"/>
            </a-input>
        </a-form-model-item>
        <a-form-model-item prop="password">
            <a-input v-model="loginForm.password" type="password" placeholder="密码">
                <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)"/>
            </a-input>
        </a-form-model-item>
        <a-form-model-item>
            <a-button block type="primary" :loading="loading" @click="login('loginForm')">
                登录
            </a-button>
        </a-form-model-item>
        <a-form-model-item class="modal-footer">
            <a-button type="link" @click="changeModalStatus({ who: 'register' })">
                注册账号
            </a-button>
            <a-button type="link" style="float: right">
                找回密码
            </a-button>
        </a-form-model-item>
    </a-form-model>
</template>

<script>
    import { mapMutations, mapActions } from "vuex"

    import http from "@/api/user"

    export default {
        name: "login",
        data() {
            return {
                loading: false,
                loginForm: {
                    username: null,
                    password: null,
                },
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 3, message: '用户名不得少于3位', trigger: 'blur' },
                        { max: 16, message: '用户名不得大于16位', trigger: 'blur' },
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { min: 3, message: '密码不得少于3位', trigger: 'blur' },
                        { max: 16, message: '密码不得大于16位', trigger: 'blur' },
                    ],
                },
            }
        },
        methods: {
            ...mapMutations(["changeModalStatus",]),
            ...mapActions("user", ["getUser",]),
            login(loginForm) {
                this.$refs[loginForm].validate(async valid => {
                    if ( !valid )
                        return false
                    this.loading = true
                    const res = await http.login(this.loginForm)
                    if ( res.code != 200 )
                        this.$error(res.message)
                    else {
                        this.getUser()
                        this.$success(res.message)
                        this.changeModalStatus({ visible: false })
                    }
                    this.loading = false
                })
            },
        },
    }

</script>
