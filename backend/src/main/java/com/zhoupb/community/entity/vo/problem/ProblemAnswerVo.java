package com.zhoupb.community.entity.vo.problem;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.pojo.ProblemAnswer;
import com.zhoupb.community.entity.vo.BaseVo;
import com.zhoupb.community.entity.vo.user.UserProfileVo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProblemAnswerVo extends ProblemAnswer implements BaseVo<ProblemAnswer> {
	
	private UserProfileVo userProfile = null;
	
	public ProblemAnswerVo(Integer id, String contentMarkdown, String contentHtml, Integer voteCount, Boolean isAccept,
			Boolean deleted, LocalDateTime createTime, LocalDateTime updateTime, Integer userId, Integer problemId) {
		super(id, contentMarkdown, contentHtml, voteCount, isAccept, deleted, createTime, updateTime, userId, problemId);
	}
	
}
