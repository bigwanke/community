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
import com.zhoupb.community.entity.pojo.ShareVote;
import com.zhoupb.community.entity.vo.share.ShareVoteVo;
import com.zhoupb.community.mapper.ShareMapper;
import com.zhoupb.community.mapper.ShareReplyMapper;
import com.zhoupb.community.mapper.ShareVoteMapper;
import com.zhoupb.community.service.ShareVoteService;

@Service
public class ShareVoteServiceImpl implements ShareVoteService{
	
	@Autowired
	private ShareVoteMapper shareVoteMapper = null;
	
	@Autowired
	private ShareMapper shareMapper= null;
	
	@Autowired
	private ShareReplyMapper shareReplyMapper = null;
	
	@Override
	@Transactional
	public Result<?> post(ShareVoteVo shareVote) {
		
		//	判断传入的合法性
		if(shareVote.getShareId() == -1 && shareVote.getReplyId() == -1)
			return Result.fail("share id equals -1 and reply id equals -1");
		
		LambdaQueryChainWrapper<ShareVote> query = new LambdaQueryChainWrapper<>(shareVoteMapper);
		//  点赞的是分享就根据分享id查(shareId,replyId = -1时为不点赞的,即shareId等于-1,点赞的是reply)
		if(shareVote.getShareId() != -1) {
			query.eq(ShareVote::getShareId,shareVote.getShareId());
		}
		else {
			query.eq(ShareVote::getReplyId,shareVote.getReplyId());
		}
		//	查出一条
		ShareVote one = query.one();
		//	查到了，且是类型一致，则取消
		if(one != null && one.getIsUp() == shareVote.getIsUp() && one.getUserId() == shareVote.getUserId()) {
			shareVoteMapper.deleteById(one.getId());
			if (shareVote.getShareId() != -1) {
				shareMapper.updateVoteCountByPrimaryKey(shareVote.getShareId(), !shareVote.getIsUp());
			}
			else {
				shareReplyMapper.updateVoteByPrimaryKey(shareVote.getReplyId(), !shareVote.getIsUp());
			}
			return Result.success(String.format("取消点%s成功", shareVote.getIsUp() ? "赞" : "踩"),202);
		}
		//	查到了，且是类型不一致，则更新
		if(one != null && one.getIsUp() != shareVote.getIsUp()) {
			one.setIsUp(shareVote.getIsUp());
			one.setUpdateTime(null);
			if(shareVoteMapper.updateById(one) == 0) {
				return Result.fail("insert fail");
			}
		}
			//	查不到(则需要新增)
			
		if(one == null) {
			if(shareVoteMapper.insert(shareVote) == 0) {
				return Result.fail("insert fail");
			}
		}
		if(shareVote.getShareId() != -1) {
			shareMapper.updateVoteCountByPrimaryKey(shareVote.getShareId(),shareVote.getIsUp());
		}
		else {
			shareReplyMapper.updateVoteByPrimaryKey(shareVote.getReplyId(),shareVote.getIsUp());
		}
		if(shareVote.getIsUp()) {
			return Result.success("点赞成功");
		}else {
			return Result.success("点踩成功");
		}

	}

	//根据文章id和用户id查询点赞信息
	@Override
	public Result<ShareVoteVo> getByShareIdAndUserId(int shareId, int userId) {
		ShareVoteVo shareVote = shareVoteMapper.selectByShareIdAndUserId(shareId, userId);
		if(shareVote == null) {
			return Result.fail("not found");
		}
		return Result.success(null,shareVote);
	}
	
	//根据replyId，userId查评论列表
	@Override
	public Result<List<ShareVoteVo>> listByReplyIdsAndUserId(List<Integer> ids, int userId) {
		if(ids == null || ids.size() == 0) {
			return Result.success(null, new ArrayList<>());
		}
		
		List<ShareVoteVo> list = shareVoteMapper.selectByReplyIdsAndUserId(ids, userId);
		return Result.success(null,list);
	}
	
	@Override
	public Result<PageData<ShareVoteVo>> adminSelectShareVote(long current, long size) {
		Page<ShareVoteVo> page = new Page<>(current , size);
		shareVoteMapper.adminSelectShareVote(page);
		return Result.success(null,new PageData<>(page));
	}
	
	@Override
	public Result<?> adminDeleteShareVoteById(int id) {
		ShareVoteVo shareVoteVo = shareVoteMapper.adminSelectShareVoteById(id);
		boolean state = shareVoteVo.getDeleted();
		if (state){
			shareVoteMapper.adminDeleteShareVoteById(!state, id);
			return Result.success("已取消该分享的禁用状态！");
		}
		shareVoteMapper.adminDeleteShareVoteById(!state, id);
		return Result.success("该分享已被禁用！");
	}
}
