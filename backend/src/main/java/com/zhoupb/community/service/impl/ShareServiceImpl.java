package com.zhoupb.community.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.common.StringUtil;
import com.zhoupb.community.entity.pojo.Share;
import com.zhoupb.community.entity.vo.share.ShareVo;
import com.zhoupb.community.mapper.ShareMapper;
import com.zhoupb.community.service.ShareService;

@Service
public class ShareServiceImpl implements ShareService{
	
	@Autowired
	private ShareMapper shareMapper = null;
	
	//根据userId查询列表
	@Override
	public Result<PageData<ShareVo>> selectByUserId(int userId, long current, long size) {
		Page<ShareVo> page = new Page<ShareVo>(current, size);
		shareMapper.selectPageVoByUserId(page, userId);
		return Result.success(null, new PageData<ShareVo>(page));
	}
	
	//根据id查share
	@Override
	public Result<ShareVo> selectByPrimaryKey(int id) {
		Share share = new LambdaQueryChainWrapper<Share>(shareMapper).eq(Share::getId, id).one();
		if ( share == null ) {
			return Result.fail("找不到该圈子.");
		}
		ShareVo shareVo = new ShareVo();
		shareVo.POJOtoMe(share);
		return Result.success(null, shareVo);
	}
	
	//查所有
	@Override
	public Result<PageData<ShareVo>> selectList(long current, long size) {
		Page<ShareVo> page = new Page<>(current, size);
		shareMapper.selectPageVo(page);
		return Result.success(null, new PageData<>(page));
	}
	
	//添加阅读量
	@Override
	public Result<?> addReadCountByPrimaryKey(int id) {
		if ( shareMapper.updateReadCountByPrimaryKey(id, true) == 0 ) {
			return Result.fail("add share read count fail, id: " + id);
		}
		return Result.success(null);
	}
	
	//修改
	@Override
	public Result<?> put(ShareVo share) {
		if ( share == null ) {
			return Result.fail("圈子内容不能为空哦.");
		}
		String str = StringUtil.html2Str(share.getContentHtml());
		share.setDescription(str);

		
		if ( !new LambdaUpdateChainWrapper<>(shareMapper).eq(Share::getId, share.getId()).eq(Share::getUserId, share.getUserId()).update(share) )
			return Result.fail("更新失败，好可惜啊");
		return Result.success("修改成功，你真棒");
	}
	
	//新增
	@Override
	public Result<?> insertShare(ShareVo share) {
		if ( share == null ) {
			return Result.fail("圈子内容不能为空哦");
		}
		String str = StringUtil.html2Str(share.getContentHtml());
		share.setDescription(str);
		if ( shareMapper.insert(share) == 0 ) {
			return Result.fail("o, 发布失败咯");
		}
		return Result.success("恭喜, 你发布了一篇圈子");
	}
	
	@Override
	public Result<PageData<ShareVo>> adminSelectShare(long current, long size) {
		Page<ShareVo> page = new Page<>(current , size);
		shareMapper.adminSelectShare(page);
		return Result.success(null,new PageData<>(page));
	}
	
	
	@Override
	public Result<?> adminDeleteShareById(int id) {
		ShareVo shareVo = shareMapper.adminSelectShareById(id);
		boolean state = shareVo.getDeleted();
		if (state){
			shareMapper.adminDeleteShareById(!state, id);
			return Result.success("已取消该文章的禁用状态！");
		}
		shareMapper.adminDeleteShareById(!state, id);
		return Result.success("该文章已被禁用！");
	}

}
