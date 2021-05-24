package com.zhoupb.community.controller.share;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhoupb.community.annotation.LoginRequired;
import com.zhoupb.community.common.Common;
import com.zhoupb.community.common.JSONResponse;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.dto.share.ShareVotePostDTO;
import com.zhoupb.community.service.ShareVoteService;
import com.zhoupb.validation.Validation;

@RestController
@RequestMapping("share/vote/")
public class ShareVoteController {
	
	@Autowired
	private ShareVoteService shareVoteService = null; 
	
	@PostMapping
	@LoginRequired
	@Validation
	public JSONResponse post(HttpServletRequest request, @RequestBody ShareVotePostDTO shareVote) {
		
		shareVote.setUserId((int) request.getAttribute(Common.REQ_USER_ID));
		Result<?> res = shareVoteService.post(shareVote.toVo());
		if(res.isSuccess()) {
			System.err.println(res.getData());
			return JSONResponse.ok(res.getMessage(),res.getData());
		}
		return JSONResponse.error(-1, res.getMessage());
		
	}
	
}
   