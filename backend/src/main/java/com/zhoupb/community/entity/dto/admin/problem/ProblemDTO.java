package com.zhoupb.community.entity.dto.admin.problem;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.problem.ProblemVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemDTO implements BaseDTO<ProblemVo>{
	
	private Integer id = null;
	
	private String title = null;
	
	private String tags[] = null;
	
	private String contentMarkdown = null;
	
	private Integer readCount = null;
	
	private Integer voteCount = null;
	
	private Integer answerCount = null;
	
	private Boolean deleted = null;
	
	private UserProfileDTO user = null;
	
	@Override
	public void VoToMe(ProblemVo vo) {
		this.id = vo.getId();
		this.title = vo.getTitle();
		this.tags = vo.getTags();
		this.contentMarkdown = vo.getContentMarkdown();
		this.readCount = vo.getReadCount();
		this.voteCount = vo.getVoteCount();
		this.answerCount = vo.getAnswerCount();
		this.deleted = vo.getDeleted();
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
	}
	
	
}
