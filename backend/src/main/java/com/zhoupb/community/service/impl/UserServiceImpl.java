package com.zhoupb.community.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhoupb.community.common.BCrypt;
import com.zhoupb.community.common.Common;
import com.zhoupb.community.common.JWTUtil;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.pojo.Profile;
import com.zhoupb.community.entity.pojo.User;
import com.zhoupb.community.entity.vo.user.UserProfileVo;
import com.zhoupb.community.exception.SaveToDataBaseException;
import com.zhoupb.community.mapper.ProfileMapper;
import com.zhoupb.community.mapper.UserMapper;
import com.zhoupb.community.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper = null;
	
	@Autowired
	private ProfileMapper profileMapper = null;
	
	@Transactional
	@Override
	public Result<?> register(UserProfileVo user) {
		if ( user == null )
			return Result.fail("用户数据不能为空");
		
		int count = userMapper.checkUsername(user.getUsername());
		if ( count > 0 )
			return Result.fail("用户名已经被使用啦. 😥");
		
		count = userMapper.checkEmail(user.getEmail());
		if ( count > 0 )
			return Result.fail("邮箱已经被使用啦. 😥");
		
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		if ( userMapper.insert(user) == 0 )
			throw new SaveToDataBaseException("注册失败. 😥");
		
		Profile profile = new Profile(null, null, Common.DEFAULT_AVATAR_URL, null, null, null, user.getId());
		if ( profileMapper.insert(profile) == 0 )
			throw new SaveToDataBaseException("注册失败 . 😥");
		
		return Result.success("注册成功, 赶紧登录叭. 😄");
	}

	@Override
	public Result<String> login(UserProfileVo user) {
		if ( user == null )
			return Result.fail("用户数据不能为空");
		
		User dbUser = userMapper.selectByUsername(user.getUsername());
		
		if (dbUser == null)
			return Result.fail("用户不存在.");
		
		if ( !BCrypt.checkpw(user.getPassword(), dbUser.getPassword()) )
			return Result.fail("用户名或密码错误.");
		
		if ( dbUser.getDeleted() )
			return Result.fail("用户已经被禁封");
		
		String token = JWTUtil.create(dbUser.getId(), dbUser.getUsername(), dbUser.getIsAdmin());
		
		return Result.success("欢迎回来. 😉", token);
	}
	
	@Override
	public Result<UserProfileVo> selectUserProfileByPrimaryKey(int id) {
		UserProfileVo userProfile = userMapper.selectUserProfileVoByPrimaryKey(id);
		if ( userProfile == null )
			Result.fail("没有该用户");
		return Result.success(null, userProfile);
	}

	@Override
	public Result<PageData<UserProfileVo>> adminSelectUserList(long current,long size) {
		Page<UserProfileVo> page = new Page<>(current,size);
		userMapper.adminSelectUserProfileVo(page);
		return Result.success(null,new PageData<>(page));
	}

	@Override
	public Result<?> adminDeleteUserById(int id) {
		UserProfileVo userProfile = userMapper.adminSelectById(id);
		boolean state = userProfile.getDeleted();
		System.out.println(state);
		if (state) {
			state =false;
			userMapper.adminDeleteUserById(state,id);
			return Result.success("已取消该用户的禁用状态！");
		}else {
			state=true;
			userMapper.adminDeleteUserById(true,id);
			return Result.success("该用户已被禁用！");
		}
		
	}

	@Override
	public Result<?> adminUpdateUserById(int id) {
		UserProfileVo userProfile = userMapper.adminSelectById(id);
		boolean state = userProfile.getIsAdmin();
		if(state) {
			userMapper.adminUpdateUserById(!state, id);
			return Result.success("该用户已修改为普通用户！");
		}else {
			userMapper.adminUpdateUserById(!state, id);
			return Result.success("该用户已修改为管理员！");
		}
	}




}
