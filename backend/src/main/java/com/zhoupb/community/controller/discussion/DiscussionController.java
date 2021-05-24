package com.zhoupb.community.controller.discussion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhoupb.community.common.JSONResponse;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.dto.discussion.DiscussionDTO;
import com.zhoupb.community.entity.vo.discussion.DiscussionReplyVo;
import com.zhoupb.community.entity.vo.discussion.DiscussionVo;
import com.zhoupb.community.entity.vo.discussion.DiscussionVoteVo;
import com.zhoupb.community.entity.vo.user.UserProfileVo;
import com.zhoupb.community.service.DiscussionReplyService;
import com.zhoupb.community.service.DiscussionService;
import com.zhoupb.community.service.DiscussionVoteService;
import com.zhoupb.community.service.UserService;

@RestController
@RequestMapping("discussion/")
public class DiscussionController {
	
	@Autowired
	private DiscussionService discussionService = null;
	
	@Autowired
	private UserService userService = null;
	
	@Autowired
	private DiscussionVoteService discussionVoteService = null;
	
	@Autowired
	private DiscussionReplyService discussionReplyService = null;
	
	//根据id查
	@GetMapping("{id}/")
	public JSONResponse get(@PathVariable int id, @RequestParam(required = false) Integer userId) {
		Result<DiscussionVo> res = discussionService.selectByPrimaryKey(id);
		if (!res.isSuccess()) {
			return JSONResponse.error(-1, res.getMessage());
		}
		Result<UserProfileVo> userProfileRes = userService.selectUserProfileByPrimaryKey(res.getData().getUserId());
		if (!userProfileRes.isSuccess()) {
			return JSONResponse.error(-1, userProfileRes.getMessage());
		}


		// 添加浏览记录
		discussionService.addReadCountByPrimaryKey(id);
		res.getData().setReadCount(res.getData().getReadCount() + 1);
		
		res.getData().setUserProfile(userProfileRes.getData());
		DiscussionDTO discussionDTO = new DiscussionDTO();
		
//		Result<DiscussionReplyVo> discussionReplyRes = discussionReplyService.getDiscussionByPrimaryKey(res.getData().getLastReplyId());
//		res.getData().setReply(discussionReplyRes.getData());
		
		if(res.getData().getLastReplyId() != null) {
			Result<DiscussionReplyVo> discussionReplyRes = discussionReplyService.getDiscussionByPrimaryKey(res.getData().getLastReplyId());
			res.getData().setReply(discussionReplyRes.getData());
		}else {
			DiscussionReplyVo discussionReply = new DiscussionReplyVo();
			res.getData().setReply(discussionReply);
		} 
		
		discussionDTO.VoToMe(res.getData());
		
		if ( userId != null ) {
			Result<DiscussionVoteVo> discussionVoteRes = discussionVoteService.getByDiscussionIdAndUserId(id, userId);
			if ( discussionVoteRes.isSuccess() && discussionDTO.getId() == discussionVoteRes.getData().getDiscussionId() )
				discussionDTO.setIsUp(discussionVoteRes.getData().getIsUp());
		}
		
		return JSONResponse.ok(res.getMessage(), discussionDTO);
	}
	
	//查所有
	@GetMapping
	public JSONResponse list(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "20") Integer size) {
		Result<PageData<DiscussionVo>> res = discussionService.selectList(current, size);
		
		List<DiscussionDTO> list = new ArrayList<>();
		res.getData().getData().forEach(e -> {
			
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

			DiscussionDTO dto = new DiscussionDTO();
			dto.VoToMe(e);
			list.add(dto);
		});
		
		return JSONResponse.ok(null, PageData.transformType(list, res.getData()));
	}
	
}
