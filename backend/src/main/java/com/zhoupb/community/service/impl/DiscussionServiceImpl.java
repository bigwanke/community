package com.zhoupb.community.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.common.StringUtil;
import com.zhoupb.community.entity.pojo.Discussion;
import com.zhoupb.community.entity.vo.discussion.DiscussionVo;
import com.zhoupb.community.mapper.DiscussionMapper;
import com.zhoupb.community.service.DiscussionService;

@Service
public class DiscussionServiceImpl implements DiscussionService{
	
	@Autowired
	private DiscussionMapper discussionMappper = null;
	
	//发布讨论
	@Override
	public Result<?> post(DiscussionVo discussion) {
		if ( discussion == null ) {
			return Result.fail("讨论内容不能为空哦.");
		}
		
		String str = StringUtil.html2Str(discussion.getContentHtml());
		discussion.setDescription(str);
		if ( discussionMappper.insert(discussion) == 0 ) {
			return Result.fail("o, 发布失败咯. 再试一次叭. 😅");
		}

		return Result.success("恭喜, 你发布了一篇讨论");
	}
	
	//根据userId查询discussion
	@Override
	public Result<PageData<DiscussionVo>> selectByUserId(int userId, long current, long size) {
		Page<DiscussionVo> page = new Page<DiscussionVo>(current, size);
		discussionMappper.selectPageVoByUserId(page, userId);
		return Result.success(null, new PageData<DiscussionVo>(page));
	}

	//根据id查
	@Override
	public Result<DiscussionVo> selectByPrimaryKey(int id) {
		Discussion discussion = new LambdaQueryChainWrapper<Discussion>(discussionMappper).eq(Discussion::getId, id).one();
		if ( discussion == null ) {
			return Result.fail("找不到该讨论.");
		}
		DiscussionVo discussionVo = new DiscussionVo();
		discussionVo.POJOtoMe(discussion);
		return Result.success(null, discussionVo);
	}
	
	//查所有
	@Override
	public Result<PageData<DiscussionVo>> selectList(long current, long size) {
		Page<DiscussionVo> page = new Page<>(current, size);
		discussionMappper.selectPageVo(page);
		return Result.success(null, new PageData<>(page));
	}
	
	
	//添加阅读量
	@Override
	public Result<?> addReadCountByPrimaryKey(int id) {
		if (discussionMappper.updateReadCountByPrimaryKey(id, true) == 0) {
			return Result.fail("add discussion read count fail, id: " + id);
		}
		return Result.success(null);

	}
	
	//修改最后发表的评论id
	@Override
	public Result<?> updateLastReplyId(int id, int lastReplyId) {
		if(discussionMappper.updateDiscussionLastReplyId(id, lastReplyId) == 0) {
			return Result.fail("update discussion lastReplyId fail,id" + id);
		}
		System.err.println("最后发布id" + lastReplyId);
		return Result.success(null);
	}

	
	//修改
	@Override
	public Result<?> put(DiscussionVo discussion) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Result<PageData<DiscussionVo>> adminSelectDiscussion(long current, long size) {
		Page<DiscussionVo> page = new Page<>(current , size);
		discussionMappper.adminSelectDiscussion(page);
		return Result.success(null,new PageData<>(page));
	}
	
	@Override
	public Result<?> adminDeleteDiscussionById(int id) {
		DiscussionVo discussionVo = discussionMappper.adminSelectDiscussionById(id);
		boolean state = discussionVo.getDeleted();
		if (state){
			discussionMappper.adminDeleteDiscussionById(!state, id);
			return Result.success("已取消该讨论的禁用状态！");
		}
		discussionMappper.adminDeleteDiscussionById(!state, id);
		return Result.success("该讨论已被禁用！");
	}


}
