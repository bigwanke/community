<template>
  <div class="m-content">
        <table class="m-table" v-if="datas">
          <thead class="m-thead">
            <tr>
              <td>id</td>
              <td>标题</td>
              <td>阅读数</td>
              <td>点赞数</td>
              <td>评论数</td>
              <td>用户名称</td>
              <td>禁用状态</td>
              <td>操作</td>
            </tr>
          </thead>
          <tbody class="m-tbody">
            <tr v-for="discussion in discussions" :key="discussion.id">
              <td>{{ discussion.id }}</td>
              <td>{{ discussion.title }}</td>
              <td>{{ discussion.readCount }}</td>
              <td>{{ discussion.voteCount }}</td>
              <td>{{ discussion.replyCount }}</td>
              <td>{{ discussion.user.username }}</td>
              <td>{{ discussion.deleted==true?"是":"否" }}</td>
              <td>
                <a :href="`/discussion/${discussion.id}`" target="_blank" style="margin-right:10px">
                  查看文章
                </a>  
                <a-button :type="discussion.deleted==true?'primary':'danger'" @click="showModal(discussion.id)">{{ discussion.deleted==true?"恢复":"禁用" }}</a-button>
              </td>
            </tr>
          </tbody>
          <tfoot class="m-tfoot">
            <tr>
              <td colspan="9">
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
      title="禁用/解除"
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
import http from "@/api/discussion"
export default {
    data() {
      return {
      dId:1,
      collapsed: false,
      current:1,
      datas:null,
      discussions:null,
      ModalText: '确认执行该操作吗？',
      visible: false,
      confirmLoading: false,
      };
    },
    async mounted(){
      const res = await http.adminGetDiscussion(this.current);
      if (res.code != 200){
      this.$warning(res.message)
      return
    }
      this.datas = res.data;
      this.discussions = this.datas.data;
    },

    methods:{
      showModal(id) {
      this.visible = true;
      this.dId=id;
    },
    handleOk(e) {
      this.confirmLoading = true;
      setTimeout(() => {
        this.visible = false;
        this.confirmLoading = false;
      }, 2000);
      this.changeDeleted(this.dId)
    },
    handleCancel(e) {
      console.log('Clicked cancel button');
      this.visible = false;
    },
    async  changeDeleted(id){
      this.discussions.forEach(e => {
        if ( e.id == id ) {
          e.deleted=!e.deleted
          return
        }
      });
      const res = await http.adminDeleteDiscussion(id);
      this.$success(res.message);
    },
    async  onChange(current) {
      
      this.datas.current = current;
      const res = await http.adminGetDiscussion(this.datas.current)
      this.datas = res.data;
      this.discussions = this.datas.data;
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