package com.zhoupb.community.entity.dto.user;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.share.ShareVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserShareEditDTO implements BaseDTO<ShareVo>{
	
	private Integer id = null;
	
	private String contentMarkdown = null;
	
	@Override
	public void VoToMe(ShareVo vo) {
		if ( vo == null ) {
			return;	
		} 
		this.id = vo.getId();
		this.contentMarkdown = vo.getContentMarkdown();
	}
}
