<template>
    <div class="wrapper top-interval " id="test">
        <h2>练习编写MarkDown</h2>
        <Editor v-model="content"></Editor>
        <button @click="toHtml">to HTMl</button>
        <Preview :html="html"></Preview>

        <h2>图片上传</h2>
        <div class="clearfix">
            <a-upload
                name="image"
                :action="config.uploadImageAPIURL"
                list-type="picture-card"
                :file-list="fileList"
                @preview="handlePreview"
                @change="handleChange"
            >
                <div v-if="fileList.length < 8">
                    <a-icon type="plus" />
                    <div class="ant-upload-text">
                        Upload
                    </div>
                </div>
            </a-upload>
            <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
                <img alt="example" style="width: 100%" :src="previewImage" />
            </a-modal>
          </div>
    </div>
</template>

<script>
    import Editor from "@/pages/index/components/editor/Editor"
    import Preview from "@/pages/index/components/editor/Preview"

    import markdown from "@/config/markdown-it.js"

    import content from "@/config/content"
    import config from "@/config"

    function getBase64(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => resolve(reader.result);
            reader.onerror = error => reject(error);
        });
    }

    export default {
        name: "Index",
        components: { Editor, Preview },
        data() {
            return {
                content: content,
                config: config,
                html: null,
                previewVisible: false,
                previewImage: '',
                fileList: [
                    {
                      uid: '-1',
                      name: 'image.png',
                      status: 'done',
                      url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
                    },
                    {
                      uid: '-2',
                      name: 'image.png',
                      status: 'done',
                      url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
                    },
                    {
                      uid: '-3',
                      name: 'image.png',
                      status: 'done',
                      url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
                    },
                    {
                      uid: '-4',
                      name: 'image.png',
                      status: 'done',
                      url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
                    },
                    {
                      uid: '-5',
                      name: 'image.png',
                      status: 'error',
                    },
                ],
            }
        },
        methods: {
            toHtml() {
                this.html = markdown.render(this.content)
            },
            handleCancel() {
                this.previewVisible = false;
            },
            async handlePreview(file) {
                // 上传图片
                if (!file.url && !file.preview) {
                    file.preview = await getBase64(file.originFileObj);
                }
                this.previewImage = file.url || file.preview;
                this.previewVisible = true;
            },
            handleChange({ fileList }) {
                this.fileList = fileList;
            },
        },
    }
</script>

<style lang="less" scoped="scoped">
    #test {
        background-color: #fff;
        border-radius: 4px;
        padding: 15px 20px;
    }

    h2 {
        margin: 20px 0;
    }

</style>
