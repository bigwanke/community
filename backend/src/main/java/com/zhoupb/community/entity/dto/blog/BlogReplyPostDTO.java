package com.zhoupb.community.entity.dto.blog;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.blog.BlogReplyVo;
import com.zhoupb.validation.EnableValidator;
import com.zhoupb.validation.annotation.NotBlank;
import com.zhoupb.validation.annotation.Required;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EnableValidator
public class BlogReplyPostDTO implements BaseDTO<BlogReplyVo> {

	@Required(message = "必须要填写内容哦. m")
	@NotBlank(message = "内容不能为空哦. m")
	private String contentMarkdown = null;
	
	@Required(message = "必须要填写内容哦. h")
	@NotBlank(message = "内容不能为空哦. h")
	private String contentHtml = null;
	
	@Required(message = "parentId is null.")
	private Integer parentId = null;
	
	private Integer userId = null;
	
	@Required(message = "blogId is null")
	private Integer blogId = null;
	
	@Override
	public BlogReplyVo toVo() {
		return new BlogReplyVo(null, contentMarkdown, contentHtml, parentId, null, null, null, null, userId, blogId);
	}
	
}
