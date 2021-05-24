package com.zhoupb.community.entity.vo.share;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.pojo.ShareVote;
import com.zhoupb.community.entity.vo.BaseVo;
import com.zhoupb.community.entity.vo.user.UserProfileVo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShareVoteVo extends ShareVote implements BaseVo<ShareVote>{
	
	private UserProfileVo userProfile = null;
	
	public ShareVoteVo(Integer id, Integer shareId, Integer replyId, Boolean isUp, LocalDateTime createTime,
			LocalDateTime updateTime, Integer userId, Boolean deleted) {
		super(id, shareId, replyId, isUp, createTime, updateTime, userId, deleted);
	}
	

	
}