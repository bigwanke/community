package com.zhoupb.community.entity.dto.user;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.blog.BlogVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBlogEditDTO implements BaseDTO<BlogVo> {

	private Integer id = null;
	
	private String title = null;
	
	private String tags[] = null;
	
	private String contentMarkdown = null;
	
	@Override
	public void VoToMe(BlogVo vo) {
		if ( vo == null ) return;
		
		this.id = vo.getId();
		this.title = vo.getTitle();
		this.tags = vo.getTags();
		this.contentMarkdown = vo.getContentMarkdown();
	}
	
}
