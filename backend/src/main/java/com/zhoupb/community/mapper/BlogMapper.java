package com.zhoupb.community.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.ArrayTypeHandler;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhoupb.community.entity.pojo.Blog;
import com.zhoupb.community.entity.vo.blog.BlogVo;

public interface BlogMapper extends BaseMapper<Blog> {
	
	@Results({
		@Result(column = "tags", property = "tags", typeHandler = ArrayTypeHandler.class)
	})
	@Select("select * from blog where deleted = false and user_id = #{userId}")
	public Page<BlogVo> selectPageVoByUserId(Page<BlogVo> page, @Param("userId") int userId);
	
	@Results({
		@Result(column = "tags", property = "tags", typeHandler = ArrayTypeHandler.class),
		
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
			"	b.*, " +
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
			"from blog b  " +
			"	left join \"user\" u on b.user_id = u.id  " +
			"	left join user_profile p on p.user_id = u.id  " +
			"where b.deleted = false order by update_time desc")
	public Page<BlogVo> selectPageVo(Page<BlogVo> page);
	
	@Update("<script> " +
			"	update blog set read_count = read_count " +
			"	<if test='isAdd'> + </if> " + 
			"	<if test='!isAdd'> - </if>" + 
			" 	1 where deleted = false and id = #{id}" +
			"</script>")
	public int updateReadCountByPrimaryKey(@Param("id") int id, @Param("isAdd") boolean isAdd);
	
	@Update("<script> " +
			"	update blog set update_time = now(), vote_count = vote_count" +
			"	<if test='isUp'> + </if>" +
			"	<if test='!isUp'> - </if>" +
			"	1 where deleted = false and id = #{id}" +
			"</script>")
	public int updateVoteCountByPrimaryKey(@Param("id") int id, @Param("isUp") boolean isUp);
	
	@Update("<script> " +
			"	update blog set update_time = now(), reply_count = reply_count " +
			"	<if test='isAdd'> + </if> " + 
			"	<if test='!isAdd'> - </if>" + 
			" 	1 where deleted = false and id = #{id}" +
			"</script>")
	public int updateReplyCountByPrimarKey(@Param("id") int id, @Param("isAdd") boolean isAdd);
	
	@Results({
		@Result(column = "tags", property = "tags", typeHandler = ArrayTypeHandler.class),
		@Result(column = "u_username", property = "userProfile.username"),
	})
	@Select("select b.*, "+
			"u.username u_username "+
			"from blog b "+
			"left join \"user\" u "+
			"on b.user_id = u.id ")
	public Page<BlogVo> adminSelectBlog(Page<BlogVo> page);
	
	
	@Select("select * " +
			"from blog where id=#{id}")
	public BlogVo adminSelectBlogById(int id);
	
	@Delete("update blog "+
			"set deleted = #{deleted} ,update_time = now() "+
			"where id = #{id}")
	public int adminDeleteBlogById(@Param("deleted") boolean deleted , @Param("id") int id);
	
}
