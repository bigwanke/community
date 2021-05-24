package com.zhoupb.community.controller.discussion;

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
import com.zhoupb.community.entity.dto.discussion.DiscussionVotePostDTO;
import com.zhoupb.community.service.DiscussionVoteService;
import com.zhoupb.validation.Validation;

@RestController
@RequestMapping("discussion/vote/")
public class DiscussionVoteController {
	
	@Autowired
	private DiscussionVoteService discussionVoteService = null;
	
	//点赞
	@PostMapping
	@LoginRequired
	@Validation
	public JSONResponse post(HttpServletRequest request, @RequestBody DiscussionVotePostDTO discussionVote) {
		discussionVote.setUserId((int)request.getAttribute(Common.REQ_USER_ID));
		Result<?> res = discussionVoteService.post(discussionVote.toVo());
		if (res.isSuccess()) {
			return JSONResponse.ok(res.getMessage(),res.getData());
		} 
		return JSONResponse.error(-1, res.getMessage());
		
	}
}
