package com.zhoupb.community.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhoupb.community.common.HttpUtil;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.common.StringUtil;
import com.zhoupb.community.entity.pojo.Blog;
import com.zhoupb.community.entity.vo.blog.BlogVo;
import com.zhoupb.community.mapper.BlogMapper;
import com.zhoupb.community.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogMapper blogMapper = null;
	
	@Override
	public Result<?> post(BlogVo blog) {
		if ( blog == null )
			return Result.fail("分享内容不能为空哦.");
		
//		String str = StringUtil.html2Str(blog.getContentHtml());
//		blog.setDescription(str);
//
//		str = str.replaceAll("[\\x00-\\xff]", "");
//		if ( str.length() > 60 )
//			str = str.substring(0, 60);
//		
//		Result<String> res = HttpUtil.getGenerateImageURL(str);
//		if ( !res.isSuccess() )
//			return Result.fail(res.getMessage());
//		blog.setCoverImage(res.getData());
		
		setBlogDescriptionAndCoverImage(blog);
		
		if ( blogMapper.insert(blog) == 0 )
			return Result.fail("o, 发布失败咯. 再试一次叭. 😅");
		return Result.success("恭喜, 你发布了一篇文章, 再接再厉哦. 👍");
	}
	
	@Override
	public Result<PageData<BlogVo>> selectByUserId(int userId, long current, long size) {
		Page<BlogVo> page = new Page<BlogVo>(current, size);
		blogMapper.selectPageVoByUserId(page, userId);
		return Result.success(null, new PageData<BlogVo>(page));
	}

	@Override
	public Result<BlogVo> selectByPrimaryKey(int id) {
		Blog blog = new LambdaQueryChainWrapper<Blog>(blogMapper).eq(Blog::getId, id).one();
		if ( blog == null )
			return Result.fail("找不到该文章.");
		BlogVo blogVo = new BlogVo();
		blogVo.POJOtoMe(blog);
		return Result.success(null, blogVo);
	}
	
	@Override
	public Result<PageData<BlogVo>> selectList(long current, long size) {
		Page<BlogVo> page = new Page<>(current, size);
		blogMapper.selectPageVo(page);
		return Result.success(null, new PageData<>(page));
	}
	
	@Override
	public Result<?> addReadCountByPrimaryKey(int id) {
		if ( blogMapper.updateReadCountByPrimaryKey(id, true) == 0 ) 
			return Result.fail("add blog read count fail, id: " + id);
		return Result.success(null);
	}
	
	@Override
	public Result<?> put(BlogVo blog) {
		setBlogDescriptionAndCoverImage(blog);
		
		if ( !new LambdaUpdateChainWrapper<>(blogMapper).eq(Blog::getId, blog.getId()).eq(Blog::getUserId, blog.getUserId()).update(blog) )
			return Result.fail("更新失败咯. 🤣");
		return Result.success("修改好啦. 👌");
	}
	
	private void setBlogDescriptionAndCoverImage(Blog blog) {
		String str = StringUtil.html2Str(blog.getContentHtml());
		blog.setDescription(str);

//		str = str.replaceAll("[\\x00-\\xff]", "");
		if ( str.length() > 60 )
			str = str.substring(0, 60);
		
		Result<String> res = HttpUtil.getGenerateImageURL(str);
		blog.setCoverImage(res.getData());
	}

	@Override
	public Result<PageData<BlogVo>> adminSelectBlog(long current, long size) {
		Page<BlogVo> page = new Page<>(current,size);
		blogMapper.adminSelectBlog(page);
		return Result.success(null,new PageData<>(page));
	}

	@Override
	public Result<?> adminDeletedBlogById(int id) {
		BlogVo blogVo = blogMapper.adminSelectBlogById(id);
		boolean state = blogVo.getDeleted();
		if (state){
			blogMapper.adminDeleteBlogById(!state, id);
			return Result.success("已取消该分享的禁用状态！");
		}
		blogMapper.adminDeleteBlogById(!state, id);
		return Result.success("该分享已被禁用！");
	}
	
}
