<template>
    <a-form-model ref="registerForm" :rules="rules" :model="registerForm">
        <a-form-model-item prop="username">
            <a-input v-model="registerForm.username" placeholder="用户名">
                <a-icon slot="prefix" type="user" style="color:rgba(0,0,0,.25)"/>
            </a-input>
        </a-form-model-item>
        <a-form-model-item prop="password">
            <a-input v-model="registerForm.password" type="password" placeholder="密码">
                <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)"/>
            </a-input>
        </a-form-model-item>
        <a-form-model-item prop="confirmPassword">
            <a-input v-model="registerForm.confirmPassword" type="password" placeholder="确认密码">
                <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)"/>
            </a-input>
        </a-form-model-item>
        <a-form-model-item prop="email">
            <a-input v-model="registerForm.email" placeholder="邮箱">
                <a-icon slot="prefix" type="mail" style="color:rgba(0,0,0,.25)"/>
            </a-input>
        </a-form-model-item>

        <a-form-model-item>
            <a-button block type="primary" :loading="loading" @click="register('registerForm')">
                注册
            </a-button>
        </a-form-model-item>
        <a-form-model-item class="modal-footer">
            <a-button type="link" @click="changeModalStatus({ who: 'login' })">
                立即登录
            </a-button>
        </a-form-model-item>
    </a-form-model>
</template>

<script>
    import { mapMutations } from "vuex"

    import http from "@/api/user"

    export default {
        name: "register",
        data() {
            const checkconfirmPassword = (rule, value, callback) => {
				if ( !value || value != this.registerForm.password )
					callback(new Error("两次输入密码不一致"))
				callback()
			}
            return {
                loading: false,
                registerForm: {
                    username: null,
                    password: null,
                    confirmPassword: null,
                    email: null,
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
                    confirmPassword: [
                        { required: true, validator: checkconfirmPassword, trigger: "blur" }
                    ],
                    email: [
                        {
                            type: "email",
                            required: true,
                            message: '请输入邮箱地址',
                            trigger: 'blur',
                        },
                    ],
                },
            }
        },
        methods: {
            ...mapMutations(["changeModalStatus",]),
            register(registerForm) {
                this.$refs[registerForm].validate(async valid => {
                    if ( !valid )
                        return false
                    this.loading = true
                    const res = await http.register(this.registerForm)
                    this.loading = false
                    if ( res.code != 200 )
                        this.$error(res.message)
                    else {
                        this.$success(res.message)
                        setTimeout(() => {
                            this.changeModalStatus({ who: "login" })
                        }, 1000)
                    }
                })
            },
        },
    }
</script>

<style>
</style>
