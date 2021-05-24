package com.zhoupb.community.entity.dto.user;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.discussion.DiscussionVo;
import com.zhoupb.validation.EnableValidator;
import com.zhoupb.validation.annotation.NotBlank;
import com.zhoupb.validation.annotation.Required;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EnableValidator
public class UserDiscussionPostDTO implements BaseDTO<DiscussionVo>{
	
	@Required(message = "必须要输入标题")
	@NotBlank(message = "标题不能为空")
	private String title = null;

	@Required(message = "必须要填写内容哦. h")
	@NotBlank(message = "内容不能为空哦. h")
	private String contentHtml = null;
	
	@Required(message = "必须要填写内容哦. m")
	@NotBlank(message = "内容不能为空哦. m")
	private String contentMarkdown = null;
	
	private Integer userId = null;

	@Override
	public DiscussionVo toVo() {
		return new DiscussionVo(null, contentHtml, contentMarkdown, null, null, null, null, null, null, userId, title, null, null);
	}
}
