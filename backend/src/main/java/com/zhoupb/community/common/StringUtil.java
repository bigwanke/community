package com.zhoupb.community.common;

public class StringUtil {

	public static boolean isEmpty(CharSequence str) {
		if ( str == null )
			return true;
		if ( str.length() == 0 )
			return true;
		return false;
	}
	
	/**
	 * 提取出HTML中的文字内容
	 * @param html
	 * @return
	 */
	public static String html2Str(String html) {
		return html.replaceAll("<[^<>]+>|\\s", "");
	}
	
	public static void _main(String[] args) {
		String str = "<h1>第一篇文章</h1>\r\n"
				+ "<h2>测试一下代码</h2>\r\n"
				+ "<pre class=\"code-block\"><code class=\"code hljs java\">	<span class=\"hljs-meta\">@PostMapping(&quot;post/&quot;)</span><br />	<span class=\"hljs-meta\">@Validation</span><br />	<span class=\"hljs-meta\">@LoginRequired</span><br />	<span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> JSONResponse <span class=\"hljs-title\">post</span><span class=\"hljs-params\">(HttpServletRequest request ,<span class=\"hljs-meta\">@RequestBody</span> BlogPostDTO blog)</span> </span>{<br />		<span class=\"hljs-meta\">@SuppressWarnings(&quot;unchecked&quot;)</span><br />		Map&lt;String, Object&gt; data = (Map&lt;String, Object&gt;)request.getAttribute(Common.AUTHORIZED_DATA_KEY);<br />		<br />		blog.setUserId((Integer) data.get(Common.JWT_PAYLOAD_USER_ID_KEY));<br />		<br />		Result&lt;?&gt; res = blogService.post(blog.toPOJO());<br />		<span class=\"hljs-keyword\">if</span> ( res.isSuccess() )<br />			<span class=\"hljs-keyword\">return</span> JSONResponse.ok(res.getMessage());<br />		<span class=\"hljs-keyword\">return</span> JSONResponse.error(-<span class=\"hljs-number\">1</span>, res.getMessage());<br />	}<br /></code></pre>\r\n"
				+ "<h2>测试一下&lt;script&gt;标签</h2>\r\n"
				+ "<p>&lt;script&gt;<br>\r\n"
				+ "alert(123)<br>\r\n"
				+ "&lt;/script&gt;</p>\r\n"
				+ "<h2>end</h2>\r\n"
				+ "<p>结束了</p>\r\n"
				+ "";
		System.err.println(html2Str(str));
	}
	
}
