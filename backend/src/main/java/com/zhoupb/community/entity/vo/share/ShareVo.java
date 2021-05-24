package com.zhoupb.community.entity.vo.share;


import java.time.LocalDateTime;

import com.zhoupb.community.entity.pojo.Share;
import com.zhoupb.community.entity.vo.BaseVo;
import com.zhoupb.community.entity.vo.user.UserProfileVo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShareVo extends Share implements BaseVo<Share> {
	
	private UserProfileVo userProfile = null;
		
	public ShareVo(Integer id, String contentHtml, String contentMarkdown, Integer readCount, Integer voteCount,
			Integer replyCount, Boolean deleted, LocalDateTime createTime, LocalDateTime updateTime, Integer userId,
			String description) {
		super(id, contentHtml, contentMarkdown, readCount, voteCount, replyCount, deleted, createTime, updateTime, userId,
				description);
	}

	
	@Override
	public void POJOtoMe(Share pojo) {
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
	}
}
