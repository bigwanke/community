import axios from "@/api/axios"

const register = ({ username, password, email }) => {
    return axios.request({
        url: "/user/register/",
        method: "post",
        data: { username, password, email },
    })
}

const login = ({ username, password }) => {
    return axios.request({
        url: "/user/login/",
        method: "post",
        data: { username, password },
    })
}

const logout = () => {
    return axios.request({
        url: "/user/logout/",
        method: "post",
    })
}

const getUser = () => {
    return axios.request({
        url: "/user/profile/",
    })
}

const getUserById = id => {
    return axios.request({
        url: `/user/${id}/`
    })
}

const getUserList = current => {
    return axios.request({
        url:`/admin/getList/`,
        params:{ current, },
    })
}

const adminDeleteUserById = id => {
    return axios.request({
        url:`/admin/user/delete/${id}/`,
        method: "delete"
    })
}

const adminUpdateUserById = id =>{
    return axios.request({
        url:`/admin/user/update/${id}/`

    })
}
export default {
    register,
    login,
    logout,
    getUser,
    getUserById,
    getUserList,
    adminDeleteUserById,
    adminUpdateUserById,
}
