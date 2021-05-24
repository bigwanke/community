import axios from "@/api/axios"

const BaseURL = "/blog/vote/"

const post = ({ blogId, replyId, isUp, }) => {
    return axios.request({
        url: BaseURL,
        method: "post",
        data: { blogId, replyId, isUp, },
    })
}

const adminGetBlogVote = current =>{
    return axios.request({
        url:`admin/blog/vote/`,
        params:{current , },
        
    })
}

const adminDeleteBlogVote = id =>{
    return axios.request({
        url:`admin/blog/vote/${id}/`,
        method:"delete",
    })
}

export default {
    post,
    adminGetBlogVote,
    adminDeleteBlogVote,
}
