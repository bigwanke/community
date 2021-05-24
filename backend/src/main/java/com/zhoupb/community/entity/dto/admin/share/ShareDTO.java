package com.zhoupb.community.entity.dto.admin.share;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.share.ShareVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareDTO implements BaseDTO<ShareVo>{
	
	private Integer id = null;
	
	private String contentMarkdown = null;
	
	private Integer readCount = null;
	
	private Integer voteCount = null;
	
	private Integer replyCount = null;
	
	private Boolean deleted = null;
	
	private UserProfileDTO user = null;
	
	
	@Override
	public void VoToMe(ShareVo vo) {
		this.id = vo.getId();
		this.contentMarkdown = vo.getContentMarkdown();
		this.readCount = vo.getReadCount();
		this.voteCount = vo.getVoteCount();
		this.replyCount = vo.getReplyCount();
		this.deleted = vo.getDeleted();
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
	}
}
