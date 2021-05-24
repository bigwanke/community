import Vue from 'vue'
import axios from "axios"
import config from "@/config"
import store from "@/store"

const baseURL = process.env.NODE_ENV === "development" ? config.baseURL.development : config.baseURL.product

axios.defaults.withCredentials = true

class HttpRequest {
    constructor(baseURL) {
        this.baseURL = baseURL
    }

    getInsideConfig() {
        return {
            baseURL: this.baseURL,
            method: "get",
        }
    }

    interceptors(instance) {
        instance.interceptors.request.use(config => {
            return config
        })
        instance.interceptors.response.use(res => {
            // 验证401未认证
            if ( res.data.code == 401 ) {
                //  未认证，清除已有的vuex中的值
                store.commit("user/initUser")
                // if ( res.config.url != "/user/profile/" )
                //     Vue.prototype.$warning(res.data.message)
            }
            return res.data
        }, error => {
            // Vue.prototype.$error("请求失败.axios")
            return {
                code: -999,
                message: "请求失败",
            }
        })
    }

    request(options) {
        const instance = axios.create()
        options = Object.assign(this.getInsideConfig(), options)
        this.interceptors(instance)
        return instance(options)
    }
}

const axiosObj = new HttpRequest(baseURL)
export default axiosObj
