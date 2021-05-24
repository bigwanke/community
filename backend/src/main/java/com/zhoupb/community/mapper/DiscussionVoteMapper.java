package com.zhoupb.community.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhoupb.community.entity.pojo.DiscussionVote;
import com.zhoupb.community.entity.vo.discussion.DiscussionVoteVo;

public interface DiscussionVoteMapper extends BaseMapper<DiscussionVote>{
	
	@Select("select * from discussion_vote where deleted = false and discussion_id = #{discussionId} and user_id = #{userId}")
	public DiscussionVoteVo selectByDiscussionIdAndUserId(@Param("discussionId") int discussionId, @Param("userId") int userId);
	
	@Select("<script> " +
			"	select * from discussion_vote where deleted = false and reply_id in " +
			"	<foreach item='item' collection='ids' open='(' separator=',' close=')'>" +
			"		#{item}" +
			"	</foreach>"+
			"	and user_id = #{userId}" +
			"</script>")
	public List<DiscussionVoteVo> selectByReplyIdsAndUserId(@Param("ids") List<Integer> ids, @Param("userId") int userId);
	
	@Results({
		@Result(column = "u_username", property = "userProfile.username"),
	})
	@Select("select d_v.* , "+
			"u.username u_username "+
			"from discussion_vote d_v "+
			"left join public.user u "+
			"on u.id = d_v.user_id")
	public Page<DiscussionVoteVo> adminSelectDiscussionVote(Page<DiscussionVoteVo> page);
	
	@Select("select * "+
			"from discussion_vote "+
			"where id = #{id}")
	public DiscussionVoteVo adminSelectDiscussionVoteById(int id);
	
	
	@Delete("update discussion_vote "+
			"set deleted = #{deleted} "+
			"where id = #{id}")
	public int adminDeleteDiscussionVoteById(@Param("deleted") boolean deleted , @Param("id") int id);
}
