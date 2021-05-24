import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

import User from "@/pages/admin/views/user/User"
import Login from "@/pages/admin/views/Login"
import Home from "@/pages/admin/views/Home"
import Blog from "@/pages/admin/views/blog/Blog"
import BlogReply from "@/pages/admin/views/blog/BlogReply"
import BlogVote from "@/pages/admin/views/blog/BlogVote"
import Discussion from "@/pages/admin/views/discussion/Discussion"
import DiscussionReply from "@/pages/admin/views/discussion/DiscussionReply"
import DiscussionVote from "@/pages/admin/views/discussion/DiscussionVote"
import Problem from "@/pages/admin/views/problem/Problem"
import ProblemAnswer from "@/pages/admin/views/problem/ProblemAnswer"
import ProblemVote from "@/pages/admin/views/problem/ProblemVote"
import Share from "@/pages/admin/views/share/Share"
import ShareReply from "@/pages/admin/views/share/ShareReply"
import ShareVote from "@/pages/admin/views/share/ShareVote"




const routes = [
    {
        path: '/',
        component: Home,
        children:[
            {
                path:'user',
                component:User
            },
            {
                path:'blog',
                component:Blog
            },
            {
                path:'blogReply',
                component:BlogReply

            },
            {
                path:'discussion',
                component:Discussion
            },
            {
                path:'discussionReply',
                component:DiscussionReply
            },
            {
                path:'problem',
                component:Problem
            },
            {
                path:'problemAnswer',
                component:ProblemAnswer
            },
            {
                path:'share',
                component:Share
            },
            {
                path:'shareReply',
                component:ShareReply
            },
        ]
    },
    {
        path: '/login',
        name: 'login',
        component: Login,
    },
]

const router = new VueRouter({
    mode: 'history',
    base: "/admin/",
    routes
})

export default router
