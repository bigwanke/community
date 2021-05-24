<template>
    <div class="post">
        <div class="editor top-interval">
            <Editor v-model="form.content"></Editor>
        </div>
        <div class="btns">
            <a-button type="primary" :loading="loading" @click="submit">
                提交
            </a-button>
        </div>
    </div>
</template>

<script>

    import Editor from "@/pages/index/components/editor/Editor"

     export default {
         name: "sharePostForm",
         components: { Editor, },
         props: {
             loading: {
                 type: Boolean,
                 default: false,
             },
             content: {
                 type: String,
                 default: null,
             },
         },
         data() {
             return {
                 form: {
                     content: null,
                 },
             }
         },
         watch:{
             content(n) {
                 this.form.content = this.content
             },
         },
         methods:{
            submit(){
                if(!this.form.content){
                    this.$warning("亲,填写你要发送的内容")
                    return
                }

                const html = Editor.getMarkdownIt().render(this.form.content)

                this.form.contentHtml = html
                this.form.contentMarkdown = this.form.content

                console.log(this.form.contentMarkdown);
                this.$emit("submit", this.form)
                // this.$success("发布成功,👌")
            }
         }
     }
</script>

<style lang="less" scoped="scoped">
    .title {
        .title-textarea {
            font-size: 28px;
            width: 100%;
            border: none;
            font-weight: bold;
            height: 47px;
            outline: none;
            resize: none;
            border-bottom: 1px solid #CCD0D4;
        }

        .title-textarea::-webkit-input-placeholder {
            color: #CCD0D4;
            font-weight: 500;
        }
    }


    .btns {
        position: relative;
        height: 32px;

        .ant-btn {
            position: absolute;
            right: 20px;
        }
    }
</style>
