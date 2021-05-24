package com.zhoupb.community.entity.dto.admin.share;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.share.ShareVoteVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareVoteDTO implements BaseDTO<ShareVoteVo>{
	
	private Integer id = null;
	
	private Integer shareId = null;
	
	private Integer replyId = null;
	
	private Boolean deleted = null;
	
	private UserProfileDTO user = null;
	
	
	@Override
	public void VoToMe(ShareVoteVo vo) {
		this.id = vo.getId();
		this.shareId = vo.getShareId();
		this.replyId = vo.getReplyId();
		this.deleted = vo.getDeleted();
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
	}
}
