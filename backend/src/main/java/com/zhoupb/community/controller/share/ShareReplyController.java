package com.zhoupb.community.controller.share;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhoupb.community.annotation.LoginRequired;
import com.zhoupb.community.common.Common;
import com.zhoupb.community.common.JSONResponse;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.dto.share.ShareReplyDTO;
import com.zhoupb.community.entity.dto.share.ShareReplyPostDTO;
import com.zhoupb.community.entity.vo.share.ShareReplyVo;
import com.zhoupb.community.entity.vo.share.ShareVoteVo;
import com.zhoupb.community.service.ShareReplyService;
import com.zhoupb.community.service.ShareVoteService;
import com.zhoupb.validation.Validation;

@RestController
@RequestMapping("share/reply")
public class ShareReplyController {
	
	@Autowired
	private ShareReplyService shareReplyService = null;
	
	@Autowired
	private ShareVoteService shareVoteService = null;
	
	//写评论
	@PostMapping
	@LoginRequired
	@Validation
	public JSONResponse post(HttpServletRequest request,@RequestBody ShareReplyPostDTO shareReplyPostDTO) {
		shareReplyPostDTO.setUserId((int)request.getAttribute(Common.REQ_USER_ID));
//		shareReplyPostDTO.setUserId(19);
		Result<?> res = shareReplyService.post(shareReplyPostDTO.toVo());
		if(!res.isSuccess()) {
			return JSONResponse.error(-1, res.getMessage());
		}
		
		return JSONResponse.ok(res.getMessage());
	}
	
	//查询评论
	@GetMapping
	public JSONResponse selectList(@RequestParam Integer shareId,
			@RequestParam(required = false) Integer userId,
			@RequestParam(defaultValue = Common.PAGE_CURRENT_DEFAULT) Integer current,
			@RequestParam(defaultValue = Common.PAGE_SIZE_DEFAULT) Integer size) {
		
		//查询评论内容
		Result<PageData<ShareReplyVo>> res = shareReplyService.listByShareId(current, size, shareId);
		if(!res.isSuccess()) {
			return JSONResponse.error(-1, res.getMessage());
		}
		List<ShareReplyDTO> list = new ArrayList<ShareReplyDTO>();
		List<Integer> ids = new ArrayList<>();
		res.getData().getData().forEach(e -> {
			ShareReplyDTO shareReplyDTO = new ShareReplyDTO();
			shareReplyDTO.VoToMe(e);
			list.add(shareReplyDTO);
			
			ids.add(e.getId());
 		});
		if(userId != null) {
			//查询评论列表点赞信息
			Result<List<ShareVoteVo>> shareVoteRes = shareVoteService.listByReplyIdsAndUserId(ids, userId);
			if (shareVoteRes.isSuccess()) {
				list.forEach(e -> {
					ShareVoteVo s = null;
					if ( ( s = isContains(shareVoteRes.getData(), e.getId()) ) != null ) {
						e.setIsUp(s.getIsUp());
					}

				});
			}

		}
		return JSONResponse.ok(null, PageData.transformType(list, res.getData()));
	}
	
	private ShareVoteVo isContains(List<ShareVoteVo> list,int shareReplyId) {
		
		for(ShareVoteVo s : list) {
			
			if(s.getReplyId() == shareReplyId) {
				return s;
			}
		}
		return null;
	}
	
	//删除
	@DeleteMapping("{id}/")
	@LoginRequired
	public JSONResponse delete(HttpServletRequest request, @PathVariable int id) {
		Result<?> res = shareReplyService.deleteByPrimaryKeyAndUserId(id, (int) request.getAttribute(Common.REQ_USER_ID));
		if ( res.isSuccess() ) return JSONResponse.ok(res.getMessage());
		return JSONResponse.error(-1, res.getMessage());
	}
	
}
