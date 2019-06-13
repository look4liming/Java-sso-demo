package lee.bright.sso.server.config;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lee.bright.sso.server.model.UserInfo;

/**
 * @author Bright Lee
 */
public class UserInfoConfig {
	
	private static final UserInfoConfig instance = new UserInfoConfig();
	
	public static UserInfoConfig getInstance() {
		return instance;
	}
	
	private final Map<String, UserInfo> USER_INFO_MAP = new HashMap<String, UserInfo>();
	private final Map<String, UserInfo> TOKEN_USER_INFO_MAP = new HashMap<String, UserInfo>();
	{
		UserInfo userInfo1 = new UserInfo();
		userInfo1.setUsername("admin");
		userInfo1.setPassword("123456");
		userInfo1.setGender("male");
		userInfo1.setAddress("Shanghai");
		USER_INFO_MAP.put(userInfo1.getUsername(), userInfo1);
		
		UserInfo userInfo2 = new UserInfo();
		userInfo2.setUsername("kf001");
		userInfo2.setPassword("123456");
		userInfo2.setGender("female");
		userInfo2.setAddress("Beijing");
		USER_INFO_MAP.put(userInfo2.getUsername(), userInfo2);
		
		UserInfo userInfo3 = new UserInfo();
		userInfo3.setUsername("kf003");
		userInfo3.setPassword("123456");
		userInfo3.setGender("female");
		userInfo3.setAddress("Shanghai");
		USER_INFO_MAP.put(userInfo3.getUsername(), userInfo3);
	}
	
	private UserInfoConfig() {
	}
	
	public UserInfo getUserInfoByUsername(String username) {
		if (username == null) {
			return null;
		}
		UserInfo userInfo = USER_INFO_MAP.get(username);
		return userInfo;
	}
	
	public String generateToken(UserInfo userInfo) {
		String token = getUUID();
		TOKEN_USER_INFO_MAP.put(token, userInfo);
		return token;
	}
	
	public UserInfo getUserInfoByToken(String token) {
		if (token == null) {
			return null;
		}
		UserInfo userInfo = TOKEN_USER_INFO_MAP.get(token);
		return userInfo;
	}
	
	private String getUUID() {
		UUID uuid = UUID.randomUUID();
		String s = uuid.toString().replaceAll("\\-", "");
		return s;
	}

}
