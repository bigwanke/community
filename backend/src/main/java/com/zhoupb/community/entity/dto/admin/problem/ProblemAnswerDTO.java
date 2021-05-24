package com.zhoupb.community.entity.dto.admin.problem;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.problem.ProblemAnswerVo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProblemAnswerDTO implements BaseDTO<ProblemAnswerVo>{
	private Integer id = null;
	
	private String contentMarkdown = null;
	
	private Integer voteCount = null;
	
	private Boolean deleted = null;
	
	private UserProfileDTO user = null;
	
	@Override
	public void VoToMe(ProblemAnswerVo vo) {
		this.id = vo.getId();
		this.contentMarkdown = vo.getContentMarkdown();
		this.voteCount = vo.getVoteCount();
		this.deleted = vo.getDeleted();
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
	}
	
	
}
