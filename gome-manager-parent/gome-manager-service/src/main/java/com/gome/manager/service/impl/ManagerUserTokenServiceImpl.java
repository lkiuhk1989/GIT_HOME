package com.gome.manager.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.manager.dao.ManagerUserTokenMapper;
import com.gome.manager.domain.ManagerUserToken;
import com.gome.manager.service.ManagerUserTokenService;

@Service("managerUserTokenService")
public class ManagerUserTokenServiceImpl implements ManagerUserTokenService {
	
	@Resource
	private ManagerUserTokenMapper managerUserTokenMapper;

	@Override
	public void save(Long userId, String token) {
	
		ManagerUserToken userToken = new ManagerUserToken();
		userToken.setUserId(userId);
		userToken = managerUserTokenMapper.selectByUserToken(userToken);
		if(userToken != null){
			//更新token
			userToken.setCreateTime(new Date());
			userToken.setToken(token);
			managerUserTokenMapper.updateByPrimaryKeySelective(userToken);
		}else{
			userToken = new ManagerUserToken();
			userToken.setCreateTime(new Date());
			userToken.setToken(token);
			userToken.setUserId(userId);
			managerUserTokenMapper.insert(userToken);
		}
		

	}

	@Override
	public ManagerUserToken getByUserToken(Long userId, String token) {
		ManagerUserToken userToken = new ManagerUserToken();
		userToken.setToken(token);
		userToken.setUserId(userId);
		userToken = managerUserTokenMapper.selectByUserToken(userToken);
		return userToken;
	}

}
