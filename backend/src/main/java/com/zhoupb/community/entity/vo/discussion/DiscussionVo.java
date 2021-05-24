package com.zhoupb.community.entity.vo.discussion;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.pojo.Discussion;
import com.zhoupb.community.entity.vo.BaseVo;
import com.zhoupb.community.entity.vo.user.UserProfileVo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiscussionVo extends Discussion implements BaseVo<Discussion> {


	private  UserProfileVo userProfile = null;
	
	private DiscussionReplyVo reply = null;
	
	public DiscussionVo(Integer id, String contentHtml, String contentMarkdown, Integer readCount, Integer voteCount,
			Integer replyCount, Boolean deleted, LocalDateTime createTime, LocalDateTime updateTime, Integer userId,
			String title, String description, Integer lastReplyId) {
		super(id, contentHtml, contentMarkdown, readCount, voteCount, replyCount, deleted, createTime, updateTime, userId,
				title, description, lastReplyId);
	}

	@Override
	public void POJOtoMe(Discussion pojo) {
		this.setContentHtml(pojo.getContentHtml());
		this.setContentMarkdown(pojo.getContentMarkdown());
		this.setCreateTime(pojo.getCreateTime());
		this.setUpdateTime(pojo.getUpdateTime());
		this.setDeleted(pojo.getDeleted());
		this.setId(pojo.getId());
		this.setReadCount(pojo.getReadCount());
		this.setReplyCount(pojo.getReplyCount());
		this.setUserId(pojo.getUserId());
		this.setVoteCount(pojo.getVoteCount());
		this.setDescription(pojo.getDescription());
		this.setTitle(pojo.getTitle());
		this.setLastReplyId(pojo.getLastReplyId());
	}
	
}
