<template>
  <div class="m-login-style">

    <a-form
      :form="loginForm"
      id="components-form-demo-normal-login"
      class="login-form m-text"
    >
      <a-form-item>
        <a-input
          v-decorator="[
            'username',
            {
              rules: [
                { required: true, message: 'Please input your username!' },
              ],
            },
          ]"
          placeholder="Username"
        >
          <a-icon
            slot="prefix"
            type="user"
            style="color: rgba(0, 0, 0, 0.25)"
          />
        </a-input>
      </a-form-item>
      <a-form-item>
        <a-input
          v-decorator="[
            'password',
            {
              rules: [
                { required: true, message: 'Please input your Password!' },
              ],
            },
          ]"
          type="password"
          placeholder="Password"
        >
          <a-icon
            slot="prefix"
            type="lock"
            style="color: rgba(0, 0, 0, 0.25)"
          />
        </a-input>
      </a-form-item>
      <a-form-item class="m-login-button">
        <a-button
          type="primary"
          html-type="submit"
          class="login-form-button "
          @click="login"
        >
          登录
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import http from "@/api/user";
import {mapMutations} from "vuex"
import VueRouter from 'vue-router'
import router from '@/pages/admin/router'

export default {
  nane: "login",

  beforeCreate() {
    this.loginForm = this.$form.createForm(this, { name: 'normal_login' });
  },
  methods: {
    ...mapMutations("user",["setUser"]),
    login(e) {
      e.preventDefault();
      this.loginForm.validateFields(async (err, values) => {
        if(err){
            return
        }
        
        this.loading=true;
        const res = await http.login(values);
        console.log(res);
        if (res.code != 200) this.$error(res.message);
        else {
            const data = await http.getUser();
            if(!data.data.isAdmin ){
                this.$warning("用户不是管理员")
                return
            }
            console.log(data);
            this.setUser(data.data);
            

            this.$success(res.message);
            router.push("/user");
        }

          
      });
    },
  },
};
</script>

<style lang="less" scoped="scoped">
.m-login-style {
  margin: auto;
  padding-top: 200px;
  width: 20%;
  margin-bottom: 0;
  .m-login-button{
    margin-left: 40%;
  }
}
.m-text {
  margin: auto;
}
</style>