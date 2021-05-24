package com.zhoupb.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zhoupb.community.common.Common;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins(Common.ALLOWED_HOST)
				.allowedMethods(Common.ALLOWED_METHOD)
				.allowCredentials(Common.ALLOW_CREDENTIALS)
				.maxAge(3600);
	}

}