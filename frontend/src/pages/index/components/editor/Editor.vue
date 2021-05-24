<template>
    <div class="editor-container" :style="{width: width, minHeight: height}">
        <div class="editor" ref="editor"></div>
        <transition name="drag-fade">
            <div v-show="drag" class="drag-mask">
                <div class="icon">
                    <a-icon type="picture" theme="twoTone"/>
                </div>
                <span class="message">将图片拖拽到这里</span>
            </div>
        </transition>
        <div v-show="drag" class="drag-mask-wrapper" ref="dragMask"></div>
    </div>
</template>

<script>
    import ace from "ace-builds"
    import "ace-builds/webpack-resolver"
    import "ace-builds/src-noconflict/theme-textmate"
    import "ace-builds/src-noconflict/mode-markdown"

    import markdown from "@/config/markdown-it.js"

    import axios from "@/api/axios"
    import CONFIG from "@/config"

    export default {
        name: "editor",
        model: {
            prop: "content",
            event: "change",
        },
        props:{
            content: null,
            width: {
                type: String,
                default: "100%",
            },
            height: {
                type: String,
                default: "500px",
            },
        },
        data() {
        	return {
        		editor: null,
        		editSession: null,
                first: true,
                drag: false
        	}
        },
        watch: {
            content(n) {
                // 此处有bug一份，
                if ( this.first ) {
                    this.editSession.setValue(n)
                    this.first = false
                    let row = this.editSession.getLength()
                    this.editor.moveCursorTo(row, n.length + 1, true); 
                }
            }
        },
        mounted() {
            this.editor = ace.edit(this.$refs.editor, {
            	fontSize: 14,
            	tabSize: 4,
            	theme: "ace/theme/textmate",
            	mode: "ace/mode/markdown",
            	showPrintMargin: false,
            	showFoldWidgets: false,
            	highlightActiveLine: true,
            })

            this.editSession = this.editor.getSession()

            // 粘贴上传图片
            this.$refs.editor.addEventListener("paste", e => {
                let clipboardData = e.clipboardData.items
                let image = null
                for ( let i = 0; i < clipboardData.length; i++ ) {
                    if ( clipboardData[i].type.indexOf("image") !== -1 ) {
                        image = clipboardData[i].getAsFile()                        
                    }
                }
                if ( image ) {
                    this.uploadImage(image)
                        .then(res => {
                            if ( res.code == 200 ) this.editor.insert(`![${image.name}](${res.data})\n`)
                            else this.$error(res.message)
                        })
                }
            })

            // 拖拽上传
            document.addEventListener("dragenter", e => {
                this.drag = true
            })

            this.$refs.dragMask.addEventListener("dragleave", e => {
                this.drag = false
            })

            this.$refs.dragMask.addEventListener("dragover", e => {
                e.stopPropagation()
                e.preventDefault()
            })

            this.$refs.dragMask.addEventListener("drop", e => {
                e.stopPropagation()
                e.preventDefault()
                let files = e.dataTransfer.files
                let image = files[0]
                let dotIndex = image.name.lastIndexOf(".")
                if ( dotIndex == -1 ) {
                    this.$error(image.name + "不是一张图片")
                    this.drag = false
                    return
                }
                const suffix = image.name.substring(dotIndex + 1)
                if ( !CONFIG.allow_image_suffix.includes(suffix) ) {
                    this.$error(image.name + "不是一张图片")
                    this.drag = false
                    return
                }
                this.uploadImage(image)
                    .then(res => {
                        if ( res.code == 200 ) this.editor.insert(`![${image.name}](${res.data})\n`)
                        else this.$error(res.message)
                    })
                this.drag = false
            })

            //  监听改变事件，触发 v-model
            this.editSession.on("change", () => {
                this.$emit("change", this.editSession.getValue())
            })

            // this.content === null 会报错
            this.editSession.setValue(this.content ? this.content : "")
        },
        methods: {
            uploadImage(image) {
                let formData = new FormData()
                formData.append("image", image)
                return axios.request({
                    url: CONFIG.uploadImageAPIURL,
                    method: "post",
                    headers:{"Content-Type": "multipart/form-data"},
                    data: formData
                })
            }
        },
        getMarkdownIt() {
            return markdown
        }
    }
</script>

<style scoped="scoped" lang="less">

    .drag-fade-enter-active {
        transition: all .3s ease;
    }

    .drag-fade-leave-active {
        transition: all .3s ease;
    }

    .drag-fade-enter, 
    .drag-fade-leave-to {
        transform: scale(1.5);
        opacity: 0;
    }

    .editor-container {
        position: relative;
        border: 1px solid #E8E8E8;

        .editor {
            position: absolute;
            top: 0;
            right: 0;
            bottom: 0;
            left: 0;
        }

        .drag-mask {
            position: absolute;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            z-index: 998;
            background-color: #fafafa;
            border: 1px dashed #40a9ff;
            text-align: center;
            padding-top: 2%;
            border-radius: 4px;
            transition: all .3s;

            .icon {
                font-size: 78px;
            }

            .message {
                margin: 0 0 4px;
                color: rgba(0,0,0,.85);
                display: block;
            }

        }
        .drag-mask-wrapper {
            position: fixed;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            height: 100vh;
            width: 100vw;
            z-index: 999;
            background-color: transparent;
        }
    }

</style>
