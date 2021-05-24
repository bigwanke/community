package com.zhoupb.community.entity.dto.user;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.problem.ProblemVo;
import com.zhoupb.validation.EnableValidator;
import com.zhoupb.validation.annotation.ArrayElementNotBlank;
import com.zhoupb.validation.annotation.ArrayRequired;
import com.zhoupb.validation.annotation.NotBlank;
import com.zhoupb.validation.annotation.Required;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EnableValidator
public class UserProblemPostDTO implements BaseDTO<ProblemVo> {

	@Required(message = "必须要输入标题")
	@NotBlank(message = "标题不能为空")
	private String title = null;

	@ArrayRequired(message = "至少要输入一个标签")
	@ArrayElementNotBlank(message = "第[%d]个标签不能是空的哦")
	private String tags[] = null;
	
	@Required(message = "必须要填写内容哦. m")
	@NotBlank(message = "内容不能为空哦. m")
	private String contentMarkdown = null;
	
	@Required(message = "必须要填写内容哦. h")
	@NotBlank(message = "内容不能为空哦. h")
	private String contentHtml = null;
	
	private Integer userId = null;
	
	@Override
	public ProblemVo toVo() {
		return new ProblemVo(null, title, tags, contentMarkdown, contentHtml, null, null, null, null, null, null, null, userId);
	}
	
}
