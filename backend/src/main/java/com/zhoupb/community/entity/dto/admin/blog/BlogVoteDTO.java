package com.zhoupb.community.entity.dto.admin.blog;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.blog.BlogVoteVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogVoteDTO implements BaseDTO<BlogVoteVo>{
	
	private Integer id = null;
	
	private Integer blogId = null;
	
	private Integer replyId = null;
	
	private Boolean deleted = null;
	
	private UserProfileDTO user = null;
	
	@Override
	public void VoToMe(BlogVoteVo vo) {
		this.id = vo.getId();
		this.blogId = vo.getBlogId();
		this.replyId = vo.getReplyId();
		this.deleted = vo.getDeleted();
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
	}
	
}
