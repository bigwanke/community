package com.zhoupb.community.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.pojo.Discussion;
import com.zhoupb.community.entity.pojo.DiscussionReply;
import com.zhoupb.community.entity.vo.discussion.DiscussionReplyVo;
import com.zhoupb.community.exception.SaveToDataBaseException;
import com.zhoupb.community.mapper.DiscussionMapper;
import com.zhoupb.community.mapper.DiscussionReplyMapper;
import com.zhoupb.community.mapper.UserMapper;
import com.zhoupb.community.service.DiscussionReplyService;

@Service
public class DiscussionReplyServiceImpl implements DiscussionReplyService{
	
	@Autowired
	private DiscussionMapper discussionMapper = null;
	
	@Autowired
	private DiscussionReplyMapper discussionReplyMapper = null;
	
	@Autowired
	private UserMapper userMapper = null;
	
	//发评论
	@Override
	@Transactional
	public Result<?> post(DiscussionReplyVo discussionReplyVo) {
		Discussion discussion = new LambdaQueryChainWrapper<Discussion>(discussionMapper).eq(Discussion::getId, discussionReplyVo.getDiscussionId()).one();
		
		if ( discussion == null ) {
			return Result.fail("not found in discussionReply by id: " + discussionReplyVo.getDiscussionId());
		} 
		discussion.setLastReplyId(discussionReplyVo.getId());
		if ( discussionReplyMapper.insert(discussionReplyVo) == 0) {
			throw new SaveToDataBaseException("insert database error");
		} 

		discussion.setReplyCount(discussion.getReplyCount() + 1);
//		DiscussionReply discussionReply = discussionReplyMapper.selectLastReplyByDiscussionId(discussionReplyVo.getDiscussionId());
//		if(discussionReply == null) {
//			discussionReplyVo.setFloor(1);
//		} 
//		else {
//			discussionReplyVo.setFloor(discussionReply.getFloor() + 1);
//		}

		if ( !new LambdaUpdateChainWrapper<>(discussionMapper).eq(Discussion::getId, discussion.getId()).update(discussion) ) {
			throw new SaveToDataBaseException("update error");
		}
			
		return Result.success("发布成功");
	}

	//根据文章id查评论
	@Override
	public Result<PageData<DiscussionReplyVo>> listByDiscussionId(int discussionId, long current, long size) {
		// TODO Auto-generated method stub
		Page<DiscussionReplyVo> page = new Page<>(current, size);
		discussionReplyMapper.selectPageVoByDiscussionId(page, discussionId);
		return Result.success(null, new PageData<>(page));
	}

	//根据id查
	@Override
	@Transactional
	public Result<DiscussionReplyVo> getDiscussionByPrimaryKey(int id) {
		DiscussionReply discussionReply = new LambdaQueryChainWrapper<DiscussionReply>(discussionReplyMapper).eq(DiscussionReply::getId, id).one();
		if ( discussionReply == null ) {
			return Result.fail("没有找到此评论");
		} 
		DiscussionReplyVo discussionReplyVo = new DiscussionReplyVo();
		discussionReplyVo.POJOtoMe(discussionReply);
		discussionReplyVo.setUserProfile(userMapper.selectUserProfileVoByPrimaryKey(discussionReplyVo.getUserId()));
		return Result.success(null, discussionReplyVo);
	}

	//删除
	@Override
	@Transactional
	public Result<?> deleteByPrimaryKeyAndUserId(int id, int userId) {
		DiscussionReply discussionReply = new LambdaQueryChainWrapper<>(discussionReplyMapper).eq(DiscussionReply::getId, id).eq(DiscussionReply::getUserId, userId).one();
		if ( discussionReply == null ) {
			 return Result.fail("似乎没有这个评论哦. 🤣");
		}
		
		if ( discussionMapper.updateReplyCountByPrimarKey(discussionReply.getDiscussionId(), false) == 0 ) {
			throw new SaveToDataBaseException("更新分享失败. 🤣");
		}

		
		if ( discussionReplyMapper.deleteByPrimaryKeyAndUserId(id, userId) == 0 ) {
			throw new SaveToDataBaseException("删除回复失败. 🤣");
		}

		return Result.success("删除成功啦. 😉");
	}
	
	//查询最后发布的评论
	@Override
	public Result<DiscussionReplyVo> getLastDiscussionReply(int discussionId) {
		DiscussionReply discussionReply = discussionReplyMapper.selectLastReplyByDiscussionId(discussionId);
		if(discussionReply == null) {
			return Result.fail("似乎没有这个评论哦.🤣");
		}
		DiscussionReplyVo discussionReplyVo = new DiscussionReplyVo();
		discussionReplyVo.POJOtoMe(discussionReply);
		discussionReplyVo.setUserProfile(userMapper.selectUserProfileVoByPrimaryKey(discussionReplyVo.getUserId()));
		return Result.success(null, discussionReplyVo);
	}
	
	@Override
	public Result<PageData<DiscussionReplyVo>> adminSelectDiscussionReply(long current, long size) {
		Page<DiscussionReplyVo> page = new Page<>(current , size);
		discussionReplyMapper.adminSelectDiscussionReply(page);
		return Result.success(null,new PageData<>(page));
	}
	
	@Override
	public Result<?> adminDeleteDiscussionReplyById(int id) {
		DiscussionReplyVo discussionReplyVo = discussionReplyMapper.adminSelectDiscussionReplyById(id);
		boolean state = discussionReplyVo.getDeleted();
		if (state){
			discussionReplyMapper.adminDeletedDiscussionReplyById(!state, id);
			return Result.success("已取消该评论的禁用状态！");
		}
		discussionReplyMapper.adminDeletedDiscussionReplyById(!state, id);
		return Result.success("该评论已被禁用！");
	}

}
