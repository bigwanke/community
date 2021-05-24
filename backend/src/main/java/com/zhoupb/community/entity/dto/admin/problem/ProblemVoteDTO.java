package com.zhoupb.community.entity.dto.admin.problem;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.problem.ProblemVoteVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemVoteDTO implements BaseDTO<ProblemVoteVo>{
	private Integer id = null;
	
	private Integer problemId = null;
	
	private Integer answerId = null;
	
	private Boolean deleted = null;
	
	private UserProfileDTO user = null;
	
	@Override
	public void VoToMe(ProblemVoteVo vo) {
		this.id = vo.getId();
		this.problemId = vo.getProblemId();
		this.answerId = vo.getAnswerId();
		this.deleted = vo.getDeleted();
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
	}
}
