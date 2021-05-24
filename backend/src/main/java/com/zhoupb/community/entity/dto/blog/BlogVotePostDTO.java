package com.zhoupb.community.entity.dto.blog;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.blog.BlogVoteVo;
import com.zhoupb.validation.EnableValidator;
import com.zhoupb.validation.annotation.Required;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EnableValidator
public class BlogVotePostDTO implements BaseDTO<BlogVoteVo> {

	@Required(message = "blogId is null")
	private Integer blogId = null;
	
	@Required(message = "replyId is null")
	private Integer replyId = null;
	
	@Required(message = "'is up' is null")
	private Boolean isUp = null;
	
	private Integer userId = null;
	
	@Override
	public BlogVoteVo toVo() {
		return new BlogVoteVo(null, blogId, replyId, isUp, null, null, null, userId);
	}
	
}
