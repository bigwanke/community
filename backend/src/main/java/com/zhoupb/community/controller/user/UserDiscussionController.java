package com.zhoupb.community.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhoupb.community.annotation.LoginRequired;
import com.zhoupb.community.common.Common;
import com.zhoupb.community.common.JSONResponse;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.dto.user.UserDiscussionDTO;
import com.zhoupb.community.entity.dto.user.UserDiscussionPostDTO;
import com.zhoupb.community.entity.vo.discussion.DiscussionReplyVo;
import com.zhoupb.community.entity.vo.discussion.DiscussionVo;
import com.zhoupb.community.entity.vo.user.UserProfileVo;
import com.zhoupb.community.service.DiscussionReplyService;
import com.zhoupb.community.service.DiscussionService;
import com.zhoupb.community.service.UserService;
import com.zhoupb.validation.Validation;


@RestController
@RequestMapping("user/discussion/")
public class UserDiscussionController {
	
	@Autowired
	private DiscussionService discussionService = null;
	
	@Autowired
	private DiscussionReplyService discussionReplyService = null;
	
	@Autowired
	private UserService userService = null;
	
	//发布讨论
	@PostMapping
	@Validation
	@LoginRequired
	public JSONResponse post(HttpServletRequest request ,@RequestBody UserDiscussionPostDTO discussion) {
		discussion.setUserId((Integer) request.getAttribute(Common.REQ_USER_ID));
		Result<?> res = discussionService.post(discussion.toVo());
		if (!res.isSuccess()) {
			return JSONResponse.error(-1, res.getMessage());
		}
		return JSONResponse.ok(res.getMessage());
		
	}
	
	
	//根据userId查询
	@GetMapping("{userId}/")
	public JSONResponse list(@RequestParam(required = false, defaultValue = Common.PAGE_CURRENT_DEFAULT) Integer current, 
			@RequestParam(required = false, defaultValue = Common.PAGE_SIZE_DEFAULT) Integer size,
			@PathVariable Integer userId) {
		//查出结果集
		Result<PageData<DiscussionVo>> res = discussionService.selectByUserId(userId, current, size);
		if( !res.isSuccess()) {
			return JSONResponse.error(-1, res.getMessage());
		}
		//转类型vo-DTO
		List<UserDiscussionDTO> list = new ArrayList<>();
		PageData<DiscussionVo> page = res.getData();
		page.getData().forEach(e -> {
			
			UserDiscussionDTO userDiscussionDTO = new UserDiscussionDTO();
			
			Result<UserProfileVo> userProfileRes = userService.selectUserProfileByPrimaryKey(e.getUserId());
			e.setUserProfile(userProfileRes.getData());
			
			//修改最后发布的圈子评论
			if(e.getId() != null) {
				Result<DiscussionReplyVo> discussionReplyRes = discussionReplyService.getLastDiscussionReply(e.getId());
				if(discussionReplyRes.isSuccess() && discussionReplyRes.getData().getId() != null) {
					discussionService.updateLastReplyId(e.getId(), discussionReplyRes.getData().getId());
				}else {
					DiscussionReplyVo discussionReply = new DiscussionReplyVo();
					e.setReply(discussionReply);
				}
			}
			if(e.getLastReplyId() != null) {
				Result<DiscussionReplyVo> discussionReplyRes = discussionReplyService.getDiscussionByPrimaryKey(e.getLastReplyId());
				e.setReply(discussionReplyRes.getData());
			}else {
				DiscussionReplyVo discussionReply = new DiscussionReplyVo();
				e.setReply(discussionReply);
			}  
			
//			Result<DiscussionReplyVo> discussionReplyRes = discussionReplyService.getDiscussionByPrimaryKey(e.getLastReplyId());
//			e.setReply(discussionReplyRes.getData());
			
			userDiscussionDTO.VoToMe(e);
			list.add(userDiscussionDTO);
		});
		return JSONResponse.ok(res.getMessage(), PageData.transformType(list, page));
		
	}
}
