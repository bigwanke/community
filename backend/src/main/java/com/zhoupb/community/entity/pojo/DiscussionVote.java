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
@AllArgsConstructor
@NoArgsConstructor
public class DiscussionVote {
	
	@TableId(type = IdType.AUTO)
	private Integer id = null;
	
	private Integer discussionId = null;
	
	private Integer replyId = null;
	
	private Boolean isUp = null;
	
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime = null;
	
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime updateTime = null;
	
	private Integer userId = null;
	
}
