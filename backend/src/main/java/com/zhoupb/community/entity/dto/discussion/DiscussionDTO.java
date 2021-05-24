package com.zhoupb.community.entity.dto.discussion;


import java.time.LocalDateTime;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.discussion.DiscussionVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscussionDTO implements BaseDTO<DiscussionVo>{
	
	private Integer id = null;
	
	private String contentHtml = null;
	
	private String contentMarkdown = null;
	
	private Integer readCount = null;
	
	private Integer voteCount = null;
	
	private Integer replyCount = null;
	
	private LocalDateTime createTime = null;
	
	private LocalDateTime updateTime = null;
	
	private UserProfileDTO user = null;
	
	private Boolean isUp = null;
	
	private DiscussionReplyDTO reply = null;
	
	private String title = null;
	
	@Override
	public void VoToMe(DiscussionVo vo) {
		if ( vo == null )
			return;
		this.contentHtml = vo.getContentHtml();
		this.contentMarkdown = vo.getContentMarkdown();
		this.createTime = vo.getCreateTime();
		this.id = vo.getId();
		this.readCount = vo.getReadCount();
		this.replyCount = vo.getReplyCount();
		this.updateTime = vo.getUpdateTime();
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
		this.voteCount = vo.getVoteCount();
		this.reply = new DiscussionReplyDTO();
		this.reply.VoToMe(vo.getReply());
		this.title = vo.getTitle();
	}
	
}
