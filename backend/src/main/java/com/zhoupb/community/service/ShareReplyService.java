package com.zhoupb.community.service;

import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.vo.share.ShareReplyVo;

public interface ShareReplyService {
	
	//发评论
	public Result<?> post(ShareReplyVo shareReplyVo);
	
	//查询
	public Result<PageData<ShareReplyVo>> listByShareId(int current, int size ,int shareId);
	
	//删除
	public Result<?> deleteByPrimaryKeyAndUserId(int id, int userId);
	
	
	public Result<PageData<ShareReplyVo>> adminSelectShareReply(long current , long size);
	
	public Result<?> adminDeleteShareReplyById(int id);
}
