package com.zhoupb.community.controller.discussion;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.zhoupb.community.entity.dto.discussion.DiscussionReplyDTO;
import com.zhoupb.community.entity.dto.discussion.DiscussionReplyPostDTO;
import com.zhoupb.community.entity.vo.discussion.DiscussionReplyVo;
import com.zhoupb.community.entity.vo.discussion.DiscussionVo;
import com.zhoupb.community.entity.vo.discussion.DiscussionVoteVo;
import com.zhoupb.community.service.DiscussionReplyService;
import com.zhoupb.community.service.DiscussionService;
import com.zhoupb.community.service.DiscussionVoteService;
import com.zhoupb.validation.Validation;

@RestController
@RequestMapping("discussion/reply/")
public class DiscussionReplyController {
	
	@Autowired
	private DiscussionReplyService discussionReplyService = null;
	
	@Autowired
	private DiscussionVoteService discussionVoteService = null;
	
	@Autowired
	private DiscussionService discussionService = null;
	
	//写评论
	@PostMapping
	@LoginRequired
	@Validation
	public JSONResponse post(HttpServletRequest request, @RequestBody DiscussionReplyPostDTO reply) {
		reply.setUserId((int) request.getAttribute(Common.REQ_USER_ID));
		Result<?> res = discussionReplyService.post(reply.toVo());
		if (res.isSuccess()) {
			return JSONResponse.ok(res.getMessage());
		} 
		return JSONResponse.error(-1, res.getMessage());
	}
	
	//查评论
	@GetMapping
	public JSONResponse selectList(@RequestParam Integer discussionId,@RequestParam(required = false) Integer userId,
			@RequestParam(defaultValue = Common.PAGE_CURRENT_DEFAULT) Integer current,
			@RequestParam(defaultValue = Common.PAGE_SIZE_DEFAULT) Integer size) {
		
		Result<PageData<DiscussionReplyVo>> res = discussionReplyService.listByDiscussionId(discussionId, current, size);
		if (!res.isSuccess()) {
			return JSONResponse.error(-1, res.getMessage());
		} 
		
		List<DiscussionReplyDTO> list = new ArrayList<DiscussionReplyDTO>();
		List<Integer> ids = new ArrayList<>();
		
		res.getData().getData().forEach(e -> {
			//修改最后发布的圈子评论
			Result<DiscussionVo> discussion = discussionService.selectByPrimaryKey(e.getDiscussionId());
			if(e.getDiscussionId() != null) {
				Result<DiscussionReplyVo> discussionReplyRes = discussionReplyService.getLastDiscussionReply(e.getDiscussionId());
				if(discussionReplyRes.isSuccess() && discussionReplyRes.getData().getId() != null) {
					discussionService.updateLastReplyId(e.getDiscussionId(), discussionReplyRes.getData().getId());
				}else {
					DiscussionReplyVo discussionReply = new DiscussionReplyVo();
					discussion.getData().setReply(discussionReply);
				}
				 
			}

			if(discussion.getData().getLastReplyId() != null) {
				Result<DiscussionReplyVo> discussionReplyRes = discussionReplyService.getDiscussionByPrimaryKey(discussion.getData().getLastReplyId());
				discussion.getData().setReply(discussionReplyRes.getData());
			}else {
				DiscussionReplyVo discussionReply = new DiscussionReplyVo();
				discussion.getData().setReply(discussionReply); 
			}
			DiscussionReplyDTO t = new DiscussionReplyDTO();
			t.VoToMe(e); 
			list.add(t);
			
			ids.add(e.getId());
		});
		
		if (userId != null) {
			Result<List<DiscussionVoteVo>> discussionVoteRes = discussionVoteService.listByReplyIdsAndUserId(ids, userId);
			if (discussionVoteRes.isSuccess())
				list.forEach(e -> {
					DiscussionVoteVo t = null;
					if ( ( t = isContains(discussionVoteRes.getData(), e.getId()) ) != null )
						e.setIsUp(t.getIsUp());
				});
		}
		
		return JSONResponse.ok(null, PageData.transformType(list, res.getData()));
	}
	
	private DiscussionVoteVo isContains(List<DiscussionVoteVo> list, int discussionReplyId) {
		for (DiscussionVoteVo t : list) {
			if ( t.getReplyId() == discussionReplyId ) {
				return t;
			} 
		}
			
		return null;
	}
	
	//删除
	@DeleteMapping("{id}/")
	@LoginRequired
	public JSONResponse delete(HttpServletRequest request, @PathVariable int id) {
		//获取这个评论的讨论信息
		Result<DiscussionReplyVo> dicussionRply = discussionReplyService.getDiscussionByPrimaryKey(id);
		Result<DiscussionVo> discussion = discussionService.selectByPrimaryKey(dicussionRply.getData().getDiscussionId());
		//删除
		Result<?> res = discussionReplyService.deleteByPrimaryKeyAndUserId(id, (int) request.getAttribute(Common.REQ_USER_ID));
		//修改最后发布的圈子评论

		if(dicussionRply.getData().getDiscussionId() != null) {
			Result<DiscussionReplyVo> discussionReplyRes = discussionReplyService.getLastDiscussionReply(dicussionRply.getData().getDiscussionId());
			if(discussionReplyRes.isSuccess() && discussionReplyRes.getData().getId() != null) {
				discussionService.updateLastReplyId(dicussionRply.getData().getDiscussionId(), discussionReplyRes.getData().getId());
			}
			else {
				DiscussionReplyVo discussionReply = new DiscussionReplyVo();
				discussion.getData().setReply(discussionReply);
			}
			 
		}

		if(discussion.getData().getLastReplyId() != null) {
			Result<DiscussionReplyVo> discussionReplyRes = discussionReplyService.getDiscussionByPrimaryKey(discussion.getData().getLastReplyId());
			discussion.getData().setReply(discussionReplyRes.getData());
		}else {
			DiscussionReplyVo discussionReply = new DiscussionReplyVo();
			discussion.getData().setReply(discussionReply); 
		}
		if ( res.isSuccess() ) return JSONResponse.ok(res.getMessage());
		return JSONResponse.error(-1, res.getMessage());
	}
}
