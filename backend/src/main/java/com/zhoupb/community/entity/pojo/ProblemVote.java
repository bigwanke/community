package com.zhoupb.community.entity.pojo;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProblemVote {

	@TableId(type = IdType.AUTO)
	private Integer id = null;
	
	private Integer problemId = null;
	
	private Integer answerId = null;
	
	private Boolean isUp = null;
	
	@TableField(fill = FieldFill.INSERT)
	private Boolean deleted = null;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime = null;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime = null;

	private Integer userId = null;
	
}
