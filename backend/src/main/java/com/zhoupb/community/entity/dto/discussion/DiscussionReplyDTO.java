package com.zhoupb.community.entity.dto.discussion;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.discussion.DiscussionReplyVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscussionReplyDTO implements BaseDTO<DiscussionReplyVo>{
	
	private Integer id = null;
	
	private String contentHtml = null;
	
	private Integer parentId = null; 
	
	private Integer voteCount = null;
	
	private LocalDateTime createTime = null;
	
	private LocalDateTime updateTime = null;
	
	private UserProfileDTO user  = null;
	
	private Boolean isUp = null;
	
	@Override
	public void VoToMe(DiscussionReplyVo vo) {
		this.contentHtml = vo.getContentHtml();
		this.createTime = vo.getCreateTime();
		this.updateTime = vo.getUpdateTime();
		this.id = vo.getId();
		this.parentId = vo.getParentId();
		this.voteCount = vo.getVoteCount();
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
	}
}
