package com.zhoupb.community.service;

import java.util.List;

import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.vo.share.ShareVoteVo;

public interface ShareVoteService {
	
	//点赞
	public Result<?> post(ShareVoteVo shareVote);
	
	//根据文章id和用户下id获取点赞信息
	public Result<ShareVoteVo> getByShareIdAndUserId(int shareId, int userId);
	
	//查lisit
	public Result<List<ShareVoteVo>> listByReplyIdsAndUserId(List<Integer> ids, int userId);
	
	
	public Result<PageData<ShareVoteVo>> adminSelectShareVote(long current , long size);
	
	public Result<?> adminDeleteShareVoteById(int id);
}
