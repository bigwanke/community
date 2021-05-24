package com.zhoupb.community.service;

import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.vo.blog.BlogVo;

public interface BlogService {
	
	public Result<?> post(BlogVo blog);
	
	public Result<PageData<BlogVo>> selectByUserId(int userId, long current, long size);
	
	public Result<BlogVo> selectByPrimaryKey(int id);
	
	public Result<PageData<BlogVo>> selectList(long current, long size);
	
	public Result<?> addReadCountByPrimaryKey(int id);
	
	public Result<?> put(BlogVo blog);
	
	public Result<PageData<BlogVo>> adminSelectBlog(long current ,long size);
	
	public Result<?> adminDeletedBlogById(int id);

}
