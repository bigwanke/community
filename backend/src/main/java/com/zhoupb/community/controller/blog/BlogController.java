package com.zhoupb.community.controller.blog;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhoupb.community.common.JSONResponse;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.dto.blog.BlogDTO;
import com.zhoupb.community.entity.vo.blog.BlogVo;
import com.zhoupb.community.entity.vo.blog.BlogVoteVo;
import com.zhoupb.community.entity.vo.user.UserProfileVo;
import com.zhoupb.community.service.BlogService;
import com.zhoupb.community.service.BlogVoteService;
import com.zhoupb.community.service.UserService;

@RestController
@RequestMapping("blog/")
public class BlogController {

	@Autowired
	private BlogService blogService = null; 
	
	@Autowired
	private UserService userService = null;
	
	@Autowired
	private BlogVoteService voteService = null;
	
	@GetMapping("{id}/")
	public JSONResponse get(@PathVariable int id, @RequestParam(required = false) Integer userId) {
		Result<BlogVo> res = blogService.selectByPrimaryKey(id);
		if ( !res.isSuccess() ) 
			return JSONResponse.error(-1, res.getMessage());
		
		Result<UserProfileVo> userProfileRes = userService.selectUserProfileByPrimaryKey(res.getData().getUserId());
		if ( !userProfileRes.isSuccess() )
			return JSONResponse.error(-1, userProfileRes.getMessage());

		// 添加浏览记录
		blogService.addReadCountByPrimaryKey(id);
		res.getData().setReadCount(res.getData().getReadCount() + 1);
		
		res.getData().setUserProfile(userProfileRes.getData());
		BlogDTO blogDTO = new BlogDTO();
		blogDTO.VoToMe(res.getData());
		
		if ( userId != null ) {
			Result<BlogVoteVo> blogVoteRes = voteService.getByBlogIdAndUserId(id, userId);
			if ( blogVoteRes.isSuccess() && blogDTO.getId() == blogVoteRes.getData().getBlogId() )
				blogDTO.setIsUp(blogVoteRes.getData().getIsUp());
		}
		
		return JSONResponse.ok(res.getMessage(), blogDTO);
	}
	
	@GetMapping
	public JSONResponse list(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "20") Integer size) {
		Result<PageData<BlogVo>> res = blogService.selectList(current, size);
		
		List<BlogDTO> list = new ArrayList<>();
		res.getData().getData().forEach(e -> {
			BlogDTO dto = new BlogDTO();
			dto.VoToMe(e);
			list.add(dto);
		});
		
		return JSONResponse.ok(null, PageData.transformType(list, res.getData()));
	}
	
}
