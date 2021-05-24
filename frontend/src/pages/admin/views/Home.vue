<template>
    <div>
    <Top class="m-top"></Top>
    <div class="m-down">
    <Left></Left>
    <router-view ></router-view>
    </div>
    
    </div>
    
</template>

<script>
import Top from "@/pages/admin/components/Top"
import Left from '@/pages/admin/components/Left'

export default {
    components:{Top,Left},
}
</script>

<style lang="less" scoped="scoped">

.m-down {
  display: flex;
}
.m-top {
  height: 70px;
}
</style>