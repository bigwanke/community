package com.zhoupb.community.service;

import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.vo.problem.ProblemVo;

public interface ProblemService {
	
	public Result<?> post(ProblemVo problem);

	public Result<PageData<ProblemVo>> selectByUserId(int userId, long current, long size);

	public Result<PageData<ProblemVo>> selectList(long current, long size);

	public Result<ProblemVo> selectByPrimaryKey(int id);
	
	public Result<?> addReadCountByPrimaryKey(int id);
	
	public Result<PageData<ProblemVo>> adminSelectProblem(long current , long size);
	
	public Result<?> adminDeleteProblemById(int id);

}
