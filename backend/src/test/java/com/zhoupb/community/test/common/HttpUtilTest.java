package com.zhoupb.community.test.common;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.zhoupb.community.common.HttpUtil;
import com.zhoupb.community.common.Result;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class HttpUtilTest {

//	@Test
	public void getGenerateImageURLSuccess() {
		Result<String> res = HttpUtil.getGenerateImageURL("测试一下");
		Assert.assertEquals(true, res.isSuccess());
	}
	
	@Test
	public void getGenerateImageURLError() {
		Result<String> res = HttpUtil.getGenerateImageURL("ssss");
		Assert.assertEquals(true, res.isSuccess());
	}
	
}
