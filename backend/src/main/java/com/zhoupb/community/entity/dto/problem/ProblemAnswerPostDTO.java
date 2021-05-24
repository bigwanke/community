package com.zhoupb.community.entity.dto.problem;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.problem.ProblemAnswerVo;
import com.zhoupb.validation.EnableValidator;
import com.zhoupb.validation.annotation.NotBlank;
import com.zhoupb.validation.annotation.Required;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EnableValidator
public class ProblemAnswerPostDTO implements BaseDTO<ProblemAnswerVo> {

	@Required(message = "先输入内容叭. 😅. m")
	@NotBlank(message = "先输入内容叭. 😅. m")
	private String contentMarkdown = null;
	
	@Required(message = "先输入内容叭. 😅. h")
	@NotBlank(message = "先输入内容叭. 😅. h")
	private String contentHtml = null;
	
	private Integer userId = null;
	
	@Required(message = "你回复的是哪个问题呢? 🤔")
	private Integer problemId = null;
	
	@Override
	public ProblemAnswerVo toVo() {
		return new ProblemAnswerVo(null, contentMarkdown, contentHtml, null, null, null, null, null, userId, problemId);
	}
	
}
