package com.zhoupb.community.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhoupb.community.annotation.LoginRequired;
import com.zhoupb.community.common.Common;
import com.zhoupb.community.common.JSONResponse;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.user.UserProfileVo;
import com.zhoupb.community.service.UserService;

@RestController
@RequestMapping("user/profile/")
public class UserProfileController {

	@Autowired
	private UserService userService = null;
	
	@GetMapping
	@LoginRequired
	public JSONResponse profile(HttpServletRequest request) {
		int userId = (int) request.getAttribute(Common.REQ_USER_ID);
		
		Result<UserProfileVo> res = userService.selectUserProfileByPrimaryKey(userId);
		if ( !res.isSuccess() )
			return JSONResponse.error(-1, res.getMessage());
		
		UserProfileDTO userProfileDTO = new UserProfileDTO();
		userProfileDTO.VoToMe(res.getData());
		return JSONResponse.ok("获取成功", userProfileDTO);
	}
	
}
