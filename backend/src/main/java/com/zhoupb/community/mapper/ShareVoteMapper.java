package com.zhoupb.community.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhoupb.community.entity.pojo.ShareVote;
import com.zhoupb.community.entity.vo.share.ShareVoteVo;

public interface ShareVoteMapper extends BaseMapper<ShareVote>{
	@Select("<script> " +
			"	select * from share_vote where deleted = false and reply_id in " +
			"	<foreach item='item' collection='ids' open='(' separator=',' close=')'>" +
			"		#{item}" +
			"	</foreach>"+
			"	and user_id = #{userId}" +
			"</script>")
	public List<ShareVoteVo> selectByReplyIdsAndUserId(@Param("ids") List<Integer> ids, @Param("userId") int userId);
	
	@Select("select * from share_vote where deleted = false and share_id = #{shareId} and user_id = #{userId}")
	public ShareVoteVo selectByShareIdAndUserId(@Param("shareId") int shareId, @Param("userId") int userId);
	
	
	@Results({
		@Result(column = "u_username", property = "userProfile.username"),
	})
	@Select("select s_v.* , "+
			"u.username u_username "+
			"from share_vote s_v "+
			"left join public.user u "+
			"on u.id = s_v.user_id")
	public Page<ShareVoteVo> adminSelectShareVote(Page<ShareVoteVo> page);
	
	
	@Select("select * "+
			"from share_vote "+
			"where id = #{id}")
	public ShareVoteVo adminSelectShareVoteById(int id);
	
	
	@Delete("update share_vote "+
			"set deleted = #{deleted} "+
			"where id = #{id}")
	public int adminDeleteShareVoteById(@Param("deleted") boolean deleted , @Param("id") int id);


	
}
