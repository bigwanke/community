package com.zhoupb.community.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhoupb.community.entity.pojo.ProblemVote;
import com.zhoupb.community.entity.vo.problem.ProblemVoteVo;

public interface ProblemVoteMapper extends BaseMapper<ProblemVote> {

	@Select("select * from problem_vote where deleted = false and problem_id = #{problemId} and user_id = #{userId}")
	public ProblemVoteVo selectByProblemIdAndUserId(@Param("problemId") int probelmId, @Param("userId") int userId);

	@Select("<script> " +
			"	select * from problem_vote where deleted = false and answer_id in " +
			"	<foreach item='item' collection='ids' open='(' separator=',' close=')'>" +
			"		#{item}" +
			"	</foreach>"+
			"	and user_id = #{userId}" +
			"</script>")
	public List<ProblemVoteVo> selectByAnswerIdsAndUserId(@Param("ids") List<Integer> ids, @Param("userId") int userId);
	
	
	@Results({
		@Result(column = "u_username", property = "userProfile.username"),
	})
	@Select("select p_v.* , "+
			"u.username u_username "+
			"from problem_vote p_v "+
			"left join public.user u "+
			"on u.id = p_v.user_id")
	public Page<ProblemVoteVo> adminSelectProblemVote(Page<ProblemVoteVo> page);
	
	@Select("select * "+
			"from problem_vote "+
			"where id = #{id}")
	public ProblemVoteVo adminSelectProblemVoteById(int id);
	
	
	@Delete("update problem_vote "+
			"set deleted = #{deleted} "+
			"where id = #{id}")
	public int adminDeleteProblemVoteById(@Param("deleted") boolean deleted , @Param("id") int id);
	
}
