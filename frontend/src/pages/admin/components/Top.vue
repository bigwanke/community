<template>
    <div class="m-top">
      <div class="m-top-content" v-if="user.id" style="background-color: #fff;">
        <span style="color:blue">{{ user.username }}</span>，欢迎您！
        <div class="m-top-logout" @click="logout">
          <a>退出</a>
        </div>
      </div>
    </div>
</template>

<script>
import {mapState} from "vuex"
import http from "@/api/user"
export default {
    computed:{
    ...mapState("user",["user",])
  },
  mounted(){
    console.log(this.user)
  },
  methods:{
    async logout() {
                const res = await http.logout()
                if ( res.code != 200 ) this.$error(res.message)
                else {
                    this.$success(res.message)

                    setTimeout(() => {
                        if ( this.$route.name != "login" )
                            this.$router.push({ name: "login" })
                    }, 100)

                    setTimeout(() => {
                        // 从vuex删除信息
                        this.initUser()
                    }, 200)
                }
            },
  }
  
}
</script>

<style lang="less" scoped="scoped">
.m-top {
  height: 70px;
  .m-top-content{
    padding: 20px;
    height: 100%;

    .m-top-logout{
      float: right;
    }
  }
}

</style>