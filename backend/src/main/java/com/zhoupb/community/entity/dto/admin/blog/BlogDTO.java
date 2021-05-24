package com.zhoupb.community.entity.dto.admin.blog;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.blog.BlogVo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlogDTO implements BaseDTO<BlogVo>{

	private Integer id = null;
	
	private String title = null;
	
	private String tags[] = null;
	
	private Integer readCount = null;
	
	private Integer voteCount = null;
	
	private Integer replyCount = null;
	
	private Boolean deleted = null;
	
	private UserProfileDTO user = null;
	
	@Override
	public void VoToMe(BlogVo vo) {
		if ( vo == null )
			return;
		
		this.id = vo.getId();
		this.title = vo.getTitle();
		this.tags = vo.getTags();
		this.readCount = vo.getReadCount();
		this.voteCount = vo.getVoteCount();
		this.replyCount = vo.getReplyCount();
		this.deleted = vo.getDeleted();
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
	}
}
