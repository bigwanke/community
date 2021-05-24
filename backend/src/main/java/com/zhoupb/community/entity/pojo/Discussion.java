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
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Discussion {
	@TableId(type = IdType.AUTO)
	private Integer id = null;
	
	private String contentHtml = null;
	
	private String contentMarkdown = null;
	
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
	
	private String title = null;
	
	private String description = null;
	
	private Integer lastReplyId = null;

}
