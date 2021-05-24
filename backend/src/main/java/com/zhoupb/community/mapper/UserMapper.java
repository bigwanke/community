package com.zhoupb.community.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhoupb.community.entity.pojo.User;
import com.zhoupb.community.entity.vo.user.UserProfileVo;

public interface UserMapper extends BaseMapper<User> {
	
	@Results({
//		@Result(id = true, column = "id", property = "id"),
//		@Result(column = "username", property = "username"),
//		@Result(column = "password", property = "password"),
//		@Result(column = "email", property = "email"),
//		@Result(column = "email_active", property = "emailActive"),
//		@Result(column = "is_admin", property = "isAdmin"),
//		@Result(column = "deleted", property = "deleted"),
//		@Result(column = "create_time", property = "createTime"),
//		@Result(column = "update_time", property = "updateTime"),
		@Result(column = "p_id", property = "profile.id"),
		@Result(column = "p_introduction", property = "profile.introduction"),
		@Result(column = "p_avatar", property = "profile.avatar"),
		@Result(column = "p_deleted", property = "profile.deleted"),
		@Result(column = "p_create_ime", property = "profile.createTime"),
		@Result(column = "p_update_time", property = "profile.updateTime"),
		@Result(column = "p_user_id", property = "profile.userId"),
	})
	@Select("select " +
			"	u.*, " +
			"	u_p.id p_id, " +
			"	u_p.introduction p_introduction, " +
			"	u_p.avatar p_avatar, " +
			"	u_p.deleted p_deleted, " +
			"	u_p.create_time p_create_time, " +
			"	u_p.update_time p_update_time, " +
			"	u_p.user_id p_user_id " +
			"from public.user u left join user_profile u_p on u.id = u_p.user_id where u.deleted = false and u.id = #{id}")
	public UserProfileVo selectUserProfileVoByPrimaryKey(int id);
	
	
	@Results({
		@Result(column = "p_id", property = "profile.id"),
		@Result(column = "p_introduction", property = "profile.introduction"),
		@Result(column = "p_avatar", property = "profile.avatar"),
	})
	@Select("select "+
			"u.*, " + 
			"u_p.avatar p_avatar " +
			"from public.user u left join user_profile u_p on u.id = u_p.user_id ")
	public Page<UserProfileVo> adminSelectUserProfileVo(Page<UserProfileVo> page);
	
	@Select("select "+
			"u.*, " + 
			"u_p.avatar p_avatar " +
			"from public.user u left join user_profile u_p on u.id = u_p.user_id where u.id = #{id}"
			)
	public UserProfileVo adminSelectById(int id);
	
	
	@Update("update "+
			"public.user "+
			"set deleted = #{deleted} , update_time = now() where id = #{id}"
			)
	public int adminDeleteUserById(@Param("deleted") boolean deleted ,@Param("id") int id);
	
	@Update("update " +
			"public.user "+
			"set is_admin = #{isAdmin} ,update_time = now() where id = #{id}")
	public int adminUpdateUserById(@Param("isAdmin") boolean isAdmin ,@Param("id") int id);
	
	@Select("select count(id) from \"user\" where username = #{username}")
	public int checkUsername(String username);
	
	@Select("select count(id) from \"user\" where email = #{email}")
	public int checkEmail(String email);
	
	@Select("select * from \"user\" where username = #{username} ")
	public User selectByUsername(String username);
}
