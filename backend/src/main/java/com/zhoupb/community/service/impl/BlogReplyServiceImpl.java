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
import com.zhoupb.community.entity.pojo.Blog;
import com.zhoupb.community.entity.pojo.BlogReply;
import com.zhoupb.community.entity.vo.blog.BlogReplyVo;
import com.zhoupb.community.exception.SaveToDataBaseException;
import com.zhoupb.community.mapper.BlogMapper;
import com.zhoupb.community.mapper.BlogReplyMapper;
import com.zhoupb.community.service.BlogReplyService;

@Service
public class BlogReplyServiceImpl implements BlogReplyService {

	@Autowired
	private BlogReplyMapper replyMapper = null;
	
	@Autowired
	private BlogMapper blogMapper = null;
	
	@Override
	@Transactional
	public Result<?> post(BlogReplyVo blogReply) {
		Blog blog = new LambdaQueryChainWrapper<Blog>(blogMapper).eq(Blog::getId, blogReply.getBlogId()).one();
		if ( blog == null ) return Result.fail("not found in blog by id: " + blogReply.getBlogId());
		if ( replyMapper.insert(blogReply) == 0) throw new SaveToDataBaseException("insert database error");

		blog.setReplyCount(blog.getReplyCount() + 1);
		blog.setUpdateTime(LocalDateTime.now());
		if ( !new LambdaUpdateChainWrapper<>(blogMapper).eq(Blog::getId, blog.getId()).update(blog) )
			throw new SaveToDataBaseException("update error");
		return Result.success("评论发布成功啦. 😁");
	}
	
	@Override
	public Result<PageData<BlogReplyVo>> listByBlogId(int blogId, long current, long size) {
		Page<BlogReplyVo> page = new Page<>(current, size);
		replyMapper.selectPageVoByBlogId(page, blogId);
		return Result.success(null, new PageData<>(page));
	}
	
	@Override
	@Transactional
	public Result<?> deleteByPrimaryKeyAndUserId(int id, int userId) {
		BlogReply dbReply = new LambdaQueryChainWrapper<>(replyMapper).eq(BlogReply::getId, id).eq(BlogReply::getUserId, userId).one();
		if ( dbReply == null ) return Result.fail("似乎没有这个评论哦. 🤣");
		
		if ( blogMapper.updateReplyCountByPrimarKey(dbReply.getBlogId(), false) == 0 )
			throw new SaveToDataBaseException("更新分享失败. 🤣");
		
		if ( replyMapper.deleteByPrimaryKeyAndUserId(id, userId) == 0 )
			throw new SaveToDataBaseException("删除回复失败. 🤣");
		return Result.success("删除成功啦. 😉");
	}

	@Override
	public Result<PageData<BlogReplyVo>> adminSelectBlogReply(long current , long size) {
		Page<BlogReplyVo> page = new Page<>(current , size);
		replyMapper.adminSelectBlogReply(page);
		return Result.success(null, new PageData<>(page));
	}

	@Override
	public Result<?> adminDeleteBlogReplyById(int id) {
		BlogReplyVo blogReply = replyMapper.adminSelectBlogReplyById(id);
		boolean state = blogReply.getDeleted();
		if (state) {
			replyMapper.adminDeleteBlogReplyById(!state, id);
			return Result.success("已取消该评论的禁用状态！");
		}
		replyMapper.adminDeleteBlogReplyById(!state, id);
		return Result.success("该评论已被禁用！");
	}

}
