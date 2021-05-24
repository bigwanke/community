package com.zhoupb.community.entity.dto.admin.share;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.share.ShareReplyVo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShareReplyDTO implements BaseDTO<ShareReplyVo>{
	
	private Integer id = null;
	
	private String contentMarkdown = null;
	
	private Integer voteCount = null;
	
	private Boolean deleted = null;
	
	private UserProfileDTO user = null;
	
	
	@Override
	public void VoToMe(ShareReplyVo vo) {
		this.id = vo.getId();
		this.contentMarkdown = vo.getContentMarkdown();
		this.voteCount = vo.getVoteCount();
		this.deleted = vo.getDeleted();
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
	}
}
