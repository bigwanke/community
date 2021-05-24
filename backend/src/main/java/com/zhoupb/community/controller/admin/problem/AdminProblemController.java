package com.zhoupb.community.controller.admin.problem;

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
import com.zhoupb.community.entity.dto.admin.problem.ProblemDTO;
import com.zhoupb.community.entity.vo.problem.ProblemVo;
import com.zhoupb.community.service.ProblemService;

@RestController
@RequestMapping("admin/")
public class AdminProblemController {
	@Autowired
	ProblemService problemService;
	
	@GetMapping("problem/")
	@AdminRequired
	public JSONResponse list(HttpServletRequest request,@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size) {
		Result<PageData<ProblemVo>> res = problemService.adminSelectProblem(current, size);
		
		List<ProblemDTO> list = new ArrayList<>();
		res.getData().getData().forEach(e -> {
			ProblemDTO dto = new ProblemDTO();
			dto.VoToMe(e);
			list.add(dto);
		});
		return JSONResponse.ok(null, PageData.transformType(list, res.getData()));
	}
	
	@DeleteMapping("problem/{id}/")
	public JSONResponse adminDeleteProblem(HttpServletRequest request, @PathVariable int id) {
		Result<?> res = problemService.adminDeleteProblemById(id);
		return JSONResponse.ok(res.getMessage());
		
	}
}
