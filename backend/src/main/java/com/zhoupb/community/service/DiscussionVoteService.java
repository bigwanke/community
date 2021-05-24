package com.zhoupb.community.service;

import java.util.List;

import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.vo.discussion.DiscussionVoteVo;

public interface DiscussionVoteService {
	
	//点赞
	public Result<?> post(DiscussionVoteVo discussionVote);
	
	//根据文章id和用户下id获取点赞信息
	public Result<DiscussionVoteVo> getByDiscussionIdAndUserId(int discussionId, int userId);
	
	//根据评论id和userid查点赞列表
	public Result<List<DiscussionVoteVo>> listByReplyIdsAndUserId(List<Integer> ids, int userId);
	
	
	public Result<PageData<DiscussionVoteVo>> adminSelectDiscussionVote(long current , long size);
	
	
	public Result<?> adminDeleteDiscussionVoteById(int id);

}
