package com.zhoupb.community.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.pojo.ProblemVote;
import com.zhoupb.community.entity.vo.problem.ProblemVoteVo;
import com.zhoupb.community.mapper.ProblemAnswerMapper;
import com.zhoupb.community.mapper.ProblemMapper;
import com.zhoupb.community.mapper.ProblemVoteMapper;
import com.zhoupb.community.service.ProblemVoteService;

@Service
public class ProblemVoteServiceImpl implements ProblemVoteService {

	@Autowired
	private ProblemVoteMapper voteMapper = null;
	
	@Autowired
	private ProblemMapper problemMapper = null;
	
	@Autowired
	private ProblemAnswerMapper answerMapper = null;
	
	@Override
	@Transactional
	public Result<?> post(ProblemVoteVo vote) {
		if ( vote.getAnswerId() == -1 && vote.getProblemId() == -1 )
			return Result.fail("answer id equals -1 and problem id equals -1");
		
		LambdaQueryChainWrapper<ProblemVote> query = new LambdaQueryChainWrapper<>(voteMapper).eq(ProblemVote::getUserId, vote.getUserId());
		if ( vote.getProblemId() != -1 )
			query.eq(ProblemVote::getProblemId, vote.getProblemId());
		else
			query.eq(ProblemVote::getAnswerId, vote.getAnswerId());
		
		ProblemVote dbVote = query.one();
		if ( dbVote != null && dbVote.getUserId() == vote.getUserId()  && dbVote.getIsUp() == vote.getIsUp() ) {
			voteMapper.deleteById(dbVote.getId());
			if ( vote.getProblemId() != -1 )
				problemMapper.updateVoteByPrimaryKey(vote.getProblemId(), !vote.getIsUp());
			else
				answerMapper.updateVoteByPrimaryKey(vote.getAnswerId(), !vote.getIsUp());
			return Result.success(String.format("取消点%s成功啦. 🐷", vote.getIsUp() ? "赞" : "踩"), 202);
		}
		if ( dbVote != null && dbVote.getIsUp() != vote.getIsUp() ) {
			dbVote.setIsUp(vote.getIsUp());
			dbVote.setUpdateTime(null);
			if ( voteMapper.updateById(dbVote) == 0 ) return Result.fail("insert fail");
		}
		if ( dbVote == null )
			if ( voteMapper.insert(vote) == 0 )
				return Result.fail("insert fail");
		if ( vote.getProblemId() != -1 )
			problemMapper.updateVoteByPrimaryKey(vote.getProblemId(), vote.getIsUp());
		else
			answerMapper.updateVoteByPrimaryKey(vote.getAnswerId(), vote.getIsUp());
		if ( vote.getIsUp() ) return Result.success("点赞成功啦. 😘");
		else return Result.success("点踩成功啦. 😭");
	}
	
	@Override
	public Result<ProblemVoteVo> getByProblemIdAndUserId(int probelmId, int userId) {
		ProblemVoteVo dbVoteVo = voteMapper.selectByProblemIdAndUserId(probelmId, userId);
		if ( dbVoteVo == null ) return Result.fail("not found");
		return Result.success(null, dbVoteVo);
	}
	
	@Override
	public Result<List<ProblemVoteVo>> listByAnswerIdsAndUserId(List<Integer> ids, int userId) {
		if ( ids == null || ids.size() == 0 ) return Result.success(null, new ArrayList<>());
		List<ProblemVoteVo> list = voteMapper.selectByAnswerIdsAndUserId(ids, userId);
		return Result.success(null, list);
	}
	
	@Override
	public Result<PageData<ProblemVoteVo>> adminSelectProblemVote(long current, long size) {
		Page<ProblemVoteVo> page = new Page<>(current , size);
		voteMapper.adminSelectProblemVote(page);
		return Result.success(null,new PageData<>(page));
	}
	
	@Override
	public Result<?> adminDeleteProblemVoteById(int id) {
		ProblemVoteVo problemVoteVo = voteMapper.adminSelectProblemVoteById(id);
		boolean state = problemVoteVo.getDeleted();
		if (state){
			voteMapper.adminDeleteProblemVoteById(!state, id);
			return Result.success("已取消该点赞的禁用状态！");
		}
		voteMapper.adminDeleteProblemVoteById(!state, id);
		return Result.success("该点赞已被禁用！");
	}
	
}
