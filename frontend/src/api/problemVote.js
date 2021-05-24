import axios from "@/api/axios"

const BaseURL = "/problem/vote/"

const post = ({ problemId, answerId, isUp, }) => {
    return axios.request({
        url: BaseURL,
        method: "post",
        data: { problemId, answerId, isUp, },
    })
}

const adminGetProblemVote = current =>{
    return axios.request({
        url: `admin/problem/vote/`,
        params: {current,},
    })
}

const adminDeleteProblemVote = id =>{
    return axios.request({
        url:`admin/problem/vote/${id}/`,
        method:"delete",
    })
}

export default {
    post,
    adminGetProblemVote,
    adminDeleteProblemVote,
}
