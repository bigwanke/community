package com.zhoupb.community.entity.vo.problem;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.pojo.Problem;
import com.zhoupb.community.entity.vo.BaseVo;
import com.zhoupb.community.entity.vo.user.UserProfileVo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProblemVo extends Problem implements BaseVo<Problem> {

	private UserProfileVo userProfile = null;
	
	public ProblemVo(Integer id, String title, String[] tags, String contentMarkdown, String contentHtml,
			Integer readCount, Integer voteCount, Integer answerCount, Boolean isSolved, Boolean deleted,
			LocalDateTime createTime, LocalDateTime updateTime, Integer userId) {
		super(id, title, tags, contentMarkdown, contentHtml, readCount, voteCount, answerCount, isSolved, deleted, createTime,
				updateTime, userId);
	}
	
	@Override
	public void POJOtoMe(Problem pojo) {
		this.setId(pojo.getId());
		this.setTitle(pojo.getTitle());
		this.setTags(pojo.getTags());
		this.setContentMarkdown(pojo.getContentMarkdown());
		this.setContentHtml(pojo.getContentHtml());
		this.setReadCount(pojo.getReadCount());
		this.setVoteCount(pojo.getVoteCount());
		this.setAnswerCount(pojo.getAnswerCount());
		this.setIsSolved(pojo.getIsSolved());
		this.setDeleted(pojo.getDeleted());
		this.setCreateTime(pojo.getCreateTime());
		this.setUpdateTime(pojo.getUpdateTime());
		this.setUserId(pojo.getUserId());
	}
	
}
