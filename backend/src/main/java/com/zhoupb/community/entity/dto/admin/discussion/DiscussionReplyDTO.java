package com.zhoupb.community.entity.dto.admin.discussion;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.discussion.DiscussionReplyVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscussionReplyDTO implements BaseDTO<DiscussionReplyVo>{
	
	private Integer id = null;
	
	private String contentMarkdown = null;
	
	private Integer voteCount = null;
	
	private Boolean deleted = null;
	
	private UserProfileDTO user = null;
	
	@Override
	public void VoToMe(DiscussionReplyVo vo) {
		this.id = vo.getId();
		this.contentMarkdown = vo.getContentMarkdown();
		this.voteCount = vo.getVoteCount();
		this.deleted = vo.getDeleted();
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
	}
}
