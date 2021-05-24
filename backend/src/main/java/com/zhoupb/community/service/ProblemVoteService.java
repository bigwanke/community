package com.zhoupb.community.service;

import java.util.List;

import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.vo.problem.ProblemVoteVo;

public interface ProblemVoteService {

	public Result<?> post(ProblemVoteVo vote);
	
	public Result<ProblemVoteVo> getByProblemIdAndUserId(int probelmId, int userId);
	
	public Result<List<ProblemVoteVo>> listByAnswerIdsAndUserId(List<Integer> ids, int userId);
	
	
	public Result<PageData<ProblemVoteVo>> adminSelectProblemVote(long current , long size);
	
	public Result<?> adminDeleteProblemVoteById(int id);
	
}
