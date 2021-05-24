import axios from "@/api/axios"

//-----------------用户----------------

//发share
const post = ({contentMarkdown, contentHtml,imgs }) => {
    return axios.request({
        url: "/user/share/",
        method: "post",
        data: { contentMarkdown, contentHtml,imgs }
    })
}

//查询
const userShares = userId => {
    return axios.request({
        url: `/user/share/`,
        params: { userId, },
    })
}
//查询
const userShareById = shareId => {
    return axios.request({
        url: `/user/share/${shareId}/`
    })
}

const updateShareById = ({ id, contentMarkdown, contentHtml }) => {
    return axios.request({
        url: `/user/share/${id}/`,
        method: "put",
        data: { id, contentMarkdown, contentHtml },
    })
}

//--------------------------------------
//获取全部圈子
const getShareList = () => {
    return axios.request({
        url: "/share/",
    })
}

//根据id
const getShareById = (id,userId) =>{
    return axios.request({
        url: `/share/${id}/`,
        params: {userId,},
    })
}

const adminGetShare = current =>{
    return axios.request({
        url: `admin/share/`,
        params: {current,},
    })
}

const adminDeleteShare = id =>{
    return axios.request({
        url:`admin/share/${id}/`,
        method:"delete",
    })
}

export default{
    post,
    userShares,
    getShareList,
    getShareById,
    updateShareById,
    userShareById,
    adminGetShare,
    adminDeleteShare,
}
