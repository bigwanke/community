<template>
    <div class="m-content">
        <table class="m-table" v-if="datas">
          <thead class="m-thead">
            <tr>
              <td>id</td>
              <td>文章id</td>
              <td>回复id</td>
              <td>用户名称</td>
            </tr>
          </thead>
          <tbody class="m-tbody">
            <tr v-for="discussionVote in discussionVotes" :key="discussionVote.id">
              <td>{{ discussionVote.id }}</td>
              <td>{{ discussionVote.discussionId }}</td>
              <td>{{ discussionVote.replyId }}</td>
              <td>{{ discussionVote.user.username }}</td>
            </tr>
          </tbody>
          <tfoot class="m-tfoot">
            <tr>
              <td colspan="4">
                <a-pagination
                  :current="datas.current"
                  :total="datas.total"
                  @change="onChange"
                />
              </td>
            </tr>
          </tfoot>
        </table>
      </div>
</template>

<script>
import http from "@/api/discussionVote"
export default {
    data() {
      return {
      collapsed: false,
      current:1,
      datas:null,
      discussionVotes:null,
      };
    },
    async mounted(){
      const res = await http.adminGetDiscussionVote(this.current);
      if (res.code != 200){
      this.$warning(res.message)
      return
    }
      this.datas = res.data;
      this.discussionVotes = this.datas.data;
    },

    methods:{
      
        async  onChange(current) {
        
        this.datas.current = current;
        const res = await http.adminGetDiscussionVote(this.datas.current)
        this.datas = res.data;
        this.discussionVotes = this.datas.data;
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