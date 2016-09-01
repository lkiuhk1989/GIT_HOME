package com.gome.manager.dao;

import java.util.List;

import com.gome.manager.common.Page;
import com.gome.manager.domain.CodeRecord;
import com.gome.manager.domain.User;
import com.gome.manager.domain.UserBean;

/**
 * 
 * 导读dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface UserMapper {
	
	/**
	 *
	 * 分页查询会议列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			会议列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	List<User> selectUserListByPage(Page<User> page);
	
	/**
	 *
	 * 根据搜索条件查询总记录数.
	 * @param goods
	 * 			搜索条件
	 * @return
	 * 			总记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	Integer selectTotalResultByConditions(User reading);

	int insertUser(User reading);

	User queryUserDetail(Long userId);

	void addCodeRecord(CodeRecord codeRecord);

	int updateUser(User user);

	int verifyUserName(User user);

	int verifyUserPassword(User user);

	User checkUser(User user);

	int getTotalBean(Long userId);

	void addUserBean(UserBean userBean);

	UserBean querySignDate(UserBean userBean);

	List<UserBean> queryUserSign(UserBean userBean);

	void addAdvice(UserBean userBean);

	List<UserBean> queryUserBeans(UserBean userBean);

	int queryUserGetBean(UserBean userBean);

	User queryUserInfo(User user);

	User queryPassword(User user);

	UserBean queryUserIfDownload(UserBean userBean);

	void updateUserSignCount(UserBean userBean);

	List<UserBean> queryUserAdvices(Page<UserBean> page);

	int queryUserAdvicesByConditions(UserBean conditions);

}
