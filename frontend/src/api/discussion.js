import axios from "@/api/axios"

//-----------------用户----------------

//发discussion
const post = ({title,contentMarkdown, contentHtml }) => {
    return axios.request({
        url: "/user/discussion/",
        method: "post",
        data: { title,contentMarkdown, contentHtml}
    })
}

//查询
const userDiscussions = (current, size, userId) => {
    return axios.request({
        url: `/user/discussion/${userId}/`,
        params: { current, size, },
    })
}
//--------------------------------------
//获取全部讨论
const getDiscussionList = () => {
    return axios.request({
        url: "/discussion/",
    })
}

const getDiscussionById = (id, userId) => {
    return axios.request({
        url: `/discussion/${id}/`,
        params: {userId,},
    })
}

const adminGetDiscussion = current =>{
    return axios.request({
        url: `admin/discussion/`,
        params: {current,},
    })
}

const adminDeleteDiscussion = id =>{
    return axios.request({
        url:`admin/discussion/${id}/`,
        method:"delete",
    })
}

export default{
    getDiscussionList,
    userDiscussions,
    post,
    getDiscussionById,
    adminGetDiscussion,
    adminDeleteDiscussion,
}
