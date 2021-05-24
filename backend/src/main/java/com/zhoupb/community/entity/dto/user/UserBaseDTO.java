package com.zhoupb.community.entity.dto.user;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.user.UserProfileVo;
import com.zhoupb.validation.annotation.MaxLength;
import com.zhoupb.validation.annotation.MinLength;
import com.zhoupb.validation.annotation.NotBlank;
import com.zhoupb.validation.annotation.Regex;
import com.zhoupb.validation.annotation.Required;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class UserBaseDTO implements BaseDTO<UserProfileVo> {
	
	@Required(message = "必须要输入用户名")
	@NotBlank(message = "用户名不能为空")
	@MinLength(min = 3, message = "用户名长度不能小于%d位")
	@MaxLength(max = 16, message = "用户名长度不能大于%d位")
	@Regex(regex = "[\u4e00-\u9fa5_a-zA-Z0-9]+", message = "用户名只能包含[中文、字母、数字、下划线]")
	private String username = null;
	
	@Required(message = "必须要输入密码")
	@NotBlank(message = "密码不能为空")
	@MinLength(min = 3, message = "密码长度不能小于%d位")
	@MaxLength(max = 16, message = "密码长度不能大于%d位")
	@Regex(regex = "[a-zA-Z0-9_\\.]+", message = "密码只能包含[字母、数字、下划线、小数点]")
	private String password = null;
	
	@Override
	public UserProfileVo toVo() {
		 return new UserProfileVo(null, username, password, null, null, null, null, null, null);
	}
	
}
