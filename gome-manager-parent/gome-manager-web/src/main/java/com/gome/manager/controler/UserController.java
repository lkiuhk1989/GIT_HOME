package com.gome.manager.controler;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gome.manager.common.Constant;
import com.gome.manager.common.Page;
import com.gome.manager.common.util.ResponsesDTO;
import com.gome.manager.constants.ReturnCode;
import com.gome.manager.controler.base.BaseController;
import com.gome.manager.domain.CodeRecord;
import com.gome.manager.domain.User;
import com.gome.manager.domain.UserBean;
import com.gome.manager.service.UserService;
import com.gome.manager.sms.SendSMSDemo;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController{
	@Resource
	private UserService userService;

	/**
	 * 
	 * 跳转到用户列表页.
	 *
	 * @param content
	 * 				搜索条件
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/queryUserList", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView queryUserView(@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws UnsupportedEncodingException{
		if(StringUtils.isEmpty(content)){
			model.setViewName("/users/userList");
		} else {
			model.setViewName("/users/userTable");
		}
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}

		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<User> p = new Page<User>(pageNo, pageSize);
		//通过数据库类型查找
		User user = new User();
		if(StringUtils.isNotEmpty(content)){
			user = JSON.parseObject(content, User.class);
			if(user.getUserName()!=null && user.getUserName()!=""){
				user.setUserName(java.net.URLDecoder.decode(user.getUserName(),"UTF-8"));
			}
		}
		p.setConditions(user);
		Page<User> pageUser = userService.findUserListByPage(p);
		model.addObject("page", pageUser);
		return model;
	}
	/**
	 * 
	 * 跳转到用户建议列表页.
	 *
	 * @param content
	 * 				搜索条件
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/queryUserAdvices", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView queryUserAdvices(@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws UnsupportedEncodingException{
		if(StringUtils.isEmpty(content)){
			model.setViewName("/users/adviceList");
		} else {
			model.setViewName("/users/adviceTable");
		}
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		
		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<UserBean> p = new Page<UserBean>(pageNo, pageSize);
		//通过数据库类型查找
		UserBean user = new UserBean();
		if(StringUtils.isNotEmpty(content)){
			user = JSON.parseObject(content, UserBean.class);
			if(user.getUserName()!=null && user.getUserName()!=""){
				user.setUserName(java.net.URLDecoder.decode(user.getUserName(),"UTF-8"));
			}
		}
		p.setConditions(user);
		Page<UserBean> pageUser = userService.queryUserAdvices(p);
		model.addObject("page", pageUser);
		return model;
	}

	/**
	 *
	 * 获取用户详情.
	 *
	 * @param catid期ID
	 * 
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/queryUserDetail", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO queryUserDetail(@Param(value="userId")String userId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			User userArticleDetail = userService.queryUserDetail(Long.parseLong(userId));
			if(userArticleDetail != null){
				responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				responses.setAttach(userArticleDetail);
			} else {
				responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}

	/**
	 * 
	 * 注册用户.
	 *
	 * @param content
	 * 				用户信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/addUser", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addUser(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(content)){
				User user = JSON.parseObject(content, User.class);
				if(user != null){
					user.setUserName(java.net.URLDecoder.decode(user.getUserName(),"UTF-8"));
					user.setHospital(java.net.URLDecoder.decode(user.getHospital(),"UTF-8"));
					user.setProvince(java.net.URLDecoder.decode(user.getProvince(),"UTF-8"));
					user.setCity(java.net.URLDecoder.decode(user.getCity(),"UTF-8"));
					user.setEmail(java.net.URLDecoder.decode(user.getEmail(),"UTF-8"));
					DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
					String format = df.format(new Date());
					user.setCreateTime(format);
					int record = userService.addUser(user);
					if(record > 0){
						responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					} else {
						responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}

	/**
	 * 
	 * 注册用户.
	 *
	 * @param content
	 * 				用户信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/updateUser", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO updateUser(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(content)){
				User user = JSON.parseObject(content, User.class);
				if(user != null){
					String userName = user.getUserName();
					if(userName!=null){
						userName=java.net.URLDecoder.decode(userName,"UTF-8");
						user.setUserName(userName);
					}
					if(user.getHospital()!=null){
						user.setHospital(java.net.URLDecoder.decode(user.getHospital(),"UTF-8"));
					}
					if(user.getOffice()!=null){
						user.setOffice(java.net.URLDecoder.decode(user.getOffice(),"UTF-8"));
					}
					if(user.getEmail()!=null){
						user.setEmail(java.net.URLDecoder.decode(user.getEmail(),"UTF-8"));
					}
					DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
					String format = df.format(new Date());
					user.setUpdateTime(format);
					int record = userService.updateUser(user);
					if(record > 0){
						responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					} else {
						responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	/**
	 * 
	 * 修改密码--校验旧密码是否正确.
	 *
	 * @param content
	 * 				用户信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/checkPassword", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO checkPassword(@Param(value="userId")String userId,@Param(value="password")String password, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(userId)){
				User user = new User();
				user.setId(Long.parseLong(userId));
				user.setPassword(password);
				user = userService.checkUser(user);
				if(user != null){
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					responses.setAttach(user);
				} else {
					responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	/**
	 * 
	 * 修改密码.
	 *
	 * @param content
	 * 				用户信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/updatePassword", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO updatePassword(@Param(value="userId")String userId,@Param(value="password")String password, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(userId)){
				User user = new User();
				user.setId(Long.parseLong(userId));
				user.setPassword(password);
				int result = userService.updateUser(user);
				if(result >0){
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				} else {
					responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	/**
	 * 
	 * 注册--校验用户名是否存在.
	 *
	 * @param phone 手机号
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/checkPhone", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO checkPhone(@Param(value="phone")String phone, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(phone)){
				User user = new User();
				user.setPhone(phone);
				user = userService.checkUser(user);
				if(user != null){
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					responses.setAttach(user);
				} else {
					responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	/**
	 * 
	 * 登录校验用户.
	 *
	 * @param content
	 * 				用户信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/login", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO login(@Param(value="content")String content,boolean checked, HttpServletRequest request, HttpServletResponse response,HttpSession hsession){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(content)){
				User user = JSON.parseObject(content, User.class);
				if(user != null){
					int record = userService.verifyUserName(user);
					if(record > 0){
						User user1 =  userService.queryPassword(user);
						if(user1!=null && (user1.getPassword()==null || "".equals(user1.getPassword()))){
							userService.updateUser(user);
							responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
							//将用户信息添加到cookie中
							int cookieExistsTime=-1;
							if(checked){
								cookieExistsTime=24 * 60 * 60 * 365 * 10;
							}
							user = userService.queryUserInfo(user);
							setUserInfo("userId", user.getId() + "", null, cookieExistsTime, "/", response);
							hsession.setAttribute("userId", user.getId());
							setUserInfo("userName", user.getUserName() + "", null, cookieExistsTime, "/", response);
							responses.setAttach(user);
							return responses;
						}
						int pass = userService.verifyUserPassword(user);
						if(pass>0){
							responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
							//将用户信息添加到cookie中
							int cookieExistsTime=-1;
							if(checked){
								cookieExistsTime=24 * 60 * 60 * 365 * 10;
							}
							user = userService.queryUserInfo(user);
							setUserInfo("userId", user.getId() + "", null, cookieExistsTime, "/", response);
							hsession.setAttribute("userId", user.getId());
							setUserInfo("userName", user.getUserName() + "", null, cookieExistsTime, "/", response);
							responses.setAttach(user);
						}else{
							responses.setReturnCode(ReturnCode.ERROR_USER_PASSWORD);
						}
					} else {
						responses.setReturnCode(ReturnCode.ERROR_USER_NONE);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}

	/**
	 * 
	 * 获取手机校验码.
	 *
	 * @param content
	 * 				用户信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/getCode", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO getCode(@Param(value="phone")String phone,@Param(value="flag")String flag, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(phone)){
				//生成随机6位数字
				String six = getSix();
				String sendSMS = SendSMSDemo.SendSMS(phone,six);
				if(sendSMS != null){
					//写自己的业务逻辑代码
					CodeRecord codeRecord = new CodeRecord();
					codeRecord.setPhone(phone);
					codeRecord.setFlag(flag);
					codeRecord.setCode(six);
					DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
					String createTime = df.format(new Date());
					codeRecord.setCreateTime(createTime);
					userService.addCodeRecord(codeRecord);
				}
				//存入记录表
				responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				responses.setAttach(six);
			} else {
				responses.setReturnCode(ReturnCode.ERROR_PHONE_MSG);
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	/** 
	 * 产生随机的六位数 
	 * @return 
	 */  
	public static String getSix(){  
		Random rad=new Random();  
		String result  = rad.nextInt(1000000) +"";  
		if(result.length()!=6){  
			return getSix();  
		}  
		return result;  
	}

	/**
	 * 
	 * 获取用户乐豆总数.
	 *
	 * @param userId 用户信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/getTotalBean", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO getTotalBean(@Param(value="userId")String userId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(userId)){
				int totalbean = userService.getTotalBean(Long.parseLong(userId));
				//存入记录表
				responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				responses.setAttach(totalbean);
			} else {
				responses.setReturnCode(ReturnCode.ERROR_PHONE_MSG);
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}

	/**
	 * 
	 * 新增乐豆接口.
	 *
	 * @param userId 用户信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/addUserBean", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addUserBean(@Param(value="userId")Long userId,@Param(value="sourceType")String sourceType,@Param(value="sourceId")String sourceId,@Param(value="describe")String describe, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(userId!=null){
				UserBean userBean = new UserBean();
				userBean.setUserId(userId);
				userBean.setSourceType(sourceType);
				userBean.setDescribes(describe);
				if(sourceId!=null && !"".equals(sourceId)){
					userBean.setSourceId(Long.parseLong(sourceId));
				}
				userService.addUserBean(userBean);
				//存入记录表
				responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
			} else {
				responses.setReturnCode(ReturnCode.ERROR_PHONE_MSG);
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}

	/**
	 * 
	 * 查询签到接口.
	 *
	 * @param userId 用户信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/queryUserSign", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO queryUserSign(@Param(value="userId")Long userId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(userId!=null){
				UserBean userBean = new UserBean();
				userBean.setUserId(userId);
				List<UserBean> list = userService.queryUserSign(userBean);
				if(list!=null && list.size()>0){
					Map<String,Object> map = new HashMap<String,Object>();
					List<String> signList = new ArrayList<String>();
					for (int i = 0; i < list.size(); i++) {
						signList.add(list.get(i).getSignTime());
					}
					//存入记录表
					Date date=new Date();//取时间
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					String getTime1 = formatter.format(date);
					userBean.setGetTime(getTime1);
					userBean = userService.querySignDate(userBean);
					if(userBean!=null){
						map.put("signCount", list.get(list.size()-1).getSourceId());
					}else
						map.put("signCount", 0);
					map.put("signList", signList);
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					responses.setAttach(map);
				}
			} else {
				responses.setReturnCode(ReturnCode.ERROR_PHONE_MSG);
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	/**
	 * 
	 * 查询乐豆获取和消费的接口.
	 *
	 * @param userId 用户信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/queryUserBeans", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO queryUserBeans(@Param(value="userId")Long userId,@Param(value="type")String type, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(userId!=null){
				UserBean userBean = new UserBean();
				userBean.setUserId(userId);
				userBean.setTypeB(type);
				List<UserBean> list = userService.queryUserBeans(userBean);
				if(list!=null && list.size()>0){
					//存入记录表
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					responses.setAttach(list);
				}
			} else {
				responses.setReturnCode(ReturnCode.ERROR_PHONE_MSG);
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	/**
	 * 
	 * 用户意见.
	 *
	 * @param userId 用户信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/addAdvice", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addAdvice(@Param(value="userId")Long userId,@Param(value="advice")String advice, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(userId!=null){
				UserBean userBean = new UserBean();
				userBean.setUserId(userId);
				userBean.setAdvice(advice);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String getTime = df.format(new Date());
				userBean.setGetTime(getTime);
				userService.addAdvice(userBean);
				responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
			} else {
				responses.setReturnCode(ReturnCode.ERROR_PHONE_MSG);
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}

	/**
	 * 
	 * 新增用户头像.
	 *
	 * @param content
	 * 				会议信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/addHeadPic", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addData(@Param(value="userId")Long userId,@RequestParam("fileUrl")MultipartFile fileUrl,HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		String filepath=null;
		try{
			if(fileUrl != null){
				//图片路径
				try {
					if(fileUrl!=null){
						filepath=upLoadFile(fileUrl, request);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				User user = new User();
				user.setId(userId);
				user.setFileUrl(filepath);
				int record = userService.updateUser(user);
				if(record > 0){
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					responses.setAttach(filepath);
				} else {
					responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	public static String upLoadFile(MultipartFile file,HttpServletRequest request) throws Exception{
		String fileName = new Date().getTime()+file.getOriginalFilename(); 
		String path  = request.getSession().getServletContext().getRealPath("/") ;
		if(path!=null && !"".equals(path)){
			path = path.substring(0, path.indexOf("gome-manager-web")-1);
		}
		path = path+ File.separator + "gmshopFile";
		System.out.println(path);
		File targetFile = new File(path, fileName);  
		if(!targetFile.exists()){  
			targetFile.mkdirs();  
		}  
		//保存  
		try {  
			file.transferTo(targetFile);  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return File.separator+"gmshopFile"+File.separator +fileName;  
	}
}
