package com.zhoupb.community.entity.dto.discussion;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.discussion.DiscussionVoteVo;
import com.zhoupb.validation.EnableValidator;
import com.zhoupb.validation.annotation.Required;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EnableValidator
public class DiscussionVotePostDTO implements BaseDTO<DiscussionVoteVo>{
	
	@Required(message = "discussionId is null")
	private Integer discussionId = null;
	
	@Required(message = "replyId is null")
	private Integer replyId = null; 
	
	@Required(message = "'isUp' is null")
	private Boolean isUp = null;
	
	private Integer userId = null;
	
	@Override
	public DiscussionVoteVo toVo() {
		return new DiscussionVoteVo(null, discussionId, replyId, isUp, null, null, userId);
	}
}
