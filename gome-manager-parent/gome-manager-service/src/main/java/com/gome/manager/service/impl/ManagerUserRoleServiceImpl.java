package com.gome.manager.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.manager.dao.ManagerUserRoleMapper;
import com.gome.manager.domain.ManagerUserRole;
import com.gome.manager.service.ManagerUserRoleService;

@Service("managerUserRoleService")
public class ManagerUserRoleServiceImpl implements ManagerUserRoleService {
	
	@Resource
	private ManagerUserRoleMapper managerUserRoleMapper;

	@Override
	public void save(Long userId, Long roleId) {
		ManagerUserRole userRole = new ManagerUserRole();
		userRole.setRoleId(roleId);
		userRole.setUserId(userId);
		managerUserRoleMapper.insertSelective(userRole);
	}

	@Override
	public void delByUser(Long userId) {
		managerUserRoleMapper.delByUser(userId);
		
	}

	@Override
	public void delByRole(Long userId) {
		managerUserRoleMapper.delByRole(userId);
		
	}

	@Override
	public ManagerUserRole getByUserId(Long userId) {
		// TODO Auto-generated method stub
		return managerUserRoleMapper.getByRoleId(userId);
	}
	

}
