import axios from "@/api/axios"

const post = ({ title, tags, contentMarkdown, contentHtml }) => {
    return axios.request({
        url: "/user/problem/",
        method: "post",
        data: { title, tags, contentMarkdown, contentHtml, }
    })
}

const listByUserId = (current, size, userId) => {
    return axios.request({
        url: `/user/problem/${userId}/`,
        params: { current, size, },
    })
}

const list = () => {
    return axios.request({
        url: "/problem/",
    })
}

const getById = (id, userId) => {
    return axios.request({
        url: `/problem/${id}/`,
        params: { userId, },
    })
}

const adminGetProblem = current =>{
    return axios.request({
        url: `admin/problem/`,
        params: {current,},
    })
}

const adminDeleteProblem = id =>{
    return axios.request({
        url:`admin/problem/${id}/`,
        method:"delete",
    })
}

export default {
    post,
    listByUserId,
    list,
    getById,
    adminGetProblem,
    adminDeleteProblem,
}
