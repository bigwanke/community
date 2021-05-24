package com.zhoupb.community.entity.pojo;

import java.time.LocalDateTime;

import org.apache.ibatis.type.ArrayTypeHandler;

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
@TableName(autoResultMap = true)
public class Blog {
	
	@TableId(type = IdType.AUTO)
	private Integer id = null;
	
	private String title = null;
	
	@TableField(typeHandler = ArrayTypeHandler.class)
	private String tags[] = null;
	
	private String contentMarkdown = null;
	
	private String contentHtml = null;
	
	private String coverImage = null;
	
	private String description = null;
	
	@TableField(fill = FieldFill.INSERT)
	private Integer readCount = null;
	
	@TableField(fill = FieldFill.INSERT)
	private Integer voteCount = null;
	
	@TableField(fill = FieldFill.INSERT)
	private Integer replyCount = null;
	
	@TableField(fill = FieldFill.INSERT)
	private Boolean deleted = null;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime = null;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime = null;

	private Integer userId = null;
 
}