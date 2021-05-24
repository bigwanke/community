import axios from "@/api/axios"

// ----------------------- 用户部分-------------------------
const post = ({ title, tags, contentMarkdown, contentHtml }) => {
    return axios.request({
        url: "/user/blog/",
        method: "post",
        data: { title, tags, contentMarkdown, contentHtml, }
    })
}

const userBlogs = userId => {
    return axios.request({
        url: `/user/blog/`,
        params: { userId, },
    })
}

const userBlogById = blogId => {
    return axios.request({
        url: `/user/blog/${blogId}/`
    })
}

const updateBlogById = ({ id, title, tags, contentMarkdown, contentHtml }) => {
    return axios.request({
        url: `/user/blog/${id}/`,
        method: "put",
        data: { id, title, tags, contentMarkdown, contentHtml },
    })
}

// ----------------------- end -------------------------

const getBlogById = (id, userId) => {
    return axios.request({
        url: `/blog/${id}/`,
        params: {userId,},
    })
}

const getBlogList = () => {
    return axios.request({
        url: "/blog/",
    })
}

const adminGetBlog = current =>{
    return axios.request({
        url:`/admin/getBlogList/`,
        params:{current, },
    })
}
const adminDeleteBlog = id =>{
    return axios.request({
        url:`/admin/blog/${id}/`,
        method:"delete",
    })
}

export default {
    post,
    userBlogs,
    getBlogById,
    getBlogList,
    userBlogById,
    updateBlogById,
    adminGetBlog,
    adminDeleteBlog,
}
