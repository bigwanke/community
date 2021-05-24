import axios from "@/api/axios"

const post = ({contentHtml,contentMarkdown, parentId, discussionId, }) => {
    return axios.request({
        url: "/discussion/reply/",
        method: "post",
        data: {contentHtml, contentMarkdown, parentId, discussionId, },
    })
}

const listByDiscussionId = ({ discussionId, userId, }) => {
    return axios.request({
        url: "/discussion/reply/",
        params: { discussionId, userId, },
    })
}

const deleteById = id => {
    return axios.request({
        url: `/discussion/reply/${id}/`,
        method: "delete"
    })
}

const adminGetDiscussionReply = current =>{
    return axios.request({
        url: `admin/discussion/reply/`,
        params: {current,},
    })
}

const adminDeleteDiscussionReply = id =>{
    return axios.request({
        url:`admin/discussion/reply/${id}/`,
        method:"delete",
    })
}

export default{
    post,
    listByDiscussionId,
    deleteById,
    adminGetDiscussionReply,
    adminDeleteDiscussionReply,
}
