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
import com.zhoupb.community.entity.dto.admin.blog.BlogReplyDTO;
import com.zhoupb.community.entity.vo.blog.BlogReplyVo;
import com.zhoupb.community.service.BlogReplyService;

@RestController
@RequestMapping("admin/")
public class AdminBlogReplyController {
	@Autowired
	private BlogReplyService blogReplyService;
	
	@GetMapping("blog/reply/")
	@AdminRequired
	public JSONResponse list(HttpServletRequest request,@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size) {
		Result<PageData<BlogReplyVo>> res = blogReplyService.adminSelectBlogReply(current, size);
		
		List<BlogReplyDTO> list = new ArrayList<>();
		res.getData().getData().forEach(e -> {
			BlogReplyDTO dto = new BlogReplyDTO();
			dto.VoToMe(e);
			list.add(dto);
		});
		return JSONResponse.ok(null, PageData.transformType(list, res.getData()));
	}
	
	@DeleteMapping("blog/reply/{id}/")
	public JSONResponse adminDeleteBlogReply(HttpServletRequest request, @PathVariable int id) {
		Result<?> res = blogReplyService.adminDeleteBlogReplyById(id);
		return JSONResponse.ok(res.getMessage());
		
	}
}
