package com.zhoupb.community.controller.problem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhoupb.community.common.Common;
import com.zhoupb.community.common.JSONResponse;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.dto.problem.ProblemDTO;
import com.zhoupb.community.entity.vo.problem.ProblemVo;
import com.zhoupb.community.entity.vo.problem.ProblemVoteVo;
import com.zhoupb.community.entity.vo.user.UserProfileVo;
import com.zhoupb.community.service.ProblemService;
import com.zhoupb.community.service.ProblemVoteService;
import com.zhoupb.community.service.UserService;

@RestController
@RequestMapping("problem/")
public class ProblemController {

	@Autowired
	private ProblemService problemService = null;
	
	@Autowired
	private UserService userService = null;
	
	@Autowired
	private ProblemVoteService voteService = null;
	
	@GetMapping
	public JSONResponse list(
			@RequestParam(defaultValue = Common.PAGE_CURRENT_DEFAULT) Integer current, 
			@RequestParam(defaultValue = Common.PAGE_SIZE_DEFAULT) Integer size) {
		Result<PageData<ProblemVo>> res = problemService.selectList(current, size);
		if ( !res.isSuccess() ) return JSONResponse.error(-1, res.getMessage());
		
		List<ProblemDTO> list = new ArrayList<ProblemDTO>();
		res.getData().getData().forEach(e -> {
			ProblemDTO t = new ProblemDTO();
			t.VoToMe(e);
			list.add(t);
		});
		return JSONResponse.ok(res.getMessage(), PageData.transformType(list, res.getData())); 
	}
	
	@GetMapping("{id}/")
	private JSONResponse get(@PathVariable Integer id, @RequestParam(required = false) Integer userId) {
		Result<ProblemVo> res = problemService.selectByPrimaryKey(id);
		if ( !res.isSuccess() ) 
			return JSONResponse.error(-1, res.getMessage());
		
		Result<UserProfileVo> userProfileRes = userService.selectUserProfileByPrimaryKey(res.getData().getUserId());
		if ( !userProfileRes.isSuccess() )
			return JSONResponse.error(-1, userProfileRes.getMessage());
		
		//	添加记录
		problemService.addReadCountByPrimaryKey(id);
		res.getData().setUserProfile(userProfileRes.getData());
		
		ProblemDTO problemDTO = new ProblemDTO();
		problemDTO.VoToMe(res.getData());
		
		problemDTO.setReadCount(problemDTO.getReadCount());
		
		// isUp?
		if ( userId != null ) {
			Result<ProblemVoteVo> voteRes = voteService.getByProblemIdAndUserId(id, userId);
			if ( voteRes.isSuccess() && voteRes.getData().getProblemId() == id )
				problemDTO.setIsUp(voteRes.getData().getIsUp());
		}
		
		return JSONResponse.ok(res.getMessage(), problemDTO);
	}
	
}
