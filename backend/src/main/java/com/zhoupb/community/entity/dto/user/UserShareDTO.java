package com.zhoupb.community.entity.dto.user;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.share.ShareVo;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserShareDTO implements BaseDTO<ShareVo>{
	
	private Integer id = null;
	
	private String contentHtml = null;
	
	private String contentMarkdown = null;
	
	private Integer readCount = null;
	
	private Integer voteCount = null;
	
	private Integer	replyCount = null;
	
	private LocalDateTime createTime = null;
	
	private LocalDateTime updateTime = null;
	
	private String description = null;
	
	
	@Override
	public void VoToMe(ShareVo vo) {
		if ( vo == null )
			return;
		this.contentHtml = vo.getContentHtml();
		this.contentMarkdown = vo.getContentMarkdown();
		this.createTime = vo.getCreateTime();
		this.id = vo.getId();
		this.readCount = vo.getReadCount();
		this.replyCount = vo.getReplyCount();
		this.updateTime = vo.getUpdateTime();
		this.voteCount = vo.getVoteCount();
		this.description = vo.getDescription();
		
	}
}
