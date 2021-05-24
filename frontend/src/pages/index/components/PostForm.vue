<template>
    <div class="post">
        <div class="title">
            <textarea v-model="form.title" ref="formTitle" class="title-textarea" :placeholder="titlePlaceholder" maxlength="40"></textarea>
        </div>
        <div class="editor top-interval">
            <Editor v-model="form.content"></Editor>
        </div>
        <div class="tags top-interval">
            <span class="tags-title">添加几个标签吧</span>
            <template v-for="tag in form.tags">
                <a-tag color="blue" :key="tag" :closable="true" @close="() => handleTagInputClose(tag)">
                    {{ tag }}
                </a-tag>
            </template>
            <a-input
                v-if="tagInputVisible"
                ref="input"
                type="text"
                size="small"
                :style="{ width: '78px' }"
                :value="tagInputValue"
                @change="handleTagInputChange"
                @blur="handleTagInputConfirm"
                @keyup.enter="handleTagInputConfirm"
            />
            <a-tag v-else color="blue" style="borderStyle: dashed;" @click="showTagInput">
                <a-icon type="plus" /> 添加标签
            </a-tag>
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
            tags: {
                type: Array,
                default: null,
            },
        },
        data() {
            return {
                form: {
                    title: null,
                    content: null,
                    tags:[],
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
            tags(n) {
                this.form.tags = this.tags
            },
        },
        mounted() {
            this.$refs.formTitle.focus()
        },
        methods: {
            handleTagInputClose(removedTag) {
                const tags = this.form.tags.filter(tag => tag !== removedTag);
                this.form.tags = tags;
            },
            handleTagInputChange(e) {
                this.tagInputValue = e.target.value.trim();
            },
            handleTagInputConfirm() {
                const tagInputValue = this.tagInputValue;
                let tags = this.form.tags;
                if (tagInputValue && tags.indexOf(tagInputValue) === -1)
                    tags = [...tags, tagInputValue];

                this.form.tags = tags
                this.tagInputVisible = false
                this.tagInputValue = null
            },
            showTagInput() {
                this.tagInputVisible = true;
                this.$nextTick(function() {
                    this.$refs.input.focus();
                });
            },
            submit() {
                if ( !this.form.title ) {
                    this.$warning("先写下标题吧. 😅")
                    return
                }

                if ( !this.form.content ) {
                    this.$warning("先写下内容吧. 😅")
                    return
                }

                if( !this.form.tags.length ) {
                    this.$warning("最少要写一个标签哦. 😅")
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

    .tags {
        .tags-title {
            font-weight: 800;
            margin-right: 14px;
        }

        .ant-tag {
            font-size: 14px;
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
