package com.gome.manager.service;

import java.util.List;

import com.gome.manager.common.Page;
import com.gome.manager.domain.CodeRecord;
import com.gome.manager.domain.User;
import com.gome.manager.domain.UserBean;

/**
 * 文献导读service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface UserService {

	
	/**
	 * 
	 * 分页查询会议列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	Page<User> findUserListByPage(Page<User> p);

	int addUser(User user);

	User queryUserDetail(Long userId);

	int verifyUser(User user);

	int verifyUserName(User user);

	int verifyUserPassword(User user);

	int addCodeRecord(CodeRecord codeRecord);

	int updateUser(User user);

	User checkUser(User user);

	int getTotalBean(Long userId);

	int addUserBean(UserBean userBean);

	List<UserBean> queryUserSign(UserBean userBean);

	void addAdvice(UserBean userBean);

	List<UserBean> queryUserBeans(UserBean userBean);

	User queryUserInfo(User user);

	User queryPassword(User user);

	UserBean queryUserIfDownload(UserBean userBean);

	UserBean querySignDate(UserBean userBean);

	Page<UserBean> queryUserAdvices(Page<UserBean> p);
}
