package com.zhoupb.community.service;

import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.vo.blog.BlogReplyVo;

public interface BlogReplyService {
	
	public Result<?> post(BlogReplyVo blogReply);

	public Result<PageData<BlogReplyVo>> listByBlogId(int blogId, long current, long size);
	
	public Result<?> deleteByPrimaryKeyAndUserId(int id, int userId);
	
	public Result<PageData<BlogReplyVo>> adminSelectBlogReply(long current , long size);
	
	public Result<?> adminDeleteBlogReplyById(int id);
	
}
