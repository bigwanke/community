package com.zhoupb.community.controller.blog;

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
import com.zhoupb.community.entity.dto.blog.BlogReplyDTO;
import com.zhoupb.community.entity.dto.blog.BlogReplyPostDTO;
import com.zhoupb.community.entity.vo.blog.BlogReplyVo;
import com.zhoupb.community.entity.vo.blog.BlogVoteVo;
import com.zhoupb.community.service.BlogReplyService;
import com.zhoupb.community.service.BlogVoteService;
import com.zhoupb.validation.Validation;

@RestController
@RequestMapping("blog/reply/")
public class BlogReplyController {

	@Autowired
	private BlogReplyService replyService = null;
	
	@Autowired
	private BlogVoteService voteService = null;
	
	@PostMapping
	@LoginRequired
	@Validation
	public JSONResponse post(HttpServletRequest request, @RequestBody BlogReplyPostDTO reply) {
		reply.setUserId((int) request.getAttribute(Common.REQ_USER_ID));
		Result<?> res = replyService.post(reply.toVo());
		if ( res.isSuccess() ) return JSONResponse.ok(res.getMessage());
		return JSONResponse.error(-1, res.getMessage());
	}
	
	@GetMapping
	public JSONResponse list(@RequestParam Integer blogId,
			@RequestParam(required = false) Integer userId,
			@RequestParam(defaultValue = Common.PAGE_CURRENT_DEFAULT) Integer current,
			@RequestParam(defaultValue = Common.PAGE_SIZE_DEFAULT) Integer size) {
		
		Result<PageData<BlogReplyVo>> res = replyService.listByBlogId(blogId, current, size);
		if ( !res.isSuccess() ) return JSONResponse.error(-1, res.getMessage());
		
		List<BlogReplyDTO> list = new ArrayList<BlogReplyDTO>();
		List<Integer> ids = new ArrayList<>();
		
		res.getData().getData().forEach(e -> {
			BlogReplyDTO t = new BlogReplyDTO();
			t.VoToMe(e);
			list.add(t);
			
			ids.add(e.getId());
		});
		
		if ( userId != null ) {
			Result<List<BlogVoteVo>> blogVoteRes = voteService.listByReplyIdsAndUserId(ids, userId);
			if ( blogVoteRes.isSuccess() )
				list.forEach(e -> {
					BlogVoteVo t = null;
					if ( ( t = isContains(blogVoteRes.getData(), e.getId()) ) != null )
						e.setIsUp(t.getIsUp());
				});
		}
		
		return JSONResponse.ok(null, PageData.transformType(list, res.getData()));
	}
	
	private BlogVoteVo isContains(List<BlogVoteVo> list, int blogReplyId) {
		for ( BlogVoteVo t : list )
			if ( t.getReplyId() == blogReplyId ) return t;
		return null;
	}
	
	@DeleteMapping("{id}/")
	@LoginRequired
	public JSONResponse delete(HttpServletRequest request, @PathVariable int id) {
		Result<?> res = replyService.deleteByPrimaryKeyAndUserId(id, (int) request.getAttribute(Common.REQ_USER_ID));
		if ( res.isSuccess() ) return JSONResponse.ok(res.getMessage());
		return JSONResponse.error(-1, res.getMessage());
	}
	
}
