package com.zhoupb.community.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.zhoupb.community.entity.dto.user.UserProblemDTO;
import com.zhoupb.community.entity.dto.user.UserProblemPostDTO;
import com.zhoupb.community.entity.vo.problem.ProblemVo;
import com.zhoupb.community.service.ProblemService;
import com.zhoupb.validation.Validation;

@RestController
@RequestMapping("user/problem/")
public class UserProblemController {

	@Autowired
	private ProblemService problemService = null;
	
	@PostMapping
	@LoginRequired
	@Validation
	public JSONResponse post(HttpServletRequest request ,@RequestBody UserProblemPostDTO problem) {
		problem.setUserId((Integer) request.getAttribute(Common.REQ_USER_ID));
		
		Result<?> res = problemService.post(problem.toVo());
		if ( res.isSuccess() )
			return JSONResponse.ok(res.getMessage());
		return JSONResponse.error(-1, res.getMessage());
	}
	
	@GetMapping("{userId}/")
	public JSONResponse list(@RequestParam Integer current, @RequestParam Integer size, @PathVariable Integer userId) {
		Result<PageData<ProblemVo>> res = problemService.selectByUserId(userId, current, size);
		if ( !res.isSuccess() )
			return JSONResponse.error(-1, res.getMessage());
		List<UserProblemDTO> data = new ArrayList<>();
		
		res.getData().getData().forEach(e -> {
			UserProblemDTO t = new UserProblemDTO();
			t.VoToMe(e);
			data.add(t);
		});
		
		return JSONResponse.ok(res.getMessage(), PageData.transformType(data, res.getData()));
	}
	
}
