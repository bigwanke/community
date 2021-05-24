package com.zhoupb.community.entity.dto.user;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.user.UserProfileVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileDTO implements BaseDTO<UserProfileVo> {

	private Integer id = null;

	private String username = null;

	private String email = null;

	private Boolean emailActive = null;

	private Boolean isAdmin = null;
	
	private ProfileDTO profile = null;
	
	@Override
	public void VoToMe(UserProfileVo vo) {
		if ( vo == null )
			return;
		
		this.id = vo.getId();
		this.username = vo.getUsername();
		this.email = vo.getEmail();
		this.emailActive = vo.getEmailActive();
		this.isAdmin = vo.getIsAdmin();
		this.profile = new ProfileDTO();
		this.profile.VoToMe(vo.getProfile());
	}
	
}
