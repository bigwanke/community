import axios from "@/api/axios"

const baseURL = "/problem/answer/"

const post = ({ contentMarkdown, contentHtml, problemId }) => {
    return axios.request({
        url: baseURL,
        method: "post",
        data: { contentMarkdown, contentHtml, problemId },
    })
}

const listByProblemId = (problemId, userId) => {
    return axios.request({
        url: baseURL,
        params: { problemId, userId},
    })
}

const put = ({ id, problemId, isAccept, }) => {
    return axios.request({
        url: baseURL,
        method: "put",
        data: { id, problemId, isAccept, },
    })
}

const deleteById = id => {
    return axios.request({
        url: `${baseURL}${id}/`,
        method: "delete",
    })
}

const adminGetProblemAnswer = current =>{
    return axios.request({
        url: `admin/problem/answer/`,
        params: {current,},
    })
}

const adminDeleteProblemAnswer = id =>{
    return axios.request({
        url:`admin/problem/answer/${id}/`,
        method:"delete",
    })
}

export default {
    post,
    listByProblemId,
    put,
    deleteById,
    adminGetProblemAnswer,
    adminDeleteProblemAnswer,
}
