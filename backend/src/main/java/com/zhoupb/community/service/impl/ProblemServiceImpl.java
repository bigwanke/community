package com.zhoupb.community.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.pojo.Problem;
import com.zhoupb.community.entity.vo.problem.ProblemVo;
import com.zhoupb.community.mapper.ProblemMapper;
import com.zhoupb.community.service.ProblemService;

@Service
public class ProblemServiceImpl implements ProblemService {
	
	@Autowired
	private ProblemMapper problemMapper = null;

	@Override
	public Result<?> post(ProblemVo problem) {
		int res = problemMapper.insert(problem);
		if ( res == 0 ) return Result.fail("a o, 发布失败咯. 😶");
		return Result.success("提问成功，请耐心等待. 😉");
	}

	@Override
	public Result<PageData<ProblemVo>> selectByUserId(int userId, long current, long size) {
		Page<ProblemVo> page = new Page<>(current, size);
		problemMapper.selectPageVoByUserId(page, userId);
		return Result.success(null, new PageData<>(page));
	}
	
	@Override
	public Result<PageData<ProblemVo>> selectList(long current, long size) {
		Page<ProblemVo> page = new Page<>(current, size);
		problemMapper.selectPageVo(page);
		return Result.success(null, new PageData<>(page));
	}
	
	@Override
	public Result<ProblemVo> selectByPrimaryKey(int id) {
		Problem problem = new LambdaQueryChainWrapper<Problem>(problemMapper).eq(Problem::getId, id).one();
		if ( problem == null ) return Result.fail("not found");
		ProblemVo problemVo = new ProblemVo();
		problemVo.POJOtoMe(problem);
		return Result.success(null, problemVo);
	}
	
	@Override
	public Result<?> addReadCountByPrimaryKey(int id) {
		if ( problemMapper.addReadCountByPrimaryKey(id) == 0 )
			return Result.fail("add blog read count fail, id: " + id);
		return Result.success(null);
	}
	
	@Override
	public Result<PageData<ProblemVo>> adminSelectProblem(long current, long size) {
		Page<ProblemVo> page = new Page<>(current , size);
		problemMapper.adminSelectPoblem(page);
		return Result.success(null,new PageData<>(page));
	}
	
	@Override
	public Result<?> adminDeleteProblemById(int id) {
		ProblemVo problemVo = problemMapper.adminSelectProblemById(id);
		boolean state = problemVo.getDeleted();
		if (state){
			problemMapper.adminDeleteProblem(!state, id);
			return Result.success("已取消该问题的禁用状态！");
		}
		problemMapper.adminDeleteProblem(!state, id);
		return Result.success("该问题已被禁用！");
	}

}
