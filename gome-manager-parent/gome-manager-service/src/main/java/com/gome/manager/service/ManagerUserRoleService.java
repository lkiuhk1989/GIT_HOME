package com.gome.manager.service;

import com.gome.manager.domain.ManagerUserRole;

public interface ManagerUserRoleService {
	
	public void save(Long userId, Long roleId);
	
    public void delByUser(Long userId);
    
    public void delByRole(Long userId);
    
    public ManagerUserRole getByUserId(Long userId);

}
