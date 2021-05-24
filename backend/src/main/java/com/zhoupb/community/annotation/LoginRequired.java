package com.zhoupb.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 必须要登录的注解
 * 
 * 加上后, 必须要登录
 * 必须要加上 HttpServletRequest 该类型的参数
 * 验证通过后，使用 HttpServletRequest.getAttribute(Common.REQ_USER_ID) 获取用户ID
 * 使用 HttpServletRequest.getAttribute(Common.REQ_USERNAME) 获取用户名
 * 
 * -----------------------------以下弃用---------------------------------------
 * 可以获得一个类型为 Map<String, Object> 的对象
 * 使用 Map<String, Object>.get(Common.JWT_PAYLOAD_USER_ID_KEY) 获得用户id
 * 使用 Map<String, Object>.get(Common.JWT_PAYLOAD_USERNAME_KEY) 获得用户名
 * ----------------------------  end  -----------------------------------------
 * @author zhoupb
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {

}
