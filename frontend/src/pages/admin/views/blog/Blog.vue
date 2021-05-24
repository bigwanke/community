<template>
  <div class="m-content">
        <table class="m-table" v-if="datas">
          <thead class="m-thead">
            <tr>
              <td>id</td>
              <td>标题</td>
              <td>标签</td>
              <td>阅读数</td>
              <td>点赞数</td>
              <td>评论数</td>
              <td>用户名称</td>
              <td>禁用状态</td>
              <td>操作</td>
            </tr>
          </thead>
          <tbody class="m-tbody">
            <tr v-for="blog in blogs" :key="blog.id">
              <td>{{ blog.id }}</td>
              <td>{{ blog.title }}</td>
              <td>
                <a-tag v-for="tag in blog.tags" :key="tag" color="blue">
                 {{ tag }}
                </a-tag>
              </td>
              <td>{{ blog.readCount }}</td>
              <td>{{ blog.voteCount }}</td>
              <td>{{ blog.replyCount }}</td>
              <td>{{ blog.user.username }}</td>
              <td>{{ blog.deleted==true?"是":"否" }}</td>
              <td>
                <a :href="`/blog/${blog.id}`" target="_blank" style="margin-right:10px">
                  查看分享
                </a>  
                <a-button :type="blog.deleted==true?'primary':'danger'" @click="showModal(blog.id)">{{ blog.deleted==true?"恢复":"禁用" }}</a-button>
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
import http from "@/api/blog"
export default {
    data() {
      return {
      dId:1,
      collapsed: false,
      current:1,
      datas:null,
      blogs:null,
      ModalText: '确认执行该操作吗？',
      visible: false,
      confirmLoading: false,
      };
    },
    async mounted(){
      const res = await http.adminGetBlog(this.current);
      if (res.code != 200){
      this.$warning(res.message)
      return
    }
      this.datas = res.data;
      this.blogs = this.datas.data;
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
      this.blogs.forEach(e => {
        if ( e.id == id ) {
          e.deleted=!e.deleted
          return
        }
      });
      const res = await http.adminDeleteBlog(id);
      this.$success(res.message);
    },
    async  onChange(current) {
      
      this.datas.current = current;
      const res = await http.adminGetBlog(this.datas.current)
      this.datas = res.data;
      this.blogs = this.datas.data;
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