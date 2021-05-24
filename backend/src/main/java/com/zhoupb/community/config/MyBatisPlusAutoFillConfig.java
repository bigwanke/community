package com.zhoupb.community.config;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

@Component
public class MyBatisPlusAutoFillConfig implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		this.strictInsertFill(metaObject, "emailActive", () -> false, Boolean.class);
		this.strictInsertFill(metaObject, "isAdmin", () -> false, Boolean.class);
		
		//share
		this.strictInsertFill(metaObject, "readCount", () -> 0, Integer.class);
		this.strictInsertFill(metaObject, "voteCount", () -> 0, Integer.class);
		this.strictInsertFill(metaObject, "replyCount", () -> 0, Integer.class);
		
		this.strictInsertFill(metaObject, "deleted", () -> false, Boolean.class);
		this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class);
		this.strictInsertFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);
		
		// problem
		this.strictInsertFill(metaObject, "answerCount", () -> 0, Integer.class);
		this.strictInsertFill(metaObject, "isSolved", () -> false, Boolean.class);
		this.strictInsertFill(metaObject, "isAccept", () -> false, Boolean.class);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.strictUpdateFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);
	}
	

	
}
