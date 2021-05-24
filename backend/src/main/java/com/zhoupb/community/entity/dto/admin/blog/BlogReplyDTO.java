package com.zhoupb.community.entity.dto.admin.blog;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.blog.BlogReplyVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogReplyDTO implements BaseDTO<BlogReplyVo>{
	
	private Integer id = null;
	
	private String contentMarkdown = null;
	
	private Integer voteCount = null;
	
	private Boolean deleted = null;
	
	private UserProfileDTO user = null;
	
	@Override
	public void VoToMe(BlogReplyVo vo) {
		this.id = vo.getId();
		this.contentMarkdown = vo.getContentMarkdown();
		this.voteCount = vo.getVoteCount();
		this.deleted = vo.getDeleted();
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
	}
}
