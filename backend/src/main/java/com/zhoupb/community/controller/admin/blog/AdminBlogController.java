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
import com.zhoupb.community.entity.dto.admin.blog.BlogDTO;
import com.zhoupb.community.entity.vo.blog.BlogVo;
import com.zhoupb.community.service.BlogService;

@RestController
@RequestMapping("admin/")
public class AdminBlogController {
	@Autowired
	private BlogService blogService;
	
	@GetMapping("getBlogList/")
	@AdminRequired
	public JSONResponse list(HttpServletRequest request,@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size) {
		Result<PageData<BlogVo>> res = blogService.adminSelectBlog(current, size);
		
		List<BlogDTO> list = new ArrayList<>();
		res.getData().getData().forEach(e -> {
			BlogDTO dto = new BlogDTO();
			dto.VoToMe(e);
			list.add(dto);
		});
		return JSONResponse.ok(null, PageData.transformType(list, res.getData()));
	}
	
	@DeleteMapping("blog/{id}/")
	public JSONResponse adminDeleteBlog(HttpServletRequest request, @PathVariable int id) {
		Result<?> res = blogService.adminDeletedBlogById(id);
		return JSONResponse.ok(res.getMessage());
		
	}
}
