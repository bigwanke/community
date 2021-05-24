import axios from "@/api/axios"

const post = ({contentHtml,contentMarkdown, parentId, shareId, }) => {
    return axios.request({
        url: "/share/reply/",
        method: "post",
        data: {contentHtml, contentMarkdown, parentId, shareId, },
    })
}

const listByShareId = ({ shareId, userId, }) => {
    return axios.request({
        url: "/share/reply/",
        params: { shareId, userId, },
    })
}

const deleteById = id => {
    return axios.request({
        url: `/share/reply/${id}/`,
        method: "delete"
    })
}

const adminGetShareReply = current =>{
    return axios.request({
        url: `admin/share/reply/`,
        params: {current,},
    })
}

const adminDeleteShareReply = id =>{
    return axios.request({
        url:`admin/share/reply/${id}/`,
        method:"delete",
    })
}

export default {
    post,
    listByShareId,
    deleteById,
    adminGetShareReply,
    adminDeleteShareReply,
}
