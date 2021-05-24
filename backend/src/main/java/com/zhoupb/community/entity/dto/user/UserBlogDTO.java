package com.zhoupb.community.entity.dto.user;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.blog.BlogVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBlogDTO implements BaseDTO<BlogVo> {
	
	private Integer id = null;
	
	private String title = null;
	
	private String tags[] = null;
	
	private String contentHtml = null;
	
	private String coverImage = null;
	
	private String description = null;
	
	private Integer readCount = null;
	
	private Integer voteCount = null;
	
	private Integer replyCount = null;

	private LocalDateTime createTime = null;

	private LocalDateTime updateTime = null;
	
	@Override
	public void VoToMe(BlogVo vo) {
		if ( vo == null )
			return;
		
		this.id = vo.getId();
		this.title = vo.getTitle();
		this.tags = vo.getTags();
		this.contentHtml = vo.getContentHtml();
		this.coverImage = vo.getCoverImage();
		this.description = vo.getDescription();
		this.readCount = vo.getReadCount();
		this.voteCount = vo.getVoteCount();
		this.replyCount = vo.getReplyCount();
		this.createTime = vo.getCreateTime();
		this.updateTime = vo.getUpdateTime();
	}

}
