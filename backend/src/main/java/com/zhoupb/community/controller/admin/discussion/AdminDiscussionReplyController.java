package com.zhoupb.community.controller.admin.discussion;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhoupb.community.annotation.AdminRequired;
import com.zhoupb.community.common.JSONResponse;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.dto.admin.discussion.DiscussionReplyDTO;
import com.zhoupb.community.entity.vo.discussion.DiscussionReplyVo;

import com.zhoupb.community.service.DiscussionReplyService;

@RestController
@RequestMapping("admin/")
public class AdminDiscussionReplyController {
	@Autowired
	DiscussionReplyService discussionReplyService;
	
	@GetMapping("discussion/reply/")
	@AdminRequired
	public JSONResponse list(HttpServletRequest request,@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size) {
		Result<PageData<DiscussionReplyVo>> res = discussionReplyService.adminSelectDiscussionReply(current, size);
		
		List<DiscussionReplyDTO> list = new ArrayList<>();
		res.getData().getData().forEach(e -> {
			DiscussionReplyDTO dto = new DiscussionReplyDTO();
			dto.VoToMe(e);
			list.add(dto);
		});
		return JSONResponse.ok(null, PageData.transformType(list, res.getData()));
	}
	
	@DeleteMapping("discussion/reply/{id}/")
	public JSONResponse adminDeleteDiscussionReply(HttpServletRequest request, @PathVariable int id) {
		Result<?> res = discussionReplyService.adminDeleteDiscussionReplyById(id);
		return JSONResponse.ok(res.getMessage());
		
	}
}
