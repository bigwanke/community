package com.zhoupb.community.service;

import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.vo.discussion.DiscussionVo;

public interface DiscussionService {
	
	public Result<?> post(DiscussionVo discussion);
	
	public Result<PageData<DiscussionVo>> selectByUserId(int userId, long current, long size);
	
	public Result<DiscussionVo> selectByPrimaryKey(int id);
	
	public Result<PageData<DiscussionVo>> selectList(long current, long size);
	
	public Result<?> addReadCountByPrimaryKey(int id);
	
	public Result<?> updateLastReplyId(int id,int lastReplyId);
	
	public Result<?> put(DiscussionVo discussion);
	
	public Result<PageData<DiscussionVo>> adminSelectDiscussion(long current , long size);
	
	public Result<?> adminDeleteDiscussionById(int id);
}
