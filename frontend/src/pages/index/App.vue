<template>
    <div id="app">
        <Navbar></Navbar>
        <router-view></router-view>
        <Footer></Footer>
    </div>
</template>

<script>
    import "@/style/common.less"
    
    import Navbar from "@/pages/index/components/Navbar"
    import Footer from "@/pages/index/components/Footer"

    export default {
        name: "app",
        components: { Navbar, Footer, }
    }

</script>
