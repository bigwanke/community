package com.zhoupb.community.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhoupb.community.entity.pojo.ShareReply;
import com.zhoupb.community.entity.vo.share.ShareReplyVo;

public interface ShareReplyMapper extends BaseMapper<ShareReply>{
	
	//点赞
	@Update("<script> " +
			"	update share_reply set update_time = now(), vote_count = vote_count" +
			"	<if test='is_up'> + </if>" +
			"	<if test='!is_up'> - </if>" +
			"	1 where deleted = false and id = #{id}" +
			"</script>")
	public int updateVoteByPrimaryKey(@Param("id") int id, @Param("is_up") boolean isUp);
	
	//查评论
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
	@Select("select  " +
			 "	s_r.*,  " +
			 "	u.id u_id,  " +
			 "	u.username u_username,  " +
			 "	u.password u_password,  " +
			 "	u.email u_email,  " +
			 "	u.email_active u_email_active,  " +
			 "	u.is_admin u_is_admin,  " +
			 "	u.deleted u_deleted,  " +
			 "	u.create_time u_create_time,  " +
			 "	u.update_time u_update_time,  " +
			 "	p.id p_id,  " +
			 "	p.introduction p_introduction,  " +
			 "	p.avatar p_avatar,  " +
			 "	p.deleted p_deleted,  " +
			 "	p.create_time p_create_time,  " +
			 "	p.update_time p_update_time  " +
			 "from share_reply s_r   " +
			 "	left join \"user\" u on s_r.user_id = u.id   " +
			 "	left join user_profile p on p.user_id = u.id   " +
			 "where s_r.deleted = false and s_r.share_id = #{shareId} order by update_time desc")
	public Page<ShareReplyVo> selectPageVoByShareId(Page<ShareReplyVo> page,@Param("shareId") int shareId);

	//删除
	@Update("update share_reply set deleted = true, update_time = now() where deleted = false AND id = #{id} and user_id = #{userId}")
	public int deleteByPrimaryKeyAndUserId(@Param("id") int id, @Param("userId") int userId);
	
	
	@Results({
		@Result(column = "u_username", property = "userProfile.username"),
	})
	@Select("select s_r.* , "+
			"u.username u_username "+
			"from share_reply s_r "+
			"left join public.user u "+
			"on u.id = s_r.user_id ")
	public Page<ShareReplyVo> adminSelectShareReply(Page<ShareReplyVo> page);
	
	
	@Select("select * "+
			"from share_reply "+
			"where id = #{id}")
	public ShareReplyVo adminSelectShareReplyById(int id);
	
	
	@Delete("update share_reply "+
			"set deleted = #{deleted} ,update_time = now() "+
			"where id = #{id}")
	public int adminDeleteShareReplyById(@Param("deleted") boolean deleted , @Param("id") int id);
}
