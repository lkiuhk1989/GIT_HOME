package com.gome.manager.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.manager.common.Page;
import com.gome.manager.dao.UserMapper;
import com.gome.manager.domain.CodeRecord;
import com.gome.manager.domain.User;
import com.gome.manager.domain.UserBean;
import com.gome.manager.service.UserService;

/**
 * 
 * 导读service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource
	UserMapper userMapper;

	public Page<User> findUserListByPage(Page<User> page) {
		List<User> userList = userMapper.selectUserListByPage(page);
		if(userList!=null && userList.size()>0){
			for (int i = 0; i < userList.size(); i++) {
				String createTime = userList.get(i).getCreateTime();
				if(createTime.length()<15){
					Date d = new Date(Long.parseLong(createTime)*1000);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
					String format = sdf.format(d);
					userList.get(i).setCreateTime(format);
				}
			}
		}
		int totalResult = userMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<User>(page.getPageNo(), page.getPageSize(), totalResult, userList, page.getConditions());
	}

	@Override
	public int addUser(User user) {
		int id = userMapper.insertUser(user);
		return id;
	}

	@Override
	public User queryUserDetail(Long userId) {
		// TODO Auto-generated method stub
		User user = userMapper.queryUserDetail(userId);
		return user;
	}

	@Override
	public int verifyUser(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int verifyUserName(User user) {
		return userMapper.verifyUserName(user);
	}

	@Override
	public int verifyUserPassword(User user) {
		// TODO Auto-generated method stub
		return userMapper.verifyUserPassword(user);
	}

	@Override
	public int addCodeRecord(CodeRecord codeRecord) {
		userMapper.addCodeRecord(codeRecord);
		return 1;
	}

	@Override
	public int updateUser(User user) {
		userMapper.updateUser(user);
		return 1;
	}

	@Override
	public User checkUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.checkUser(user);
	}

	@Override
	public int getTotalBean(Long userId) {
		// TODO Auto-generated method stub
		Integer totalBean = userMapper.getTotalBean(userId);
		return totalBean;
	}

	@SuppressWarnings("static-access")
	@Override
	public int addUserBean(UserBean userBean) {
		String sourceType = userBean.getSourceType();
		int amount=0;
		//来源1、点赞2、评论、3、完善资料4、签到5、新注册6、微互动
		if(sourceType.equals("1")){
			amount = 1;
		}
		if(sourceType.equals("2")){
			//判断当前文章，当前用户是否评论过，评论过，只添加评论，不增加乐豆
			int countBean = userMapper.queryUserGetBean(userBean);
			if(countBean==0){
				amount = 5;
			}else{
				amount = 0;
			}
		}
		if(sourceType.equals("3")){
			amount = 20;
			userBean.setDescribes("完善资料赠送");
		}
		if(sourceType.equals("4")){
			Date date=new Date();//取时间
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
			date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String getTime1 = formatter.format(date);
			userBean.setGetTime(getTime1);
			UserBean userBean2 = userMapper.querySignDate(userBean);
			if(userBean2!=null){
				userBean.setSourceId(userBean2.getSourceId()+1);
			}
			calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
			date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
			String getTime2 = formatter.format(date);
			userBean.setGetTime(getTime2);
			UserBean userBean3 = userMapper.querySignDate(userBean);
			if(userBean2!=null  && userBean3!=null){
				amount = 3;
				userBean.setDescribes("连续三天签到");
			}else if(userBean2!=null  && userBean3==null){
				amount = 1;
				userBean.setDescribes("签到赠送");
			}else{
				amount = 1;
				userBean.setDescribes("签到赠送");
				userBean.setSourceId(1L);
			}
		}
		if(sourceType.equals("5")){
			amount =20;
			userBean.setDescribes("新注册奖励");
		}
		if(sourceType.equals("6")){
			amount = 10;
		}
		if(sourceType.equals("7") || sourceType.equals("8")){
			amount = -20;
		}
		if(sourceType.equals("10")){
			amount = 10;
			userBean.setDescribes("观看视频奖励");
		}
		userBean.setAmount(amount);
		if(sourceType.equals("4")){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String signTime = df.format(new Date());
			userBean.setSignTime(signTime);
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String getTime = df.format(new Date());
		userBean.setGetTime(getTime);
		userMapper.addUserBean(userBean);
		return 1;
	}

	@Override
	public List<UserBean> queryUserSign(UserBean userBean) {
		// TODO Auto-generated method stub
		return userMapper.queryUserSign(userBean);
	}

	@Override
	public void addAdvice(UserBean userBean) {
		// TODO Auto-generated method stub
		userMapper.addAdvice(userBean);
	}

	@Override
	public List<UserBean> queryUserBeans(UserBean userBean) {
		// TODO Auto-generated method stub
		return userMapper.queryUserBeans(userBean);
	}

	@Override
	public User queryUserInfo(User user) {
		// TODO Auto-generated method stub
		return userMapper.queryUserInfo(user);
	}

	@Override
	public User queryPassword(User user) {
		// TODO Auto-generated method stub
		return userMapper.queryPassword(user);
	}

	@Override
	public UserBean queryUserIfDownload(UserBean userBean) {
		// TODO Auto-generated method stub
		return userMapper.queryUserIfDownload(userBean);
	}

	@Override
	public UserBean querySignDate(UserBean userBean) {
		// TODO Auto-generated method stub
		return userMapper.querySignDate(userBean);
	}

	@Override
	public Page<UserBean> queryUserAdvices(Page<UserBean> page) {
		List<UserBean> userList = userMapper.queryUserAdvices(page);
		int totalResult = userMapper.queryUserAdvicesByConditions(page.getConditions());
		return new Page<UserBean>(page.getPageNo(), page.getPageSize(), totalResult, userList, page.getConditions());
	}
}
