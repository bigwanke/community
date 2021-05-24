package com.zhoupb.community.entity.vo.problem;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.pojo.ProblemVote;
import com.zhoupb.community.entity.vo.BaseVo;
import com.zhoupb.community.entity.vo.user.UserProfileVo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProblemVoteVo extends ProblemVote implements BaseVo<ProblemVote> {
	
	private UserProfileVo userProfile = null;
	
	public ProblemVoteVo(Integer id, Integer problemId, Integer answerId, Boolean isUp, Boolean deleted,
			LocalDateTime createTime, LocalDateTime updateTime, Integer userId) {
		super(id, problemId, answerId, isUp, deleted, createTime, updateTime, userId);
	}

}
