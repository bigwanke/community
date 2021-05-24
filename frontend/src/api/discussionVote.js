import axios from "@/api/axios"

const post = ({ discussionId, replyId, isUp, }) => {
    return axios.request({
        url: "/discussion/vote/",
        method: "post",
        data: { discussionId, replyId, isUp, },
    })
}

const adminGetDiscussionVote = current =>{
    return axios.request({
        url: `admin/discussion/vote/`,
        params: {current,},
    })
}

const adminDeleteDiscussionVote = id =>{
    return axios.request({
        url:`admin/discussion/vote/${id}/`,
        method:"delete",
    })
}

export default {
    post,
    adminGetDiscussionVote,
    adminDeleteDiscussionVote,
}
