package com.zhoupb.community.controller.admin.blog;

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
import com.zhoupb.community.entity.dto.admin.blog.BlogVoteDTO;
import com.zhoupb.community.entity.vo.blog.BlogVoteVo;
import com.zhoupb.community.service.BlogVoteService;

@RestController
@RequestMapping("admin/")
public class AdminBlogVoteController {
	@Autowired
	private BlogVoteService blogVoteService;
	
	@GetMapping("blog/vote/")
	@AdminRequired
	public JSONResponse list(HttpServletRequest request,@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size) {
		Result<PageData<BlogVoteVo>> res = blogVoteService.adminSelectBlogVote(current, size);
		
		List<BlogVoteDTO> list = new ArrayList<>();
		res.getData().getData().forEach(e -> {
			BlogVoteDTO dto = new BlogVoteDTO();
			dto.VoToMe(e);
			list.add(dto);
		});
		return JSONResponse.ok(null, PageData.transformType(list, res.getData()));
	}
	
	@DeleteMapping("blog/vote/{id}/")
	public JSONResponse adminDeleteBlogVote(HttpServletRequest request, @PathVariable int id) {
		Result<?> res = blogVoteService.adminDeleteBlogVoteById(id);
		return JSONResponse.ok(res.getMessage());
		
	}
}
