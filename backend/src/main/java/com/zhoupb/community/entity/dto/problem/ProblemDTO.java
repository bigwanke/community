package com.zhoupb.community.entity.dto.problem;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.problem.ProblemVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemDTO implements BaseDTO<ProblemVo> {

	private Integer id = null;
	
	private String title = null;
	
	private String tags[] = null;
	
	private String contentHtml = null;
	
	private Integer readCount = null;
	
	private Integer voteCount = null;
	
	private Integer answerCount = null;
	
	private Boolean isSolved = null;

	private LocalDateTime createTime = null;
	
	private UserProfileDTO user = null;
	
	private Boolean isUp = null;
	
	@Override
	public void VoToMe(ProblemVo vo) {
		if ( vo == null ) return;
		this.id = vo.getId();
		this.title = vo.getTitle();
		this.tags = vo.getTags();
		this.contentHtml = vo.getContentHtml();
		this.readCount = vo.getReadCount();
		this.voteCount = vo.getVoteCount();
		this.answerCount = vo.getAnswerCount();
		this.isSolved = vo.getIsSolved();
		this.createTime = vo.getCreateTime();
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
	}
}
