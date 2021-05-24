package com.zhoupb.community.entity.dto.user;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.vo.problem.ProblemVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProblemDTO implements BaseDTO<ProblemVo> {
	
	private Integer id = null;
	
	private String title = null;
	
	private String tags[] = null;
	
	private Integer readCount = null;
	
	private Integer voteCount = null;
	
	private Integer answerCount = null;
	
	private Boolean isSolved = null;

	private LocalDateTime createTime = null;

	@Override
	public void VoToMe(ProblemVo vo) {
		if ( vo == null ) return;
		this.id = vo.getId();
		this.title = vo.getTitle();
		this.tags = vo.getTags();
		this.readCount = vo.getReadCount();
		this.voteCount = vo.getVoteCount();
		this.answerCount = vo.getAnswerCount();
		this.isSolved = vo.getIsSolved();
		this.createTime = vo.getCreateTime();
	}

}
