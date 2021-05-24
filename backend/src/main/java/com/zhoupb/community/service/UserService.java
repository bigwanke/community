package com.zhoupb.community.service;

import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.vo.user.UserProfileVo;

public interface UserService {
	
	public Result<?> register(UserProfileVo user);

	public Result<String> login(UserProfileVo user);
	
	public Result<UserProfileVo> selectUserProfileByPrimaryKey(int id);
	
	public Result<PageData<UserProfileVo>> adminSelectUserList(long current ,long size );
	
	public Result<?> adminDeleteUserById(int id);
	
	public Result<?> adminUpdateUserById(int id);
}
