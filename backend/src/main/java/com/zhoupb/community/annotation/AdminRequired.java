package com.zhoupb.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对LoginRequired增强
 * 
 * 加上后, 必须要登录，且是管理员
 * 必须要加上 HttpServletRequest 该类型的参数
 * 验证通过后，使用 HttpServletRequest.getAttribute(Common.REQ_USER_ID) 获取用户ID
 * 使用 HttpServletRequest.getAttribute(Common.REQ_USERNAME) 获取用户名
 * 
 * @author zhoupb
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminRequired {

}
