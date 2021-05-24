

<template>
    <div class="m-left">
        <div style="width: 100%">
          
          <a-menu
            :default-selected-keys="['1']"
            :default-open-keys="['sub1']"
            mode="inline"
            theme="light"
            :inline-collapsed="collapsed"
          >
            <a-menu-item key="1" @click="toChange('user')">
              <a-icon type="user" />
              <span>用户管理</span>
            </a-menu-item>
           
            <a-sub-menu key="blog">
                <span slot="title"
                  ><a-icon type="share-alt" /><span>分享管理</span></span
                >
                <a-menu-item key="2" @click="toChange('blog')"> 文章 </a-menu-item>
                <a-menu-item key="3" @click="toChange('blogReply')"> 评论 </a-menu-item>
              </a-sub-menu>
           
            <a-sub-menu key="discussion">
              <span slot="title"
                ><a-icon type="message" /><span>讨论管理</span></span
              >
              <a-menu-item key="4" @click="toChange('discussion')"> 文章 </a-menu-item>
              <a-menu-item key="5" @click="toChange('discussionReply')"> 评论 </a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="problem">
              <span slot="title"
                ><a-icon type="question" /><span>问答管理</span></span
              >
              <a-menu-item key="6" @click="toChange('problem')"> 问题 </a-menu-item>
              <a-menu-item key="7" @click="toChange('problemAnswer')"> 回答 </a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="share">
              <span slot="title"
                ><a-icon type="mail" /><span>圈子管理</span></span
              >
              <a-menu-item key="8" @click="toChange('share')"> 文章 </a-menu-item>
              <a-menu-item key="9" @click="toChange('shareReply')"> 评论 </a-menu-item>
            </a-sub-menu>
          </a-menu>
        </div>
        <a-button
            type="primary"
            style="margin-bottom: 16px"
            @click="toggleCollapsed"
          >
            <a-icon :type="collapsed ? 'menu-unfold' : 'menu-fold'" />
          </a-button>
      </div>
</template>


<script>
import router from '@/pages/admin/router'
export default {
    data(){
        return{
            collapsed:false,
        }
    },
    methods: {
      toggleCollapsed() {
        this.collapsed = !this.collapsed;
      },
      toChange(adress){
        router.push(adress)
      }
    },
}
</script>
<style lang="less" scoped="scoped">


</style>