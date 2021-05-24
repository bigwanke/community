import axios from "@/api/axios"

const post = ({ shareId, replyId, isUp, }) => {
    return axios.request({
        url: "/share/vote/",
        method: "post",
        data: { shareId, replyId, isUp, },
    })
}

const adminGetShareVote = current =>{
    return axios.request({
        url: `admin/share/vote/`,
        params: {current,},
    })
}

const adminDeleteShareVote = id =>{
    return axios.request({
        url:`admin/share/vote/${id}/`,
        method:"delete",
    })
}

export default {
    post,
    adminGetShareVote,
    adminDeleteShareVote,
}
