package com.gome.manager.service;

import com.gome.manager.domain.ManagerUserToken;

public interface ManagerUserTokenService {
	
	/**
	 * 保存用户token等信息
	 * @param userId
	 * @param token
	 * @param sessionToken
	 * @param expires
	 */
	public void save(Long userId, String token);
	
	
	public ManagerUserToken getByUserToken(Long userId, String token);

}
