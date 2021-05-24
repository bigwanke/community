package com.zhoupb.community.entity.dto.problem;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.problem.ProblemAnswerVo;
import com.zhoupb.validation.EnableValidator;
import com.zhoupb.validation.annotation.Required;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EnableValidator
public class ProblemAnswerPutDTO implements BaseDTO<ProblemAnswerVo> {

	@Required(message = "id is null")
	private Integer id = null;
	
	@Required(message = "problemId is null")
	private Integer problemId = null;
	
	@Required(message = "accept is null")
	private Boolean isAccept = null;
	
	private Integer userId = null;
	
	@Override
	public ProblemAnswerVo toVo() {
		return new ProblemAnswerVo(id, null, null, null, isAccept, null, null, null, userId, problemId);
	}
	
}
