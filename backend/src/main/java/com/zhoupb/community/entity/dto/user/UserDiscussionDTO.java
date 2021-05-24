package com.zhoupb.community.entity.dto.user;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.discussion.DiscussionReplyDTO;
import com.zhoupb.community.entity.vo.discussion.DiscussionVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDiscussionDTO implements BaseDTO<DiscussionVo>{
	
	private Integer id = null;
	
	private String contentHtml = null;
	
	private String title = null;
	
	private Integer readCount = null;
	
	private Integer voteCount = null;
	
	private Integer replyCount  = null;
	
	private LocalDateTime createTime = null;
	
	private LocalDateTime updateTime = null;
	
	private String description = null;
	
	private DiscussionReplyDTO reply = null;
	
	private UserProfileDTO user = null;
	
	@Override
	public void VoToMe(DiscussionVo vo) {
		if ( vo == null )
			return;
		
		this.id = vo.getId();
		this.contentHtml = vo.getContentHtml();
		this.readCount = vo.getReadCount();
		this.voteCount = vo.getVoteCount();
		this.replyCount = vo.getReplyCount();
		this.createTime = vo.getCreateTime();
		this.updateTime = vo.getUpdateTime();
		this.description = vo.getDescription();
		this.title = vo.getTitle();
		this.reply = new DiscussionReplyDTO();
		this.reply.VoToMe(vo.getReply());
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
	}
	
}
