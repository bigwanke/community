package com.zhoupb.community.service;

import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.vo.problem.ProblemAnswerVo;

public interface ProblemAnswerService {
	
	public Result<?> post(ProblemAnswerVo problemAnswer);
	
	public Result<PageData<ProblemAnswerVo>> listByProblemId(long current, long size, int problemId);
	
	public Result<?> updateAccept(ProblemAnswerVo answer);
	
	public Result<?> deleteByPrimaryKeyAndUserId(int id, int userId);
	
	
	public Result<PageData<ProblemAnswerVo>> adminSelectProblemAnswer(long current , long size);
	
	public Result<?> adminDeleteProblemAnswerById(int id);

}
