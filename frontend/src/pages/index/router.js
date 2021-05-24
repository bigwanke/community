import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store"

import config from "@/config"

Vue.use(VueRouter)

import Index from "@/pages/index/views/Index"
import NotFound from "@/pages/index/views/404"

import BlogHome from "@/pages/index/views/blog/Home"
import BlogIndex from "@/pages/index/views/blog/Index"
import BlogContent from "@/pages/index/views/blog/Content"

import ProblemHome from "@/pages/index/views/problem/Home"
import ProblemIndex from "@/pages/index/views/problem/Index"
import ProblemContent from "@/pages/index/views/problem/Content"

import ShareHome from "@/pages/index/views/share/Home"
import ShareIndex from "@/pages/index/views/share/Index"
import ShareContent from "@/pages/index/views/share/Content"

import DiscussionHome from "@/pages/index/views/discussion/Home"
import DiscussionIndex from "@/pages/index/views/discussion/Index"
import DiscussionContent from "@/pages/index/views/discussion/Content"

import UserHome from "@/pages/index/views/user/Home"
import UserIndex from "@/pages/index/views/user/Index"

import UserBlogIndex from "@/pages/index/views/user/blog/Index"
import UserBlogPost from "@/pages/index/views/user/blog/Post"
import UserBlogEdit from "@/pages/index/views/user/blog/Edit"

import UserProblemIndex from "@/pages/index/views/user/problem/Index"
import UserProblemPost from "@/pages/index/views/user/problem/Post"

import UserShareIndex from "@/pages/index/views/user/share/Index"
import UserSharePost from "@/pages/index/views/user/share/Post"
import UserShareEdit from "@/pages/index/views/user/share/Edit"

import UserDiscussionIndex from "@/pages/index/views/user/discussion/Index"
import UserDiscussionPost from "@/pages/index/views/user/discussion/Post"

const routes = [
    {
        path: '/',
        name: 'index',
        meta: { title: "首页" },
        component: Index,
    },
    {
        path: "/blog",
        component: BlogHome,
        redirect: { name: "404" },
        children: [
            {
                path: "home",
                name: "blog",
                meta: { title: "分享" },
                component: BlogIndex,
            },
            {
                path: ":id",
                name: "blogContent",
                meta: { title: "内容", regex: /^[0-9]+$/ },
                component: BlogContent,
            }
        ],
    },
    {
        path: "/problem",
        component: ProblemHome,
        redirect: { name: "404" },
        children: [
            {
                path: "home",
                name: "problem",
                meta: { title: "问答" },
                component: ProblemIndex,
            },
            {
                path: ":id",
                name: "problemContent",
                meta: { title: "内容", regex: /^[0-9]+$/ },
                component: ProblemContent,
            }
        ]
    },
    {
        path: "/share",
        component: ShareHome,
        redirect: { name: "404" },
        children: [
            {
                path: "home",
                name: "share",
                meta: {title: "圈子"},
                component: ShareIndex,
            },
            {
               path: ":id",
               name: "shareContent",
               meta: {title: "内容", regex: /^[0-9]+$/},
               component: ShareContent,
            }
        ]
    },
    {
        path: "/discussion",
        component: DiscussionHome,
        redirect: { name: "404" },
        children: [
            {
                path: "home",
                name: "discussion",
                meta: {title: "讨论"},
                component: DiscussionIndex,
            },
            {
               path: ":id",
               name: "discussionContent",
               meta: {title: "内容", regex: /^[0-9]+$/},
               component: DiscussionContent,
            }
        ]
    },
    {
        path: "/user/:id",
        component: UserHome,
        redirect: { name: "404" },
        children: [
            {
                path: "home",
                name: "user",
                meta: { title: "我的主页" },
                component: UserIndex,
            },
            {
                path: "blog",
                name: "userBlog",
                meta: { title: "我的分享" },
                component: UserBlogIndex,
            },
            {
                path: "blog/post",
                name: "userBlogPost",
                meta: { requiresAuth: true, title: "写分享" },
                component: UserBlogPost,
            },
            {
                path: "blog/:blogid",
                name: "userBlogEdit",
                meta: { requiresAuth: true, title: "编辑分享" },
                component: UserBlogEdit,
            },
            {
                path: "problem",
                name: "userProblem",
                meta: { title: "我的问答" },
                component: UserProblemIndex,
            },
            {
                path: "problem/post",
                name: "userProblemPost",
                meta: { requiresAuth: true, title: "提问" },
                component: UserProblemPost,
            },
            {
                path: "share",
                name: "userShare",
                meta: { title: "我的圈子" },
                component: UserShareIndex,
            },
            {
                path: "share/post",
                name:"userSharePost",
                meta: {requiresAuth: true, title: "写圈子" },
                component: UserSharePost,
            },
            {
                path: "share/:shareId",
                name: "userShareEdit",
                meta: { requiresAuth: true, title: "提交修改" },
                component: UserShareEdit,
            },
            {
                path: "discussion",
                name: "userDiscussion",
                meta: { title: "我的讨论" },
                component: UserDiscussionIndex,
            },
            {
                path: "discussion/post",
                name:"userDiscussionPost",
                meta: {requiresAuth: true, title: "发讨论" },
                component: UserDiscussionPost,
            }
        ],
    },
    {
        path: "*",
        name: "404",
        meta: { title: "页面找不到啦" },
        component: NotFound,
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

router.beforeEach((to, from, next) => {
    if ( to.meta.regex )
        for ( let param in to.params )
            if ( typeof to.params[param] != "number" && !to.params[param].match(to.meta.regex) )
                next( {name: "404"} )

    if ( to.meta.title )
        document.title = to.meta.title + " - " + config.title
    if ( to.matched.some(record => record.meta.requiresAuth) )
        // 先看看cookie能不能匹配到
        if ( !document.cookie.match("_authorization_") && !store.state.user.user.id ) {
            Vue.prototype.$warning("先登录叭. 😜")
            store.commit("changeModalStatus", { who: "login", visible: true })
            next({ name: "index" })
        }
    next()
})

export default router
