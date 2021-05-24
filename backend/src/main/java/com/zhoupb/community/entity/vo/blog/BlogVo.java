package com.zhoupb.community.entity.vo.blog;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.pojo.Blog;
import com.zhoupb.community.entity.vo.BaseVo;
import com.zhoupb.community.entity.vo.user.UserProfileVo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BlogVo extends Blog implements BaseVo<Blog> {
	
	private UserProfileVo userProfile = null;
	
	public BlogVo(Integer id, String title, String[] tags, String contentMarkdown, 
			String contentHtml, String coverImage, String description, Integer readCount,
			Integer voteCount, Integer replyCount, Boolean deleted, LocalDateTime createTime, LocalDateTime updateTime,
			Integer userId) {
		super(id, title, tags, contentMarkdown, contentHtml, coverImage, description, readCount, voteCount, replyCount, deleted, createTime, updateTime, userId);
	}

	@Override
	public void POJOtoMe(Blog pojo) {
		this.setId(pojo.getId());
		this.setTitle(pojo.getTitle());
		this.setTags(pojo.getTags());
		this.setContentMarkdown(pojo.getContentMarkdown());
		this.setContentHtml(pojo.getContentHtml());
		this.setCoverImage(pojo.getCoverImage());
		this.setDescription(pojo.getDescription());
		this.setReadCount(pojo.getReadCount());
		this.setVoteCount(pojo.getVoteCount());
		this.setReplyCount(pojo.getReplyCount());
		this.setDeleted(pojo.getDeleted());
		this.setCreateTime(pojo.getCreateTime());
		this.setUpdateTime(pojo.getUpdateTime());
		this.setUserId(pojo.getUserId());
	}
	
}
