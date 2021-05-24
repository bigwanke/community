package com.zhoupb.community.entity.dto.admin.discussion;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.discussion.DiscussionVoteVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscussionVoteDTO implements BaseDTO<DiscussionVoteVo>{
	
	private Integer id = null;
	
	private Integer discussionId = null;
	
	private Integer replyId = null;
	
	private UserProfileDTO user = null;
	
	@Override
	public void VoToMe(DiscussionVoteVo vo) {
		this.id = vo.getId();
		this.discussionId = vo.getDiscussionId();
		this.replyId = vo.getReplyId();
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
	}
}
