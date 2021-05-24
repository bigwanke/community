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
import com.zhoupb.community.entity.pojo.BlogVote;
import com.zhoupb.community.entity.vo.blog.BlogVoteVo;
import com.zhoupb.community.mapper.BlogMapper;
import com.zhoupb.community.mapper.BlogReplyMapper;
import com.zhoupb.community.mapper.BlogVoteMapper;
import com.zhoupb.community.service.BlogVoteService;

@Service
public class BlogVoteServiceImpl implements BlogVoteService {

	@Autowired
	private BlogVoteMapper voteMapper = null;
	
	@Autowired
	private BlogMapper blogMapper = null;
	
	@Autowired
	private BlogReplyMapper replyMapper = null;
	
	@Override
	@Transactional
	public Result<?> post(BlogVoteVo vote) {
		//	判断传入的合法性
		if ( vote.getReplyId() == -1 && vote.getBlogId() == -1 )
			return Result.fail("blog id equals -1 and reply id equals -1");
		
		LambdaQueryChainWrapper<BlogVote> query = new LambdaQueryChainWrapper<>(voteMapper).eq(BlogVote::getUserId, vote.getUserId());
		//	点赞的是分享就根据分享id查
		if ( vote.getBlogId() != -1 )
			query.eq(BlogVote::getBlogId, vote.getBlogId());
		else
			query.eq(BlogVote::getReplyId, vote.getReplyId());
		
		//	查出一条
		BlogVote dbVote = query.one();
		//	查到了，且是类型一致，则取消
		if ( dbVote != null && dbVote.getUserId() == vote.getUserId() && dbVote.getIsUp() == vote.getIsUp() ) {
			voteMapper.deleteById(dbVote.getId());
			if ( vote.getBlogId() != -1 )
				blogMapper.updateVoteCountByPrimaryKey(vote.getBlogId(), !vote.getIsUp());
			else
				replyMapper.updateVoteByPrimaryKey(vote.getReplyId(), !vote.getIsUp());
			return Result.success(String.format("取消点%s成功啦. 🐷", vote.getIsUp() ? "赞" : "踩"), 202);
		}
		//	查到了，且是类型一致，则更新
		if ( dbVote != null && dbVote.getIsUp() != vote.getIsUp() ) {
			dbVote.setIsUp(vote.getIsUp());
			dbVote.setUpdateTime(null);
			if ( voteMapper.updateById(dbVote) == 0)
				return Result.fail("insert fail");
		}
		//	查不到
		if ( dbVote == null )
			if ( voteMapper.insert(vote) == 0 )
				return Result.fail("insert fail");
		
		if ( vote.getBlogId() != -1 )
			blogMapper.updateVoteCountByPrimaryKey(vote.getBlogId(), vote.getIsUp());
		else
			replyMapper.updateVoteByPrimaryKey(vote.getReplyId(), vote.getIsUp());
		if ( vote.getIsUp() ) return Result.success("点赞成功啦. 😘");
		else return Result.success("点踩成功啦. 😭");
	}
	
	@Override
	public Result<List<BlogVoteVo>> listByReplyIdsAndUserId(List<Integer> ids, int userId) {
		if ( ids == null || ids.size() == 0 )
			return Result.success(null, new ArrayList<>());
		
		List<BlogVoteVo> list = voteMapper.selectByReplyIdsAndUserId(ids, userId);
		return Result.success(null, list);
	}
	
	@Override
	public Result<BlogVoteVo> getByBlogIdAndUserId(int blogId, int userId) {
		BlogVoteVo blogVote = voteMapper.selectByBlogIdAndUserId(blogId, userId);
		if ( blogVote == null ) return Result.fail("not found");
		return Result.success(null, blogVote);
	}
	
	@Override
	public Result<?> adminDeleteBlogVoteById(int id) {
		BlogVoteVo blogVote = voteMapper.adminSelectBlogVoteById(id);
		boolean state = blogVote.getDeleted();
		if (state){
			voteMapper.adminDeleteBlogVoteById(!state, id);
			return Result.success("已取消该点赞的禁用状态！");
		}
		voteMapper.adminDeleteBlogVoteById(!state, id);
		return Result.success("该点赞已被禁用！");
	}
	
	@Override
	public Result<PageData<BlogVoteVo>> adminSelectBlogVote(long current, long size) {
		Page<BlogVoteVo> page = new Page<>(current , size);
		voteMapper.adminSelectBlog(page);
		return Result.success(null,new PageData<>(page));
	}
}
