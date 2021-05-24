package com.zhoupb.community.controller.blog;

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
import com.zhoupb.community.entity.dto.blog.BlogVotePostDTO;
import com.zhoupb.community.service.BlogVoteService;
import com.zhoupb.validation.Validation;

@RestController
@RequestMapping("blog/vote/")
public class BlogVoteController {

	@Autowired
	private BlogVoteService voteService = null;
	
	@PostMapping
	@LoginRequired
	@Validation
	public JSONResponse post(HttpServletRequest request, @RequestBody BlogVotePostDTO vote) {
		vote.setUserId((int) request.getAttribute(Common.REQ_USER_ID));
		Result<?> res = voteService.post(vote.toVo());
		if ( res.isSuccess() ) return JSONResponse.ok(res.getMessage(), res.getData());
		return JSONResponse.error(-1, res.getMessage());
	}
	
}
