package com.zhoupb.community.entity.dto.discussion;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.discussion.DiscussionReplyVo;
import com.zhoupb.validation.EnableValidator;
import com.zhoupb.validation.annotation.NotBlank;
import com.zhoupb.validation.annotation.Required;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EnableValidator
public class DiscussionReplyPostDTO implements BaseDTO<DiscussionReplyVo>{
	
	@Required(message = "必须要填写内容哦. m")
	@NotBlank(message = "内容不能为空哦. m")
	private String contentHtml = null;
	
	@Required(message = "必须要填写内容哦. m")
	@NotBlank(message = "内容不能为空哦. m")
	private String contentMarkdown = null;
	
	@Required(message = "parentId is null.")
	private Integer parentId = null; 
	
	private Integer userId = null;
	
	@Required(message = "discussionId is null.")
	private Integer discussionId = null;
	
	@Override
	public DiscussionReplyVo toVo() {
		return new DiscussionReplyVo(null, contentHtml, contentMarkdown, parentId, null, null, null, null, userId, discussionId);
	}
}
