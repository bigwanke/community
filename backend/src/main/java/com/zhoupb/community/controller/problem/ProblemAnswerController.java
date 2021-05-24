package com.zhoupb.community.controller.problem;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhoupb.community.annotation.LoginRequired;
import com.zhoupb.community.common.Common;
import com.zhoupb.community.common.JSONResponse;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.dto.problem.ProblemAnswerDTO;
import com.zhoupb.community.entity.dto.problem.ProblemAnswerPostDTO;
import com.zhoupb.community.entity.dto.problem.ProblemAnswerPutDTO;
import com.zhoupb.community.entity.vo.problem.ProblemAnswerVo;
import com.zhoupb.community.entity.vo.problem.ProblemVoteVo;
import com.zhoupb.community.service.ProblemAnswerService;
import com.zhoupb.community.service.ProblemVoteService;
import com.zhoupb.validation.Validation;

@RestController
@RequestMapping("problem/answer/")
public class ProblemAnswerController {
	
	@Autowired
	private ProblemAnswerService answerService = null;
	
	@Autowired
	private ProblemVoteService voteService = null;
	
	@PostMapping
	@LoginRequired
	@Validation
	public JSONResponse post(HttpServletRequest request, @RequestBody ProblemAnswerPostDTO answer) {
		answer.setUserId((Integer) request.getAttribute(Common.REQ_USER_ID));
		
		Result<?> res = answerService.post(answer.toVo());
		if ( res.isSuccess() ) return JSONResponse.ok(res.getMessage());
		return JSONResponse.error(-1, res.getMessage());
	}

	@GetMapping
	public JSONResponse list(@RequestParam Integer problemId,
			@RequestParam(required = false) Integer userId,
			@RequestParam(defaultValue = Common.PAGE_CURRENT_DEFAULT) Integer current, 
			@RequestParam(defaultValue = Common.PAGE_SIZE_DEFAULT) Integer size) {
		Result<PageData<ProblemAnswerVo>> res = answerService.listByProblemId(current, size, problemId);
		if ( !res.isSuccess() ) return JSONResponse.error(-1, res.getMessage());
		List<ProblemAnswerDTO> list = new ArrayList<>();
		List<Integer> ids = new ArrayList<>();
		res.getData().getData().forEach( e -> {
			ProblemAnswerDTO t = new ProblemAnswerDTO();
			t.VoToMe(e);
			list.add(t);
			ids.add(e.getId());
		});
		
		if ( userId != null ) {
			Result<List<ProblemVoteVo>> voteVosRes = voteService.listByAnswerIdsAndUserId(ids, userId);
			if ( voteVosRes.isSuccess() )
				list.forEach(e -> {
					ProblemVoteVo t = null;
					if ( ( t = isContains(voteVosRes.getData(), e.getId()) ) != null )
						e.setIsUp(t.getIsUp());
				});
		}
		
		return JSONResponse.ok(res.getMessage(), PageData.transformType(list, res.getData()));
	}
	
	@PutMapping
	@LoginRequired
	@Validation
	public JSONResponse put(HttpServletRequest request, @RequestBody ProblemAnswerPutDTO answer) {
		Result<?> res = answerService.updateAccept(answer.toVo());
		if ( res.isSuccess() ) return JSONResponse.ok(res.getMessage());
		return JSONResponse.error(-1, res.getMessage());
	}
	
	private ProblemVoteVo isContains(List<ProblemVoteVo> list, int answerId) {
		for ( ProblemVoteVo t : list )
			if ( t.getAnswerId() == answerId ) return t;
		return null;
	}
	
	@DeleteMapping("{id}/")
	@LoginRequired
	public JSONResponse delete(HttpServletRequest request, @PathVariable int id) {
		Result<?> res = answerService.deleteByPrimaryKeyAndUserId(id, (int) request.getAttribute(Common.REQ_USER_ID));
		if ( res.isSuccess() ) return JSONResponse.ok(res.getMessage());
		return JSONResponse.error(-1, res.getMessage());
	}
	
}
