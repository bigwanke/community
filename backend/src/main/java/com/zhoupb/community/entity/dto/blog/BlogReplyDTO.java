package com.zhoupb.community.entity.dto.blog;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.blog.BlogReplyVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogReplyDTO implements BaseDTO<BlogReplyVo> {

	private Integer id = null;
	
	private String contentHtml = null;
	
	private Integer parentId = null;
	
	private Integer voteCount = null;

	private LocalDateTime createTime = null;

	private UserProfileDTO user = null;
	
	private Boolean isUp = null;
	
	@Override
	public void VoToMe(BlogReplyVo vo) {
		this.id = vo.getId();
		this.contentHtml = vo.getContentHtml();
		this.parentId = vo.getParentId();
		this.voteCount = vo.getVoteCount();
		this.createTime = vo.getCreateTime();
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
	}
	
}
