<template>
    <a-result
        status="404"
        title="404"
        sub-title="o, 不妙，页面找不到了"
    >
        <template #extra>
            <router-link :to="{ name: 'index' }" class="menu-item">
                <a-button type="primary"> 返回首页 </a-button>
            </router-link>
        </template>
    </a-result>
</template>