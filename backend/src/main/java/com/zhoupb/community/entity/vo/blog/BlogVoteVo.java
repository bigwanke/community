package com.zhoupb.community.entity.vo.blog;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.pojo.BlogVote;
import com.zhoupb.community.entity.vo.BaseVo;
import com.zhoupb.community.entity.vo.user.UserProfileVo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BlogVoteVo extends BlogVote implements BaseVo<BlogVote> {
	
	private UserProfileVo userProfile = null;

	public BlogVoteVo(Integer id, Integer blogId, Integer replyId, Boolean isUp, Boolean deleted,
			LocalDateTime createTime, LocalDateTime updateTime, Integer userId) {
		super(id, blogId, replyId, isUp, deleted, createTime, updateTime, userId);
	}
	
	

}
