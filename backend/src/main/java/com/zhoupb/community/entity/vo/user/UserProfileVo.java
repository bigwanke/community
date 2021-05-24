package com.zhoupb.community.entity.vo.user;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.pojo.User;
import com.zhoupb.community.entity.vo.BaseVo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileVo extends User implements BaseVo<User> {

	private ProfileVo profile = null;
	
	public UserProfileVo(Integer id, String username, String password, String email, Boolean emailActive, Boolean isAdmin,
			Boolean deleted, LocalDateTime createTime, LocalDateTime updateTime) {
		super(id, username, password, email, emailActive, isAdmin, deleted, createTime, updateTime);
	}
	
	@Override
	public User toPOJO() {
		return (User) this;
	}
	
}
