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
import com.zhoupb.community.entity.dto.admin.discussion.DiscussionDTO;
import com.zhoupb.community.entity.vo.discussion.DiscussionVo;
import com.zhoupb.community.service.DiscussionService;

@RestController
@RequestMapping("admin/")
public class AdminDiscussionController {
	@Autowired
	DiscussionService discussionService;
	
	@GetMapping("discussion/")
	@AdminRequired
	public JSONResponse list(HttpServletRequest request,@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size) {
		Result<PageData<DiscussionVo>> res = discussionService.adminSelectDiscussion(current, size);
		
		List<DiscussionDTO> list = new ArrayList<>();
		res.getData().getData().forEach(e -> {
			DiscussionDTO dto = new DiscussionDTO();
			dto.VoToMe(e);
			list.add(dto);
		});
		return JSONResponse.ok(null, PageData.transformType(list, res.getData()));
	}
	
	@DeleteMapping("discussion/{id}/")
	public JSONResponse adminDeleteDiscussion(HttpServletRequest request, @PathVariable int id) {
		Result<?> res = discussionService.adminDeleteDiscussionById(id);
		return JSONResponse.ok(res.getMessage());
		
	}
	
}
