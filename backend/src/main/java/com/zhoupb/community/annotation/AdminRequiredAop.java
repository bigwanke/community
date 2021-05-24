package com.zhoupb.community.annotation;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.zhoupb.community.common.Common;
import com.zhoupb.community.common.JWTUtil;
import com.zhoupb.community.common.Result;
import com.zhoupb.community.exception.AuthorizationException;

@Aspect
@Component
public class AdminRequiredAop {

	@Before("@annotation(com.zhoupb.community.annotation.AdminRequired)")
	public void loginRequired(JoinPoint joinPoint) {
		Object args[] = joinPoint.getArgs();
		for (Object arg : args) {
			if ( arg instanceof HttpServletRequest ) {
				HttpServletRequest request = (HttpServletRequest) arg;

				Result<Map<String, Object>> res = isLogin(request.getCookies());
				if ( !res.isSuccess() || !(boolean) res.getData().get(Common.JWT_PAYLOAD_IS_ADMIN) )
					throw new AuthorizationException("无法登录, 访问其他页面吧. 😜", 403);

				request.setAttribute(Common.REQ_USER_ID, res.getData().get(Common.JWT_PAYLOAD_USER_ID_KEY));
				request.setAttribute(Common.REQ_USERNAME, res.getData().get(Common.JWT_PAYLOAD_USERNAME_KEY));
			}
		}
	}
	
	private Result<Map<String, Object>> isLogin(Cookie[] cookies) {
		if ( cookies == null )
			return Result.fail("cookies is null!");
		
		for (Cookie cookie : cookies)
			if ( cookie.getName().equals(Common.COOKIE_KEY) )
				return JWTUtil.verify(cookie.getValue());
		
		return Result.fail("cookies length equals zero!");
	}
	
}
