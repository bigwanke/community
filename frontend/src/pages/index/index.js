import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from '@/store'
import filters from "@/filters"

import {
    Button,
    Icon,
    Modal,
    FormModel,
    Input,
    message,
    Dropdown,
    Menu,
    Result,
    Tag,
    Upload,
    Skeleton,
    Divider,
} from 'ant-design-vue'

Vue.use(Button)
Vue.use(Icon)
Vue.use(Modal)
Vue.use(FormModel)
Vue.use(Input)
Vue.use(Dropdown)
Vue.use(Menu)
Vue.use(Result)
Vue.use(Tag)
Vue.use(Upload)
Vue.use(Skeleton)
Vue.use(Divider)

message.config({
    duration: 1.5,
    maxCount: 3,
})

const IconFont = Icon.createFromIconfontCN({
    scriptUrl: "//at.alicdn.com/t/font_2321510_8ufpcy4xxkf.js",
});

Vue.component("icon-font", IconFont)

Vue.prototype.$success = message.success
Vue.prototype.$error = message.error
Vue.prototype.$warning = message.warning

Vue.config.productionTip = false

Vue.filter("beautifulTime", filters.fromNow)
Vue.filter("dateFormat", filters.format)

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
