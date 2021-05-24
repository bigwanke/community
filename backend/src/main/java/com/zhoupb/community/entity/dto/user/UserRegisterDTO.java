package com.zhoupb.community.entity.dto.user;

import com.zhoupb.community.entity.vo.user.UserProfileVo;
import com.zhoupb.validation.EnableValidator;
import com.zhoupb.validation.annotation.Regex;
import com.zhoupb.validation.annotation.Required;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EnableValidator
public class UserRegisterDTO extends UserBaseDTO {
	
	@Required(message = "请输入邮箱地址")
	@Regex(regex = "^([a-zA-Z]|[0-9])(\\w|\\-)+@[a-zA-Z0-9]+\\.([a-zA-Z]{2,4})$", message = "请输入正确的邮箱地址")
	private String email = null;
	
	@Override
	public UserProfileVo toVo() {
		return new UserProfileVo(null, this.getUsername(), this.getPassword(), this.getEmail(), null, null, null, null, null);
	}
	
}
