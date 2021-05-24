import MarkdownIt from "markdown-it"
import hljs from "highlight.js"

import mark from "markdown-it-mark"
import todo from "markdown-it-task-lists"
import sup from "markdown-it-sup"
import sub from "markdown-it-sub"
import emoji from "markdown-it-emoji"
import ins from "markdown-it-ins"
import footnote from "markdown-it-footnote"

const markdown = new MarkdownIt({
    html: false,
    xhtmlout: true,
    breaks: true,
    langPrefix: "language-",
    linkify: true,
    typographer: true,

    _highlight: true,

    highlight(str, lang) {
        if (lang && hljs.getLanguage(lang)) {
            try {
                const value = hljs.highlight(lang, str, true).value.replace(/\n/g, "<br />")
                return `<pre class="code-block"><code class="code hljs ${lang}">${value}</code></pre>`
            } catch (__) {}
        }
        return `<pre class="code-block"><code class="hljs">${markdown.utils.escapeHtml(str)}</code></pre>`
    }
})

markdown.use(mark).use(todo).use(sup).use(sub).use(emoji).use(ins).use(footnote)

export default markdown
