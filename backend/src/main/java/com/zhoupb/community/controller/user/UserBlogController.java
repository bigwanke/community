package com.zhoupb.community.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhoupb.community.annotation.LoginRequired;
import com.zhoupb.community.common.Common;
import com.zhoupb.community.common.JSONResponse;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.dto.user.UserBlogPostDTO;
import com.zhoupb.community.entity.dto.user.UserBlogPutDTO;
import com.zhoupb.community.entity.dto.user.UserBlogDTO;
import com.zhoupb.community.entity.dto.user.UserBlogEditDTO;
import com.zhoupb.community.entity.vo.blog.BlogVo;
import com.zhoupb.community.service.BlogService;
import com.zhoupb.validation.Validation;

/**
 * 
 * 用户中心的博客部分
 * 
 * @author zhoupb
 *
 */
@RestController
@RequestMapping("user/blog/")
public class UserBlogController {
	
	@Autowired
	private BlogService blogService = null;
	
	@PostMapping
	@Validation
	@LoginRequired
	public JSONResponse post(HttpServletRequest request, @RequestBody UserBlogPostDTO blog) {
		blog.setUserId((Integer) request.getAttribute(Common.REQ_USER_ID));
		
		Result<?> res = blogService.post(blog.toVo());
		if ( !res.isSuccess() )
			return JSONResponse.error(-1, res.getMessage());
		return JSONResponse.ok(res.getMessage());
	}
	
	@GetMapping
	public JSONResponse list(
			@RequestParam(required = false, defaultValue = Common.PAGE_CURRENT_DEFAULT) Integer current, 
			@RequestParam(required = false, defaultValue = Common.PAGE_SIZE_DEFAULT) Integer size, 
			@RequestParam Integer userId) {
		Result<PageData<BlogVo>> res = blogService.selectByUserId(userId, current, size);
		if ( !res.isSuccess() )
			return JSONResponse.error(-1, res.getMessage());
		
//		Result<UserProfileVo> userProfileRes = userService.selectUserProfileByPrimaryKey(userId);
//		if ( !userProfileRes.isSuccess() )
//			return JSONResponse.error(-1, userProfileRes.getMessage());
		
		List<UserBlogDTO> data = new ArrayList<>();
		
		PageData<BlogVo> page = res.getData();
		page.getData().forEach(e -> {
//			e.setUserProfile(userProfileRes.getData());
//			BlogDTO t = new BlogDTO();
			UserBlogDTO t = new UserBlogDTO();
			t.VoToMe(e);
			data.add(t);
		});
		
		return JSONResponse.ok(res.getMessage(), PageData.transformType(data, page));
	}
	
	@GetMapping("{id}/")
	@LoginRequired
	public JSONResponse get(HttpServletRequest request, @PathVariable int id) {
		Result<BlogVo> res = blogService.selectByPrimaryKey(id);
		if ( res.getData().getUserId() != (int) request.getAttribute(Common.REQ_USER_ID) )
			return JSONResponse.error(-1, "你似乎没有这篇文章");
		if ( !res.isSuccess() ) return JSONResponse.error(-1, res.getMessage());
		UserBlogEditDTO dto = new UserBlogEditDTO();
		dto.VoToMe(res.getData());
		return JSONResponse.ok(res.getMessage(), dto);
	}
	
	@PutMapping("{id}/")
	@LoginRequired
	@Validation
	public JSONResponse put(HttpServletRequest request, @RequestBody UserBlogPutDTO blog) {
		blog.setUserId((int) request.getAttribute(Common.REQ_USER_ID));
		Result<?> res = blogService.put(blog.toVo());
		if ( res.isSuccess() ) return JSONResponse.ok(res.getMessage());
		return JSONResponse.error(-1, res.getMessage());
	}
	
}
