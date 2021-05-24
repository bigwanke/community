<template>
    <div class="wrapper top-interval">
        <UserHeader :user="user"></UserHeader>
        <UserNavbar></UserNavbar>
        <div class="content-wrapper top-interval">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
    import UserHeader from "@/pages/index/components/user/Header"
    import UserNavbar from "@/pages/index/components/user/Navbar"

    import userHttp from "@/api/user"

    export default {
        name: "userHome",
        components: { UserHeader, UserNavbar, },
        data() {
            return {
                user: {},
            }
        },
        async mounted() {
            const res = await userHttp.getUserById(this.$route.params.id)
            if ( res.code == 200 ) {
                this.user = res.data
            }
        }
    }

</script>
