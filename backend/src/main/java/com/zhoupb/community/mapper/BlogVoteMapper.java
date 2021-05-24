package com.zhoupb.community.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhoupb.community.entity.pojo.BlogVote;
import com.zhoupb.community.entity.vo.blog.BlogVoteVo;

public interface BlogVoteMapper extends BaseMapper<BlogVote> {

	@Select("<script> " +
			"	select * from blog_vote where deleted = false and reply_id in " +
			"	<foreach item='item' collection='ids' open='(' separator=',' close=')'>" +
			"		#{item}" +
			"	</foreach>"+
			"	and user_id = #{userId}" +
			"</script>")
	public List<BlogVoteVo> selectByReplyIdsAndUserId(@Param("ids") List<Integer> ids, @Param("userId") int userId);

	@Select("select * from blog_vote where deleted = false and blog_id = #{blogId} and user_id = #{userId}")
	public BlogVoteVo selectByBlogIdAndUserId(@Param("blogId") int blogId, @Param("userId") int userId);
	
	
	@Results({
		@Result(column = "u_username", property = "userProfile.username"),
	})
	@Select("select b_v.* , "+
			"u.username u_username "+
			"from blog_vote b_v "+
			"left join public.user u on u.id = b_v.user_id ")
	public Page<BlogVoteVo> adminSelectBlog(Page<BlogVoteVo> page);
	
	@Select("select * "+
			"from blog_vote "+
			"where id = #{id}")
	public BlogVoteVo adminSelectBlogVoteById(int id);
	
	
	@Delete("update blog_vote "+
			"set deleted = #{deleted} "+
			"where id = #{id}")
	public int adminDeleteBlogVoteById(@Param("deleted") boolean deleted , @Param("id") int id);

}
