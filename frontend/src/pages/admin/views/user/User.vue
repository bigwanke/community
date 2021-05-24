<template>
      <div class="m-content">
        <table class="m-table" v-if="datas">
          <thead class="m-thead">
            <tr>
              <td>id</td>
              <td>姓名</td>
              <td>邮箱</td>
              <td>头像</td>
              <td>是否为管理员</td>
              <td>禁用状态</td>
              <td>操作</td>
            </tr>
          </thead>
          <tbody class="m-tbody">
            <tr v-for="user in users" :key="user.id">
              <td>{{ user.id }}</td>
              <td>{{ user.username }}</td>
              <td>{{ user.email }}</td>
              <img :src= "user.profile.avatar" class="avatar-img" />
              <td>{{ user.isAdmin==true?"是":"否" }}</td>
              <td>{{ user.deleted==true?"是":"否" }}</td>
              <td>
                <a-button type="primary" style="margin-right:10px" @click="showModal(user.id ,1)">修改权限</a-button>             
                <a-button :type="user.deleted==true?'primary':'danger'" @click="showModal(user.id,2)">{{ user.deleted==true?"恢复":"禁用" }}</a-button>
              </td>
            </tr>
          </tbody>
          <tfoot class="m-tfoot">
            <tr>
              <td colspan="7">
                <a-pagination
                  :current="datas.current"
                  :total="datas.total"
                  @change="onChange"
                />
              </td>
            </tr>
          </tfoot>
        </table>
        <a-modal
      title="修改提醒"
      :visible="visible"
      :confirm-loading="confirmLoading"
      @ok="handleOk"
      @cancel="handleCancel"
    >
      <p>{{ ModalText }}</p>
    </a-modal>
      </div>

      
</template>

<script>
import {mapState} from "vuex"
import http from "@/api/user"
export default {
  data() {
    return {
    dId:1,
    showType:1,
    current:1,
    datas:null,
    users:null,
    ModalText: '确认修改吗？',
    visible: false,
    confirmLoading: false,
    };
  },
  computed:{
    ...mapState("user",["user",])
  },
  async mounted(){
    const res = await http.getUserList(this.current);
    if (res.code != 200){
      this.$warning(res.message)
      return
    }
      this.datas = res.data;
      this.users = this.datas.data;
  },
  methods: {
    showModal(id,stype) {
      this.visible = true;
      this.dId=id;
      this.showType=stype;
    },
    handleOk(e) {
      this.confirmLoading = true;
      setTimeout(() => {
        this.visible = false;
        this.confirmLoading = false;
      }, 2000);
      if(this.showType == 1){
        this.changeAdmin(this.dId)
      }else{
      this.changeDeleted(this.dId)
    }
    },
    handleCancel(e) {
      console.log('Clicked cancel button');
      this.visible = false;
    },
    async  changeDeleted(id){
      this.users.forEach(e => {
        if ( e.id == id ) {
          e.deleted=!e.deleted
          return
        }
      });
      const res = await http.adminDeleteUserById(id);
      this.$success(res.message);
    },
    async changeAdmin(id){
      this.users.forEach(e => {
        if(e.id == id){
          e.isAdmin =  !e.isAdmin
          return
        }
      });
    const res = await http.adminUpdateUserById(id);
    this.$success(res.message);
    },
    async  onChange(current) {
      
      this.datas.current = current;
      const res = await http.getUserList(this.datas.current);
      console.log(res.data)
      this.datas = res.data;
      this.users = this.datas.data;
    },
    
  },
}
</script>

<style lang="less" scoped="scoped">
.m-content {
  padding-left: 30px;
  width: 100%;
  height: 100%;
  background-color: #fff;

  .m-thead {
    height: 44px;
    width: 100%;
    font-size: 14px;

  }

  .m-table {
    width: 100%;
    text-align: center;
    .m-tbody{
      background-color: #fafafa;

      .avatar-img{
        width: 48px;
        height: 48px;
        border-radius: 50%;
      }
    }

    .m-tfoot {
      height: 44px;
      width: 100%;
      text-align: center;
    }

    tr,td {
      border-bottom: 1px  solid #ebedf0;
    }
    tr {
      height: 50px;
    }

  }
}






</style>

