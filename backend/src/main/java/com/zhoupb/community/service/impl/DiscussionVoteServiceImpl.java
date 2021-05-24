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
import com.zhoupb.community.entity.pojo.DiscussionVote;
import com.zhoupb.community.entity.vo.discussion.DiscussionVoteVo;
import com.zhoupb.community.mapper.DiscussionMapper;
import com.zhoupb.community.mapper.DiscussionReplyMapper;
import com.zhoupb.community.mapper.DiscussionVoteMapper;
import com.zhoupb.community.service.DiscussionVoteService;

@Service
public class DiscussionVoteServiceImpl implements DiscussionVoteService {
		
	@Autowired
	private DiscussionVoteMapper discussionVoteMapper = null;
	
	@Autowired
	private DiscussionMapper discussionMapper = null;
	
	@Autowired
	private DiscussionReplyMapper discussionReplyMapper = null;
	
	@Override
	@Transactional
	public Result<?> post(DiscussionVoteVo discussionVote) {
		//	判断传入的合法性
		if(discussionVote.getDiscussionId() == -1 && discussionVote.getReplyId() == -1) {
			return Result.fail("discussion id equals -1 and reply id equals -1");
		}
		LambdaQueryChainWrapper<DiscussionVote> query = new LambdaQueryChainWrapper<>(discussionVoteMapper).eq(DiscussionVote::getUserId, discussionVote.getUserId());;
		//  点赞的是分享就根据分享id查(shareId,replyId = -1时为不点赞的,即shareId等于-1,点赞的是reply)
		if(discussionVote.getDiscussionId() != -1) {
			query.eq(DiscussionVote::getDiscussionId,discussionVote.getDiscussionId());
		}
		else {
			query.eq(DiscussionVote::getReplyId,discussionVote.getReplyId());
		}
		//	查出一条
		DiscussionVote one = query.one();
		//	查到了，且是类型一致，则取消
		if(one != null && one.getIsUp() == discussionVote.getIsUp() && one.getUserId() == one.getUserId()) {
			discussionVoteMapper.deleteById(one.getId());
			if (discussionVote.getDiscussionId() != -1) {
				discussionMapper.updateVoteCountByPrimaryKey(discussionVote.getDiscussionId(), !discussionVote.getIsUp());
			}
			else {
				discussionReplyMapper.updateVoteByPrimaryKey(discussionVote.getReplyId(), !discussionVote.getIsUp());
			}
			return Result.success(String.format("取消点%s成功", discussionVote.getIsUp() ? "赞" : "踩"), 202);
		}
		//	查到了，且是类型不一致，则更新
		if(one != null && one.getIsUp() != discussionVote.getIsUp()) {
			one.setIsUp(discussionVote.getIsUp());
			one.setUpdateTime(null);
			if(discussionVoteMapper.updateById(one) == 0) {
				return Result.fail("insert fail");
			}
		}

		//	查不到(则需要新增)
		if(one == null) {
			if(discussionVoteMapper.insert(discussionVote) == 0) {
				return Result.fail("insert fail");
			}
		}

		if(discussionVote.getDiscussionId() != -1) {
			discussionMapper.updateVoteCountByPrimaryKey(discussionVote.getDiscussionId(),discussionVote.getIsUp());
		}
		else {
			discussionReplyMapper.updateVoteByPrimaryKey(discussionVote.getReplyId(),discussionVote.getIsUp());
		}
		if(discussionVote.getIsUp()) {
			return Result.success("点赞成功");
		}
		else {
			return Result.success("点踩成功");
		}

	}
	
	//根据文章id和用户id查询点赞信息
	@Override
	public Result<DiscussionVoteVo> getByDiscussionIdAndUserId(int discussionId, int userId) {
		DiscussionVoteVo discussionVoteVo = discussionVoteMapper.selectByDiscussionIdAndUserId(discussionId, userId);
		if(discussionVoteVo == null) {
			return Result.fail("not found");
		}
		return Result.success(null,discussionVoteVo);
	}
	
	//根据评论id和userID查询
	@Override
	public Result<List<DiscussionVoteVo>> listByReplyIdsAndUserId(List<Integer> ids, int userId) {

		if ( ids == null || ids.size() == 0 ) {
			return Result.success(null, new ArrayList<>());
		}
		
		List<DiscussionVoteVo> list = discussionVoteMapper.selectByReplyIdsAndUserId(ids, userId);
		return Result.success(null, list); 
	}
	
	
	@Override
	public Result<PageData<DiscussionVoteVo>> adminSelectDiscussionVote(long current, long size) {
		Page<DiscussionVoteVo> page = new Page<>(current , size);
		discussionVoteMapper.adminSelectDiscussionVote(page);
		return Result.success(null,new PageData<>(page));
	}
	
	@Override
	public Result<?> adminDeleteDiscussionVoteById(int id) {
		return null;
	}
	

}
