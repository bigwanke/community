package com.zhoupb.community.service;

import java.util.List;

import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.vo.blog.BlogVoteVo;

public interface BlogVoteService {

	public Result<?> post(BlogVoteVo vote);
	
	public Result<List<BlogVoteVo>> listByReplyIdsAndUserId(List<Integer> ids, int userId);
	
	public Result<BlogVoteVo> getByBlogIdAndUserId(int blogId, int userId);
	
	public Result<PageData<BlogVoteVo>> adminSelectBlogVote(long current , long size);
	
	public Result<?> adminDeleteBlogVoteById(int id);
	
}
