import axios from "@/api/axios"

const BaseURL = "/blog/reply/"

const post = ({ contentMarkdown, contentHtml, parentId, blogId, }) => {
    return axios.request({
        url: BaseURL,
        method: "post",
        data: { contentMarkdown, contentHtml, parentId, blogId, },
    })
}

const listByBlogId = ({ blogId, userId, }) => {
    return axios.request({
        url: BaseURL,
        params: { blogId, userId, },
    })
}

const deleteById = id => {
    return axios.request({
        url: `${BaseURL}${id}/`,
        method: "delete"
    })
}

const adminGetBlogReply = current =>{
    return axios.request({
        url:`admin/blog/reply/`,
        params:{current,},
    })
}

const adminDeleteBlogReplyById = id =>{
    return axios.request({
        url:`admin/blog/reply/${id}/`,
        method:"delete",
    })
}

export default {
    post,
    listByBlogId,
    deleteById,
    adminGetBlogReply,
    adminDeleteBlogReplyById,
}
