package com.zhoupb.community.entity.dto.problem;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.problem.ProblemAnswerVo;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class ProblemAnswerDTO implements BaseDTO<ProblemAnswerVo> {

	private Integer id = null;
	
	private String contentHtml = null;
	
	private Integer voteCount = null;
	
	private Boolean isAccept = null;

	private LocalDateTime createTime = null;

	private UserProfileDTO user = null;
	
	private Boolean isUp = null;
	
	@Override
	public void VoToMe(ProblemAnswerVo vo) {
		if ( vo == null ) return;
		
		this.id = vo.getId();
		this.contentHtml = vo.getContentHtml();
		this.voteCount = vo.getVoteCount();
		this.isAccept = vo.getIsAccept();
		this.createTime = vo.getCreateTime();
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
	}
	
}
