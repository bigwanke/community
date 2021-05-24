package com.zhoupb.community.entity.vo.discussion;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.pojo.DiscussionVote;
import com.zhoupb.community.entity.vo.BaseVo;
import com.zhoupb.community.entity.vo.user.UserProfileVo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiscussionVoteVo extends DiscussionVote implements BaseVo<DiscussionVote>{
	
	private UserProfileVo userProfile = null;

	public DiscussionVoteVo(Integer id, Integer discussionId, Integer replyId, Boolean isUp, LocalDateTime createTime,
			LocalDateTime updateTime, Integer userId) {
		super(id, discussionId, replyId, isUp, createTime, updateTime, userId);
	}
	
	
}
