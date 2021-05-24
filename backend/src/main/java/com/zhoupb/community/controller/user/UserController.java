package com.zhoupb.community.controller.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhoupb.community.annotation.LoginRequired;
import com.zhoupb.community.common.Common;
import com.zhoupb.community.common.JSONResponse;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.dto.user.UserLoginDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.dto.user.UserRegisterDTO;
import com.zhoupb.community.entity.vo.user.UserProfileVo;
import com.zhoupb.community.service.UserService;
import com.zhoupb.validation.Validation;

@RestController
@RequestMapping("user/")
public class UserController {

	@Autowired
	private UserService userService = null;
	
	@PostMapping("register/")
	@Validation
	public JSONResponse register(@RequestBody UserRegisterDTO user) {
		Result<?> res = userService.register(user.toVo());
		if ( res.isSuccess() )
			return JSONResponse.ok(res.getMessage());
		return JSONResponse.error(-1, res.getMessage());
	}
	
	@PostMapping("login/")
	@Validation
	public JSONResponse login(HttpServletResponse response, @RequestBody UserLoginDTO user) {
		Result<String> res = userService.login(user.toVo());
		if ( res.isSuccess() ) {
			Cookie cookie = new Cookie(Common.COOKIE_KEY, res.getData());
			cookie.setMaxAge(Common.AUTHORIZATION_EXPIRED_TIME);
			cookie.setPath("/");
			
			response.addCookie(cookie);
			
			return JSONResponse.ok(res.getMessage());
		}
		return JSONResponse.error(-1, res.getMessage());
	}
	
	@PostMapping("logout/")
	@LoginRequired
	public JSONResponse logout(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookies[] = request.getCookies();
		for (Cookie cookie : cookies)
			if ( cookie.getName().equals(Common.COOKIE_KEY) ) {
				cookie.setMaxAge(0);
				cookie.setPath("/");
				
				response.addCookie(cookie);
				
				return JSONResponse.ok("再见啦. 🤪");
			}
		return JSONResponse.error(-1, "退出失败");
	}
	
	@GetMapping("{id}/")
	public JSONResponse get(@PathVariable int id) {
		Result<UserProfileVo> res = userService.selectUserProfileByPrimaryKey(id);
		if ( !res.isSuccess() ) 
			return JSONResponse.error(-1, res.getMessage());
		UserProfileDTO userProfileDTO = new UserProfileDTO();
		userProfileDTO.VoToMe(res.getData());
		return JSONResponse.ok(res.getMessage(), userProfileDTO);
	}
	
}
