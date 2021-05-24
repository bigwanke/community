<template>
    <div class="editor-preview" v-html="html"></div>
</template>

<script>
    import "@/style/ace.min.css"
    import "@/style/markdown.css"

    export default {
        name: "preview",
        props: ["html",],
    }
</script>

<style lang="less">
</style>
