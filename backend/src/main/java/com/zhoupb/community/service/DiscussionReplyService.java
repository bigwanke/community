package com.zhoupb.community.service;

import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.vo.discussion.DiscussionReplyVo;

public interface DiscussionReplyService {
	
	//发评论
	public Result<?> post(DiscussionReplyVo discussionReplyVo);
	
	
	//根据文章id查询文章所有评论
	public Result<PageData<DiscussionReplyVo>> listByDiscussionId(int discussionId, long current, long size);
	
	//根据评论id查询
	public Result<DiscussionReplyVo> getDiscussionByPrimaryKey(int id);
	
	//删除
	public Result<?> deleteByPrimaryKeyAndUserId(int id, int userId );
	
	//查询最后发布的文章
	public Result<DiscussionReplyVo> getLastDiscussionReply(int discussionId);
	
	public Result<PageData<DiscussionReplyVo>> adminSelectDiscussionReply(long current , long size);
	
	public Result<?> adminDeleteDiscussionReplyById(int id);

}
