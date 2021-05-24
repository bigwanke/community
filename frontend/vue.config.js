module.exports = {
    css: {
        loaderOptions: {
            less: {
                javascriptEnabled: true,
            }
        }
    },
    pages: {
        index: {
            // page 的入口
            entry: 'src/pages/index/index.js',
            // 模板来源
            template: 'src/pages/index/index.html',
            // 在 dist/index.html 的输出
            filename: 'index.html',
        },
        admin: {
            // page 的入口
            entry: 'src/pages/admin/index.js',
            // 模板来源
            template: 'src/pages/admin/index.html',
            // 在 dist/index.html 的输出
            filename: 'admin.html',
        },
    }
}
