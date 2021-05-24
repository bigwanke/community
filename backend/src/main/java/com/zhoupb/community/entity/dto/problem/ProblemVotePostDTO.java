package com.zhoupb.community.entity.dto.problem;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.problem.ProblemVoteVo;
import com.zhoupb.validation.EnableValidator;
import com.zhoupb.validation.annotation.Required;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EnableValidator
public class ProblemVotePostDTO implements BaseDTO<ProblemVoteVo> {

	@Required(message = "problemId is null")
	private Integer problemId = null;
	
	@Required(message = "answerId is null")
	private Integer answerId = null;
	
	@Required(message = "'is up' is null")
	private Boolean isUp = null;
	
	private Integer userId = null;

	@Override
	public ProblemVoteVo toVo() {
		return new ProblemVoteVo(null, problemId, answerId, isUp, null, null, null, userId);
	}
	
}
