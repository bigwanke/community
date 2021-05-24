package com.zhoupb.community.controller.admin.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhoupb.community.annotation.AdminRequired;
import com.zhoupb.community.common.JSONResponse;
import com.zhoupb.community.common.PageData;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.entity.dto.admin.user.UserDTO;

import com.zhoupb.community.entity.vo.user.UserProfileVo;
import com.zhoupb.community.service.UserService;

@RestController
@RequestMapping("admin/")
public class AdminUserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("getList/")
	@AdminRequired
	public JSONResponse list(HttpServletRequest request,@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size) {
		Result<PageData<UserProfileVo>> res = userService.adminSelectUserList(current, size);
		
		List<UserDTO> list = new ArrayList<>();
		res.getData().getData().forEach(e -> {
			UserDTO dto = new UserDTO();
			dto.VoToMe(e);
			list.add(dto);
		});
		return JSONResponse.ok(null, PageData.transformType(list, res.getData()));
	}
	
	@DeleteMapping("user/delete/{id}/")
	public JSONResponse adminDeleteUser(HttpServletRequest request, @PathVariable int id) {
		Result<?> res = userService.adminDeleteUserById(id);
		return JSONResponse.ok(res.getMessage());
		
	}
	@GetMapping("user/update/{id}/")
	public JSONResponse adminUpdateUser(HttpServletRequest request, @PathVariable int id) {
		Result<?> res = userService.adminUpdateUserById(id);
		return JSONResponse.ok(res.getMessage());
	}
}
