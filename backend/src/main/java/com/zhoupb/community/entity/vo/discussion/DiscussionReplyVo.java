package com.zhoupb.community.entity.vo.discussion;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.pojo.DiscussionReply;
import com.zhoupb.community.entity.vo.BaseVo;
import com.zhoupb.community.entity.vo.user.UserProfileVo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiscussionReplyVo extends DiscussionReply implements BaseVo<DiscussionReply>{
	
	private UserProfileVo userProfile = null;
	
	public DiscussionReplyVo(Integer id, String contentHtml, String contentMarkdown, Integer parentId,
			Integer voteCount, Boolean deleted, LocalDateTime createTime, LocalDateTime updateTime, Integer userId,
			Integer discussionId) {
		super(id, contentHtml, contentMarkdown, parentId, voteCount, deleted, createTime, updateTime, userId, discussionId);
	}


	@Override
	public void POJOtoMe(DiscussionReply pojo) {
		this.setId(pojo.getId());
		this.setContentMarkdown(pojo.getContentMarkdown());
		this.setContentHtml(pojo.getContentHtml());
		this.setVoteCount(pojo.getVoteCount());
		this.setDeleted(pojo.getDeleted());
		this.setCreateTime(pojo.getCreateTime());
		this.setUpdateTime(pojo.getUpdateTime());
		this.setUserId(pojo.getUserId());
		this.setDiscussionId(pojo.getDiscussionId());
		this.setParentId(pojo.getParentId());
	}
	
}
