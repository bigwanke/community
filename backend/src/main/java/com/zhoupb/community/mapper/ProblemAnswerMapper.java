package com.zhoupb.community.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhoupb.community.entity.pojo.ProblemAnswer;
import com.zhoupb.community.entity.vo.problem.ProblemAnswerVo;

public interface ProblemAnswerMapper extends BaseMapper<ProblemAnswer> {

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
			"	p_a.*, " +
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
			"from problem_answer p_a " +
			"	left join \"user\" u on p_a.user_id = u.id " +
			"	left join user_profile p on p.user_id = u.id " +
			"where p_a.deleted = false and p_a.problem_id = #{problemId} order by update_time desc")
	public Page<ProblemAnswerVo> selectPageVo(Page<ProblemAnswerVo> page, @Param("problemId") int problemId);

	@Update("<script> " +
			"	update problem_answer set update_time = now(), vote_count = vote_count" +
			"	<if test='isUp'> + </if>" +
			"	<if test='!isUp'> - </if>" +
			"	1 where deleted = false and id = #{id}" +
			"</script>")
	public int updateVoteByPrimaryKey(@Param("id") int id, @Param("isUp") boolean isUp);

	@Update("update problem_answer set deleted = true, update_time = now() where deleted = false and id = #{id} and user_id = #{userId}")
	public int deleteByPrimaryKeyAndUserId(@Param("id") int id, @Param("userId") int userId);
	
	@Select("select count(id) from problem_answer where deleted = false and problem_id = #{problemId} and is_accept = true")
	public int hasAccept(int problemId);
	
	
	@Results({
		@Result(column = "u_username", property = "userProfile.username"),
	})
	@Select("select p_a.* , "+
			"u.username u_username "+
			"from problem_answer p_a "+
			"left join public.user u "+
			"on u.id = p_a.user_id")
	public Page<ProblemAnswerVo> adminSelectProblemAnswer(Page<ProblemAnswerVo> page);
	
	
	@Select("select * "+
			"from problem_answer "+
			"where id = #{id}")
	public ProblemAnswerVo adminSelectProblemAnswerById(int id);
	
	
	@Delete("update problem_answer "+
			"set deleted = #{deleted} ,update_time = now() "+
			"where id = #{id}")
	public int adminDeleteProblemAnswerById(@Param("deleted") boolean deleted , @Param("id") int id);
}
