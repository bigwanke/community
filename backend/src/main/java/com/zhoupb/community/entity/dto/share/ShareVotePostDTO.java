package com.zhoupb.community.entity.dto.share;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.share.ShareVoteVo;
import com.zhoupb.validation.EnableValidator;
import com.zhoupb.validation.annotation.Required;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EnableValidator
public class ShareVotePostDTO implements BaseDTO<ShareVoteVo>{
	
	@Required(message = "'shareId' is null")
	private Integer shareId = null;
	
	@Required(message = "'replyId' is null")
	private Integer replyId = null;
	
	private Integer userId = null;
	
	@Required(message = "'isUp' is null")
	private Boolean isUp = null;
	
	@Override
	public ShareVoteVo toVo() {
		return new ShareVoteVo(null, shareId, replyId, isUp, null, null, userId, null);
	}
}
