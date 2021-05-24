<template>
    <SharePostForm
        :loading="loading"
        :content="share.contentMarkdown"
        @submit="submit"
    ></SharePostForm>
</template>

<script>
    import { mapMutations } from "vuex"

    import SharePostForm from "@/pages/index/components/SharePostFrom"

    import shareHttp from "@/api/share"

    export default {
        name: "userShareEdit",
        components: { SharePostForm, },
        data() {
            return {
                loading: false,
                share: {},
            }
        },
        async mounted() {
            this.setNavbarBtnInfo({ text: "返回", routeName: "userShareIndex", isBack: true })
            if ( !/^\d+$/.test(this.$route.params.shareId) ) {
                this.$error("请勿修改地址, 即将返回")
                setTimeout(() => {
                    this.$router.go(-1)
                }, 1500)
                return
            }
            const res = await shareHttp.userShareById(this.$route.params.shareId)
            if ( res.code == 200 ) {
                this.share = res.data
            }
        },
        methods: {
            ...mapMutations("user", ["setNavbarBtnInfo",]),
            async submit(form) {
                Object.assign(this.share, form)

                this.loading = true
                const res = await shareHttp.updateShareById(this.share)
                this.loading = false

                if ( res.code != 200 ) {
                    this.$error(res.message)
                    return
                }

                this.$success(res.message)

                setTimeout(() => {
                    this.$router.go(-1)
                }, 1000)
            },
        }
    }

</script>

<style>
</style>
