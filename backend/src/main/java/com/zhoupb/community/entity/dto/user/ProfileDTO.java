package com.zhoupb.community.entity.dto.user;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.user.ProfileVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO implements BaseDTO<ProfileVo> {

	private Integer id = null;
	
	private String introduction = null;
	
	private String avatar = null;
	
	@Override
	public void VoToMe(ProfileVo vo) {
		if ( vo == null )
			return;
		
		this.id = vo.getId();
		this.introduction = vo.getIntroduction();
		this.avatar = vo.getAvatar();
	}
	
}
