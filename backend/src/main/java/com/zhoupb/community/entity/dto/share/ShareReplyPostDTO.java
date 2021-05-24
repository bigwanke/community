package com.zhoupb.community.entity.dto.share;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.share.ShareReplyVo;
import com.zhoupb.validation.EnableValidator;
import com.zhoupb.validation.annotation.NotBlank;
import com.zhoupb.validation.annotation.Required;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EnableValidator
public class ShareReplyPostDTO implements BaseDTO<ShareReplyVo>{
	
	@Required(message = "必须要填写内容哦. m")
	@NotBlank(message = "内容不能为空哦. m")
	private String contentMarkdown = null;
	
	@Required(message = "必须要填写内容哦. h")
	@NotBlank(message = "内容不能为空哦. h")
	private String contentHtml = null;
	
	@Required(message = "parentId is null.")
	private Integer parentId = null;
	
	@Required(message = "shareId is null")
	private Integer shareId = null;
	
	private Integer userId = null;
	
	@Override
	public ShareReplyVo toVo() {
		return new ShareReplyVo(null, contentMarkdown, contentHtml, parentId, null, null, null, null, userId, shareId);
	}

	
	
}
