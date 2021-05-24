package com.zhoupb.community.entity.vo;

/**
 * dto  作为客户端与服务端传输数据的实体类
 * pojo 作为与数据库交互的实体类, 一个pojo对应一个表
 * 
 * 如果要多表关联查询, 就要在pojo加一个非数据库字段的属性, 
 *     这样就破坏一个pojo对应一个表的结构了
 * 解决: 创建对应的 vo
 * 
 * 因为有些敏感字段(如密码)是不传给客户端的
 * 所以vo也不适合直接传给客户端
 * 解决: 创建对应的 dto
 * 
 * vo作为中间层
 * 
 * dto <--------> [vo <-----> pojo]
 * controller --> [service -> mapper]
 * -------------------------------
 * dto <--------> [vo ------- vo]
 * controller <-- [service <- mapper]
 * 
 * @author zhoupb
 *
 * @param <P> pojo 类型
 */ 
public interface BaseVo<P> {
	
	public default P toPOJO() {
		return null;
	}
	
	public default void POJOtoMe(P pojo) {
		
	}

}
