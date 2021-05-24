package com.zhoupb.community.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.pojo.Problem;
import com.zhoupb.community.entity.pojo.ProblemAnswer;
import com.zhoupb.community.entity.vo.problem.ProblemAnswerVo;
import com.zhoupb.community.exception.SaveToDataBaseException;
import com.zhoupb.community.mapper.ProblemAnswerMapper;
import com.zhoupb.community.mapper.ProblemMapper;
import com.zhoupb.community.service.ProblemAnswerService;

@Service
public class ProblemAnswerServiceImpl implements ProblemAnswerService {

	@Autowired
	private ProblemAnswerMapper answerMapper = null;
	
	@Autowired
	private ProblemMapper problemMapper = null;
	
	
	@Override
	@Transactional
	public Result<?> post(ProblemAnswerVo problemAnswer) {
		Problem problem = new LambdaQueryChainWrapper<Problem>(problemMapper).eq(Problem::getId, problemAnswer.getProblemId()).one();
		if ( problem == null ) return Result.fail("这个问题不存在. 🤣");
		problem.setAnswerCount(problem.getAnswerCount() + 1);
		problem.setUpdateTime(LocalDateTime.now());
		
		if ( answerMapper.insert(problemAnswer) == 0 ) throw new SaveToDataBaseException("o,发布失败咯. 🤣");
		if ( !new LambdaUpdateChainWrapper<>(problemMapper).eq(Problem::getId, problem.getId()).update(problem) ) 
			throw new SaveToDataBaseException("o,发布失败咯. 🤣");
		
		return Result.success("答案发布成功啦. 😁");
	}

	@Override
	public Result<PageData<ProblemAnswerVo>> listByProblemId(long current, long size, int problemId) {
		Page<ProblemAnswerVo> page = new Page<>(current, size);
		answerMapper.selectPageVo(page, problemId);
		return Result.success(null, new PageData<>(page));
	}
	
	@Override
	@Transactional
	public Result<?> updateAccept(ProblemAnswerVo answer) {
		Problem problem = new LambdaQueryChainWrapper<>(problemMapper).eq(Problem::getId, answer.getProblemId()).one();
		if ( problem == null ) return Result.fail("not found problem. id is: " + answer.getProblemId());
		if ( answerMapper.updateById(answer) == 0 )
			return Result.fail("update fail");
		// 有bug 
		problem.setIsSolved(answer.getIsAccept());
		problem.setUpdateTime(null);
		if ( problemMapper.updateById(problem) == 0 )
			throw new SaveToDataBaseException("update fail");
		return Result.success("赶紧给ta个赞吧. 😜");
	}

	@Override
	@Transactional
	public Result<?> deleteByPrimaryKeyAndUserId(int id, int userId) {
		ProblemAnswer dbAnswer = new LambdaQueryChainWrapper<>(answerMapper).eq(ProblemAnswer::getId, id).eq(ProblemAnswer::getUserId, userId).one();
		if ( dbAnswer == null ) return Result.fail("似乎没有这个回答哦. 🤣");
		
		if ( problemMapper.updateAnswerCountByPrimaryKey(dbAnswer.getProblemId(), false) == 0 )
			throw new SaveToDataBaseException("更新问题失败. 🤣");
		
		if ( answerMapper.deleteByPrimaryKeyAndUserId(id, userId) == 0 )
			throw new SaveToDataBaseException("删除回答失败. 🤣");
		
		//	用户删除了回答，已经没有参考答案了
		if ( answerMapper.hasAccept(dbAnswer.getProblemId()) == 0 ) {
			if ( problemMapper.updateIsSolvedByPrimaryKey(dbAnswer.getProblemId()) == 0 )
				throw new SaveToDataBaseException("更新问题失败. 🤣");
		}
		
		return Result.success("删除成功啦. 😉");
	}
	
	@Override
	public Result<PageData<ProblemAnswerVo>> adminSelectProblemAnswer(long current, long size) {
		Page<ProblemAnswerVo> page = new Page<>(current , size);
		answerMapper.adminSelectProblemAnswer(page);
		return Result.success(null,new PageData<>(page));
	}
	
	@Override
	public Result<?> adminDeleteProblemAnswerById(int id) {
		ProblemAnswerVo problemAnswer = answerMapper.adminSelectProblemAnswerById(id);
		boolean state = problemAnswer.getDeleted();
		if (state){
			answerMapper.adminDeleteProblemAnswerById(!state, id);
			return Result.success("已取消该回答的禁用状态！");
		}
		answerMapper.adminDeleteProblemAnswerById(!state, id);
		return Result.success("该回答已被禁用！");
	}
	
}
