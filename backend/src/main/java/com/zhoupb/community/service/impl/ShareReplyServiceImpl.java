package com.zhoupb.community.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.pojo.Share;
import com.zhoupb.community.entity.pojo.ShareReply;
import com.zhoupb.community.entity.vo.share.ShareReplyVo;
import com.zhoupb.community.exception.SaveToDataBaseException;
import com.zhoupb.community.mapper.ShareMapper;
import com.zhoupb.community.mapper.ShareReplyMapper;
import com.zhoupb.community.service.ShareReplyService;

@Service
public class ShareReplyServiceImpl implements ShareReplyService{
	
	@Autowired
	private ShareMapper shareMapper = null;
	
	@Autowired
	private ShareReplyMapper shareReplyMapper = null;
	
	//发表
	@Override
	@Transactional
	public Result<?> post(ShareReplyVo shareReplyVo) {
		Share share = new LambdaQueryChainWrapper<Share>(shareMapper).eq(Share::getId, shareReplyVo.getShareId()).one();
		if (share == null) {
			return Result.fail("not found in share by id: " + shareReplyVo.getShareId());
		} 
		if (shareReplyMapper.insert(shareReplyVo) == 0) {
			throw new SaveToDataBaseException("insert database error");
		} 

		share.setReplyCount(share.getReplyCount() + 1);
		if ( !new LambdaUpdateChainWrapper<>(shareMapper).eq(Share::getId, share.getId()).update(share) ) {
			throw new SaveToDataBaseException("update error");
		}
			
		return Result.success("评论发布成功");
		
	}
	
	//查询
	@Override
	public Result<PageData<ShareReplyVo>> listByShareId(int current, int size, int shareId) {
		Page<ShareReplyVo> page = new Page<>(current, size);
		shareReplyMapper.selectPageVoByShareId(page, shareId);
		return Result.success(null, new PageData<>(page));
	}

	//删除
	@Override
	@Transactional
	public Result<?> deleteByPrimaryKeyAndUserId(int id, int userId) {
		ShareReply dbReply = new LambdaQueryChainWrapper<>(shareReplyMapper).eq(ShareReply::getId, id).eq(ShareReply::getUserId, userId).one();
		if ( dbReply == null ) return Result.fail("似乎没有这个评论哦. 🤣");
		
		if ( shareMapper.updateReplyCountByPrimarKey(dbReply.getShareId(), false) == 0 )
			throw new SaveToDataBaseException("更新分享失败. 🤣");
		
		if ( shareReplyMapper.deleteByPrimaryKeyAndUserId(id, userId) == 0 )
			throw new SaveToDataBaseException("删除回复失败. 🤣");
		return Result.success("删除成功啦. 😉");
	}
	
	
	@Override
	public Result<PageData<ShareReplyVo>> adminSelectShareReply(long current, long size) {
		Page<ShareReplyVo> page = new Page<>(current , size);
		shareReplyMapper.adminSelectShareReply(page);
		return Result.success(null,new PageData<>(page));
	}
	
	
	@Override
	public Result<?> adminDeleteShareReplyById(int id) {
		ShareReplyVo shareReplyVo = shareReplyMapper.adminSelectShareReplyById(id);
		boolean state = shareReplyVo.getDeleted();
		if (state){
			shareReplyMapper.adminDeleteShareReplyById(!state, id);
			return Result.success("已取消该评论的禁用状态！");
		}
		shareReplyMapper.adminDeleteShareReplyById(!state, id);
		return Result.success("该评论已被禁用！");
	}

}
