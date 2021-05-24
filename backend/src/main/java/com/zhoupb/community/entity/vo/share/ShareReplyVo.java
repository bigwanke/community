package com.zhoupb.community.entity.vo.share;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.pojo.ShareReply;
import com.zhoupb.community.entity.vo.BaseVo;
import com.zhoupb.community.entity.vo.user.UserProfileVo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShareReplyVo extends ShareReply implements BaseVo<ShareReply>{
	
	private UserProfileVo userProfile = null;
	
	public ShareReplyVo(Integer id, String contentMarkdown, String contentHtml, Integer parentId, Integer voteCount,
			Boolean deleted, LocalDateTime createTime, LocalDateTime updateTime, Integer userId, Integer shareId) {
		super(id, contentMarkdown, contentHtml, parentId, voteCount, deleted, createTime, updateTime, userId, shareId);
	}






}
