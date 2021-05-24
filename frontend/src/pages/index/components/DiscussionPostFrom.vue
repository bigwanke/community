<template>
    <div class="post">
        <div class="title">
            <textarea v-model="form.title" ref="formTitle" class="title-textarea" :placeholder="titlePlaceholder" maxlength="40"></textarea>
        </div>
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
        name: "postForm",
        components: { Editor, },
        props: {
            loading: {
                type: Boolean,
                default: false,
            },
            titlePlaceholder: {
                type: String,
                default: "请输入标题（40字内）",
            },
            title: {
                type: String,
                default: null,
            },
            content: {
                type: String,
                default: null,
            },
        },
        data() {
            return {
                form: {
                    title: null,
                    content: null,
                },
                tagInputVisible: false,
                tagInputValue: null,
            }
        },
        watch: {
            title(n) {
                this.form.title = n
            },
            content(n) {
                this.form.content = this.content
            },
        },
        mounted() {
            this.$refs.formTitle.focus()
        },
        methods: {
            submit() {
                if ( !this.form.title ) {
                    this.$warning("请输入标题吧")
                    return
                }

                if ( !this.form.content ) {
                    this.$warning("请输入内容吧")
                    return
                }

                const html = Editor.getMarkdownIt().render(this.form.content)

                this.form.contentHtml = html
                this.form.contentMarkdown = this.form.content

                this.$emit("submit", this.form)
            },
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
