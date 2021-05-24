package com.zhoupb.community.controller.admin.share;

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
import com.zhoupb.community.entity.dto.admin.share.ShareVoteDTO;
import com.zhoupb.community.entity.vo.share.ShareVoteVo;
import com.zhoupb.community.service.ShareVoteService;

@RestController
@RequestMapping("admin/")
public class AdminShareVoteController {
	@Autowired
	private ShareVoteService shareVoteService;
	
	@GetMapping("share/vote/")
	@AdminRequired
	public JSONResponse list(HttpServletRequest request,@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size) {
		Result<PageData<ShareVoteVo>> res = shareVoteService.adminSelectShareVote(current, size);
		
		List<ShareVoteDTO> list = new ArrayList<>();
		res.getData().getData().forEach(e -> {
			ShareVoteDTO dto = new ShareVoteDTO();
			dto.VoToMe(e);
			list.add(dto);
		});
		return JSONResponse.ok(null, PageData.transformType(list, res.getData()));
	}
	
	@DeleteMapping("share/vote/{id}/")
	public JSONResponse adminDeleteShareVote(HttpServletRequest request, @PathVariable int id) {
		Result<?> res = shareVoteService.adminDeleteShareVoteById(id);
		return JSONResponse.ok(res.getMessage());
		
	}
}
