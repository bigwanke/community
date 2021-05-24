import moment from "moment"

moment.locale("zh-cn");

// 友好显示时间
function fromNow(date) {
    return moment(date).fromNow()
}

function format(date) {
    return moment(date).format("YYYY-MM-DD 	HH:mm:ss")
}

export default {
    fromNow,
    format,
}
