package com.zhoupb.community.controller.share;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhoupb.community.common.JSONResponse;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.dto.share.ShareDTO;
import com.zhoupb.community.entity.vo.share.ShareVo;
import com.zhoupb.community.entity.vo.share.ShareVoteVo;
import com.zhoupb.community.entity.vo.user.UserProfileVo;
import com.zhoupb.community.service.ShareService;
import com.zhoupb.community.service.ShareVoteService;
import com.zhoupb.community.service.UserService;

@RestController
@RequestMapping("share/")
public class ShareController {
	
	@Autowired
	private ShareService shareService = null;
	
	@Autowired
	private UserService userService = null;
	
	@Autowired
	private ShareVoteService shareVoteService = null;
	
	//根据id查
	@GetMapping("{id}/")
	public JSONResponse get(@PathVariable int id, @RequestParam(required = false) Integer userId) {
		Result<ShareVo> res = shareService.selectByPrimaryKey(id);
		if ( !res.isSuccess() ) 
			return JSONResponse.error(-1, res.getMessage());
		
		Result<UserProfileVo> userProfileRes = userService.selectUserProfileByPrimaryKey(res.getData().getUserId());
		if ( !userProfileRes.isSuccess() )
			return JSONResponse.error(-1, userProfileRes.getMessage());

		// 添加浏览记录
		shareService.addReadCountByPrimaryKey(id);
		res.getData().setReadCount(res.getData().getReadCount() + 1);
		
		res.getData().setUserProfile(userProfileRes.getData());
		ShareDTO shareDTO = new ShareDTO();
		shareDTO.VoToMe(res.getData());
		
		if ( userId != null ) {
			Result<ShareVoteVo> shareVoteRes = shareVoteService.getByShareIdAndUserId(id, userId);
			if ( shareVoteRes.isSuccess() && shareDTO.getId() == shareVoteRes.getData().getShareId() )
				shareDTO.setIsUp(shareVoteRes.getData().getIsUp());
		}
		
		return JSONResponse.ok(res.getMessage(), shareDTO);
	}
	
	//查所有
	@GetMapping
	public JSONResponse list(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "20") Integer size) {
		Result<PageData<ShareVo>> res = shareService.selectList(current, size);
		
		List<ShareDTO> list = new ArrayList<>();
		res.getData().getData().forEach(e -> {
			ShareDTO dto = new ShareDTO();
			dto.VoToMe(e);
			list.add(dto);
		});
		
		return JSONResponse.ok(null, PageData.transformType(list, res.getData()));
	}
	
}
