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
import com.zhoupb.community.entity.dto.share.ShareDTO;
import com.zhoupb.community.entity.dto.user.UserShareEditDTO;
import com.zhoupb.community.entity.dto.user.UserSharePostDTO;
import com.zhoupb.community.entity.dto.user.UserSharePutDTO;
import com.zhoupb.community.entity.vo.share.ShareVo;
import com.zhoupb.community.entity.vo.user.UserProfileVo;
import com.zhoupb.community.service.ShareService;
import com.zhoupb.community.service.UserService;
import com.zhoupb.validation.Validation;

@RestController
@RequestMapping("user/share/")
public class UserShareController {
	
	@Autowired
	private ShareService shareService = null;
	
	@Autowired
	private UserService userService = null;
	
	//发布文章
	@PostMapping
	@Validation
	@LoginRequired
	public JSONResponse post(HttpServletRequest request ,@RequestBody UserSharePostDTO share) {
		share.setUserId((int) request.getAttribute(Common.REQ_USER_ID));
		
		Result<?> res = shareService.insertShare(share.toVo());
		if ( !res.isSuccess() )
			return JSONResponse.error(-1, res.getMessage());
		return JSONResponse.ok(res.getMessage());
	}
	
	
	//根据userId查询圈子列表（分页）
	@GetMapping
	public JSONResponse getUserShares(
			@RequestParam(required = false, defaultValue = Common.PAGE_CURRENT_DEFAULT) Integer current, 
			@RequestParam(required = false, defaultValue = Common.PAGE_SIZE_DEFAULT) Integer size, 
			@RequestParam Integer userId) {
		Result<PageData<ShareVo>> res = shareService.selectByUserId(userId, current, size);
		if(!res.isSuccess()) {
			return JSONResponse.error(-1, res.getMessage());
		}
		
		Result<UserProfileVo> userProfileRes = userService.selectUserProfileByPrimaryKey(userId);
		if ( !userProfileRes.isSuccess() ) {
			return JSONResponse.error(-1, userProfileRes.getMessage());
		}

		List<ShareDTO> list = new ArrayList<>();
		res.getData().getData().forEach(e -> {
			e.setUserProfile(userProfileRes.getData());
			ShareDTO t = new ShareDTO();
			t.VoToMe(e);
			list.add(t);
		});
		
		
		return JSONResponse.ok(res.getMessage(), PageData.transformType(list, res.getData()));
		
	}
	
	//修改
	@PutMapping("{id}/")
	@LoginRequired
	@Validation
	public JSONResponse put(HttpServletRequest request, @RequestBody UserSharePutDTO share) {
		share.setUserId((int) request.getAttribute(Common.REQ_USER_ID));
		Result<?> res = shareService.put(share.toVo());
		if ( res.isSuccess() ) return JSONResponse.ok(res.getMessage());
		return JSONResponse.error(-1, res.getMessage());
	}
	
	//根据id查
	@GetMapping("{id}/")
	@LoginRequired
	public JSONResponse get(HttpServletRequest request, @PathVariable int id) {
		Result<ShareVo> res = shareService.selectByPrimaryKey(id);
		if ( res.getData().getUserId() != (int) request.getAttribute(Common.REQ_USER_ID) )
			return JSONResponse.error(-1, "你似乎没有这篇文章");
		if ( !res.isSuccess() ) return JSONResponse.error(-1, res.getMessage());
		UserShareEditDTO dto = new UserShareEditDTO();
		dto.VoToMe(res.getData());
		return JSONResponse.ok(res.getMessage(), dto);
	}
}
