package com.zhoupb.community.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhoupb.community.entity.pojo.Share;
import com.zhoupb.community.entity.vo.share.ShareVo;

public interface ShareMapper extends BaseMapper<Share>{

	
	@Results({
 		@Result(column = "u_id", property = "userProfile.id"),
		@Result(column = "u_username", property = "userProfile.username"),
		@Result(column = "u_password", property = "userProfile.password"),
		@Result(column = "u_email", property = "userProfile.email"),
		@Result(column = "u_email_active", property = "userProfile.emailActive"),
		@Result(column = "u_is_admin", property = "userProfile.isAdmin"),
		@Result(column = "u_deleted", property = "userProfile.deleted"),
		@Result(column = "u_create_time", property = "userProfile.createTime"),
		@Result(column = "u_update_time", property = "userProfile.updateTime"),
		
		@Result(column = "p_id", property = "userProfile.profile.id"),
		@Result(column = "p_introduction", property = "userProfile.profile.introduction"),
		@Result(column = "p_avatar", property = "userProfile.profile.avatar"),
		@Result(column = "p_deleted", property = "userProfile.profile.deleted"),
		@Result(column = "p_create_ime", property = "userProfile.profile.createTime"),
		@Result(column = "p_update_time", property = "userProfile.profile.updateTime"),
		@Result(column = "p_user_id", property = "userProfile.profile.userId"),
	})
	@Select("select " +
			"	s.*, " +
			"	u.id u_id, " +
			"	u.username u_username, " +
			"	u.password u_password, " +
			"	u.email u_email, " +
			"	u.email_active u_email_active, " +
			"	u.is_admin u_is_admin, " +
			"	u.deleted u_deleted, " +
			"	u.create_time u_create_time, " +
			"	u.update_time u_update_time, " +
			"	p.id p_id, " +
			"	p.introduction p_introduction, " +
			"	p.avatar p_avatar, " +
			"	p.deleted p_deleted, " +
			"	p.create_time p_create_time, " +
			"	p.update_time p_update_time " +
			"from share s  " +
			"	left join \"user\" u on s.user_id = u.id  " +
			"	left join user_profile p on p.user_id = u.id  " +
			"where s.deleted = false order by update_time desc")
	public Page<ShareVo> selectPageVo(Page<ShareVo> page);
	
	@Select("select " +
			"	s.*, " +
			"	u.id u_id, " +
			"	u.username u_username, " +
			"	u.password u_password, " +
			"	u.email u_email, " +
			"	u.email_active u_email_active, " +
			"	u.is_admin u_is_admin, " +
			"	u.deleted u_deleted, " +
			"	u.create_time u_create_time, " +
			"	u.update_time u_update_time, " +
			"	p.id p_id, " +
			"	p.introduction p_introduction, " +
			"	p.avatar p_avatar, " +
			"	p.deleted p_deleted, " +
			"	p.create_time p_create_time, " +
			"	p.update_time p_update_time " +
			"from share s  " +
			"	left join \"user\" u on s.user_id = u.id  " +
			"	left join user_profile p on p.user_id = u.id  " +
			"where s.deleted = false and s.user_id = #{userId} order by update_time desc")
	public Page<ShareVo> selectPageVoByUserId(Page<ShareVo> page, @Param("userId") int userId);
	
	@Update("<script> " +
			"	update share set update_time = now(), read_count = read_count " +
			"	<if test='isAdd'> + </if> " + 
			"	<if test='!isAdd'> - </if>" + 
			" 	1 where deleted = false and id = #{id}" +
			"</script>")
	public int updateReadCountByPrimaryKey(@Param("id") int id, @Param("isAdd") boolean isAdd);
	
	@Update("<script> " +
			"	update share set update_time = now(), vote_count = vote_count" +
			"	<if test='isUp'> + </if>" +
			"	<if test='!isUp'> - </if>" +
			"	1 where deleted = false and id = #{id}" +
			"</script>")
	public int updateVoteCountByPrimaryKey(@Param("id") int id, @Param("isUp") boolean isUp);
	
	@Update("<script> " +
			"	update share set update_time = now(), reply_count = reply_count " +
			"	<if test='isAdd'> + </if> " + 
			"	<if test='!isAdd'> - </if>" + 
			" 	1 where deleted = false and id = #{id}" +
			"</script>") 
	public int updateReplyCountByPrimarKey(@Param("id") int id, @Param("isAdd") boolean isAdd);
	
	
	@Results({
		@Result(column = "u_username", property = "userProfile.username"),
	})
	@Select("select s.* , "+
			"u.username u_username "+
			"from share s "+
			"left join public.user u "+
			"on u.id = s.user_id")
	public Page<ShareVo> adminSelectShare(Page<ShareVo> page);
	
	
	@Select("select * "+
			"from share "+
			"where id = #{id}")
	public ShareVo adminSelectShareById(int id);
	
	
	@Delete("update share "+
			"set deleted = #{deleted} ,update_time = now() "+
			"where id = #{id}")
	public int adminDeleteShareById(@Param("deleted") boolean deleted , @Param("id") int id);
	
}
