package com.zhoupb.community.service;

import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.vo.share.ShareVo;

public interface ShareService {
	
	public Result<?> insertShare(ShareVo share);
	
	public Result<PageData<ShareVo>> selectByUserId(int userId, long current, long size);
	
	public Result<ShareVo> selectByPrimaryKey(int id);
	
	public Result<PageData<ShareVo>> selectList(long current, long size);
	
	public Result<?> addReadCountByPrimaryKey(int id);
	
	public Result<?> put(ShareVo share);   
	
	
	public Result<PageData<ShareVo>> adminSelectShare(long current , long size);
	
	public Result<?> adminDeleteShareById(int id);

}
