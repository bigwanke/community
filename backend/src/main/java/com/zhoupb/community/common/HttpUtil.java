package com.zhoupb.community.common;

import java.util.Map;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class HttpUtil {

	private static final RestTemplate http = new RestTemplate();
	
	static {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(5000);
		factory.setConnectTimeout(5000);
		http.setRequestFactory(factory);
	}
	
	public static Result<String> getGenerateImageURL(String str) {
		if ( StringUtil.isEmpty(str) ) return Result.success("str is empty", "https://api.zhoupb.com/image/bing/?" + System.currentTimeMillis());
		String url = "https://api.zhoupb.com/image/generate/?str=" + str;
		String res = http.getForObject(url, String.class);
		Map<String, Object> json = new JacksonJsonParser().parseMap(res);
		if ( json == null )
			return Result.fail("未接收到数据");
		if ( (int) json.get("code") != 200 )
			return Result.success((String) json.get("message"), "https://api.zhoupb.com/image/bing/?" + System.currentTimeMillis());
		return Result.success((String) json.get("message"), (String) json.get("data"));
	}
	
}
