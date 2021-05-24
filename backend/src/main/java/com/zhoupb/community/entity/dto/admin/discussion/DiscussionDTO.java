package com.zhoupb.community.entity.dto.admin.discussion;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.discussion.DiscussionVo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DiscussionDTO implements BaseDTO<DiscussionVo>{
	
	private Integer id = null;
	
	private String title = null;
	
	private String contentMarkdown = null;
	
	private Integer readCount = null;
	
	private Integer voteCount = null;
	
	private Integer replyCount = null;
	
	private Boolean deleted = null;
	
	private UserProfileDTO user = null;
	
	@Override
	public void VoToMe(DiscussionVo vo) {
		this.id = vo.getId();
		this.title = vo.getTitle();
		this.contentMarkdown = vo.getContentMarkdown();
		this.readCount = vo.getReadCount();
		this.voteCount = vo.getVoteCount();
		this.replyCount = vo.getReplyCount();
		this.deleted = vo.getDeleted();
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
	}
	
	
}
