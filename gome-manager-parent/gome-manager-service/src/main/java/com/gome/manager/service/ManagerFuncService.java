package com.gome.manager.service;

import java.util.List;

import com.gome.manager.domain.ManagerFunc;

public interface ManagerFuncService {
	
	public List<ManagerFunc> query(Long userId, Long parentId);
	
    public String queryForRoleIdNot(Long roleId);
    public String queryForRoleId(Long roleId);

}
