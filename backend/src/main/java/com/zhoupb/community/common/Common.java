package com.zhoupb.community.common;

public class Common {

	// cors
	public static final String ALLOWED_HOST []= {"http://127.0.0.1:8080"};
	public static final String ALLOWED_METHOD[] = {"GET", "POST", "DELETE", "PUT"};
	public static final boolean ALLOW_CREDENTIALS = true;
	
	
	// jwt and cookie
	public static final int AUTHORIZATION_EXPIRED_TIME = 60 * 60 * 24;
	public static final String JWT_DATA_KEY = "_data_";
	public static final String JWT_PAYLOAD_USER_ID_KEY = "_user_id_";
	public static final String JWT_PAYLOAD_USERNAME_KEY = "_username_";
	public static final String JWT_PAYLOAD_IS_ADMIN = "_is_admin_";
	public static final String COOKIE_KEY = "_authorization_";
	
	// authorized data key
	public static final String AUTHORIZED_DATA_KEY = "_authorized_";
	
	// default user avatar url
	public static final String DEFAULT_AVATAR_URL = "https://cdn.zhoupb.com/image/avatar.jpg";
	
	// request attribute
	public static final String REQ_USER_ID = JWT_PAYLOAD_USER_ID_KEY;
	public static final String REQ_USERNAME = JWT_PAYLOAD_USERNAME_KEY;
	
	/**
	 * 当前页面默认值
	 */
	public static final String PAGE_CURRENT_DEFAULT = "1";
	
	/**
	 * 分页默认大小
	 */
	public static final String PAGE_SIZE_DEFAULT = "10";
}
