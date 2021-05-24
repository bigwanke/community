package com.zhoupb.community.entity.pojo;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("\"user\"")
public class User {

	@TableId(type = IdType.AUTO)
	private Integer id = null;

	private String username = null;

	private String password = null;

	private String email = null;

	@TableField(fill = FieldFill.INSERT)
	private Boolean emailActive = null;

	@TableField(fill = FieldFill.INSERT)
	private Boolean isAdmin = null;
	
	@TableField(fill = FieldFill.INSERT)
	private Boolean deleted = null;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime = null;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime = null;

}
