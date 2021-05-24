import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from '@/store'

import {
    Button,
    Icon,
    Modal,
    Form,
    FormModel,
    Input,
    Table,
    message,
    Dropdown,
    Menu,
    Pagination,
    Result,
    Tag,
    Upload,
    Skeleton,
    Divider,
} from 'ant-design-vue'

Vue.use(Button)
Vue.use(Icon)
Vue.use(Modal)
Vue.use(Form)
Vue.use(FormModel)
Vue.use(Input)
Vue.use(Table)
Vue.use(Dropdown)
Vue.use(Menu)
Vue.use(Pagination)
Vue.use(Result)
Vue.use(Tag)
Vue.use(Upload)
Vue.use(Skeleton)
Vue.use(Divider)

Vue.config.productionTip = false

message.config({
    duration: 1.5,
    maxCount: 3,
})

Vue.prototype.$success = message.success
Vue.prototype.$error = message.error
Vue.prototype.$warning = message.warning

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
