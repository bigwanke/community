package com.zhoupb.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zhoupb.validation.ValidationAop;

@Configuration
public class Beans {

	@Bean
	public ValidationAop valdationAop() {
		return new ValidationAop();
	}

}
