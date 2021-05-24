package com.zhoupb.community.entity.dto.share;

import java.time.LocalDateTime;

import com.zhoupb.community.entity.dto.BaseDTO;
import com.zhoupb.community.entity.dto.user.UserProfileDTO;
import com.zhoupb.community.entity.vo.share.ShareReplyVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareReplyDTO implements BaseDTO<ShareReplyVo>{
	
	private Integer id = null;
	
	private String contentMarkdown = null;
	
	private String contentHtml = null;
	
	private LocalDateTime createTime = null;
	
	private LocalDateTime updateTime  = null;
	
	private Integer parentId = null;
	
	private UserProfileDTO user  = null;
	
	private Integer shareId = null;
	
	private Boolean isUp = null;
	
	private Integer voteCount = null;
	
	@Override
	public void VoToMe(ShareReplyVo vo) {
		this.id = vo.getId();
		this.contentHtml = vo.getContentHtml();
		this.contentMarkdown = vo.getContentMarkdown();
		this.createTime = vo.getCreateTime();
		this.updateTime = vo.getUpdateTime();
		this.parentId = vo.getParentId();
		this.shareId = vo.getShareId();
		this.user = new UserProfileDTO();
		this.user.VoToMe(vo.getUserProfile());
		this.voteCount = vo.getVoteCount();
	}
}
