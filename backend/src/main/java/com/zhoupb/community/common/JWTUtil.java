package com.zhoupb.community.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtil {
	
	private static final String SECRET_KEY = "0&7qy2jbje1vkfh71ryp($jgle^k(^ng2xq&k&bf=6*z4s8qy7";
	private static final String ISSUER = "system";
	private static final Algorithm ALGORTHM = Algorithm.HMAC256(SECRET_KEY);
	
	public static String create(int id, String username, boolean isAdmin) {
		Map<String, Object> payload = new HashMap<>();
		payload.put(Common.JWT_PAYLOAD_USER_ID_KEY, id);
		payload.put(Common.JWT_PAYLOAD_USERNAME_KEY, username);
		payload.put(Common.JWT_PAYLOAD_IS_ADMIN, isAdmin);

		Date now = new Date();
		Date date = new Date(now.getTime() + (Common.AUTHORIZATION_EXPIRED_TIME * 1000L));

		return JWT.create().withIssuer(ISSUER).withIssuedAt(now)
					.withExpiresAt(date)
					.withClaim(Common.JWT_DATA_KEY, payload)
					.sign(ALGORTHM);
	}
	
	/**
	 * 验证token
	 * @param token
	 * @return Map key: id, username
	 */
	public static Result<Map<String, Object>> verify(String token) {
		//  异常类型
		//  签名                           | 解析 
		//  SignatureVerificationException | JWTDecodeException
		
		JWTVerifier jwtVerifier = JWT.require(ALGORTHM).withIssuer(ISSUER).build();
		
		try {
			DecodedJWT data = jwtVerifier.verify(token);
			return Result.success("验证成功", data.getClaim(Common.JWT_DATA_KEY).asMap());
		}
		catch (TokenExpiredException e) {
			return Result.fail("超时");
		}
		catch (JWTVerificationException e) {
			return Result.fail("jwt error");
		}
	}

}
