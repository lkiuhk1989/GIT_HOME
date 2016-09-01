package com.gome.manager.controler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gome.manager.common.util.ResponsesDTO;
import com.gome.manager.constants.ReturnCode;
import com.gome.manager.controler.base.BaseController;
import com.gome.manager.domain.ManagerRole;
import com.gome.manager.domain.ManagerUser;
import com.gome.manager.service.ManagerFuncService;
import com.gome.manager.service.ManagerRoleService;
import com.gome.manager.service.ManagerUserRoleService;
import com.gome.manager.service.ManagerUserService;

/**
 * 后台管理系统账号控制
 * 
 * @author zhangzhixiang
 *
 */
@Controller
@RequestMapping(value = "/manager/ajax/user")
public class ManagerUserAjaxController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(ManagerUserAjaxController.class);

	@Resource
	private ManagerUserService managerUserService;
	@Resource
	private ManagerUserRoleService managerUserRoleService;
	@Resource
	private ManagerRoleService managerRoleService;
	@Resource
	private ManagerFuncService managerFuncService;

	/**
	 * url: ip:port/manager/ajax/user/save 参数:
	 * {"userName":"zhangzhixiang","passwd":"wefwevev","realName":"张",
	 * "contactWay":"15010106579","headPath":"http://a.jpg","roleId":2,"state":
	 * 0} 管理员添加账号
	 * 
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponsesDTO save(HttpServletRequest request, HttpServletResponse response, String content) {

		logger.info("content:" + content);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		if (StringUtils.isBlank(content)) {
			res.setReturnCode(ReturnCode.ERROR_PARAMS_NOT_NULL);
			return res;
		}

		try {
			// 获取当前用户名
			String loginName = this.getUserInfo(request, "userName");
			// json字符串映射bean
			ManagerUser user = JSON.parseObject(content, ManagerUser.class);
			System.out.println(user.toString());
			// 账号数据入库
			managerUserService.save(user, loginName);
			managerUserRoleService.save(user.getUserId(), user.getRoleId());

		} catch (Exception e) {
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}

	/**
	 * 
	 * url: ip:port/manager/ajax/user/edit 参数:
	 * {"userId":1,"userName":"zhangzhixiang","passwd":"wefwevev","realName":"张"
	 * ,"contactWay":"15010106579","headPath":"http://a.jpg","roleId":1,"state":
	 * 0} 管理员对账号的编辑
	 * 
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponsesDTO edit(HttpServletRequest request, HttpServletResponse response, String content) {

		logger.info("content:" + content);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		if (StringUtils.isBlank(content)) {
			res.setReturnCode(ReturnCode.ERROR_PARAMS_NOT_NULL);
			return res;
		}

		try {
			// 获取当前用户名
			String loginName = this.getUserInfo(request, "userName");
			// json字符串映射bean
			ManagerUser user = JSON.parseObject(content, ManagerUser.class);
			System.out.println(user.toString());
			// 账号数据入库
			managerUserService.edit(user, loginName);
			//删除数据
			managerUserRoleService.delByUser(user.getUserId());
			//新增数据
			managerUserRoleService.save(user.getUserId(), user.getRoleId());

		} catch (Exception e) {
			logger.info("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}

	/**
	 * url:ip:port/manager/ajax/user/del/1 参数:
	 * 
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/del/{userId}", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=utf-8")
	public ResponsesDTO del(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") Long userId) {

		logger.info("userId:" + userId);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		if (userId == null) {
			res.setReturnCode(ReturnCode.ERROR_PARAMS_NOT_NULL);
			return res;
		}

		try {
			// 获取当前用户名
			String loginName = this.getUserInfo(request, "loginName");
			managerUserService.del(userId, loginName);
		} catch (Exception e) {
			logger.info("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}

	/**
	 * 登录
	 * http://127.0.0.1:8080/manager/ajax/user/login
	 * {"userName":"admin","passwd":"wefwevev"}
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	public ResponsesDTO login(HttpServletRequest request, HttpServletResponse response, HttpSession hsession, String content,boolean checked) {

		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		logger.info("content:"+content);
		try {

			if (StringUtils.isBlank(content)) {
				res.setReturnCode(ReturnCode.ERROR_PARAMS_NOT_NULL);
				return res;
			}

			JSONObject obj = JSON.parseObject(content);
			String userName = obj.getString("userName");
			String passwd = obj.getString("passwd");

			ManagerUser user = managerUserService.selectByUserName(userName);

			if (user == null) {
				res.setReturnCode(ReturnCode.ERROR_USER_NONE);// 用户不存在
				return res;
			}

			if (!user.getPasswd().equals(passwd)) {
				res.setReturnCode(ReturnCode.ERROR_USER_PASSWORD);
				return res;
			}
			
			if(user.getState() != 0){
				res.setReturnCode(ReturnCode.ERROR_USER_LOCK_OR_DEL);
				return res;
			}
			
			int cookieExistsTime=-1;
			if(checked){
            	cookieExistsTime=24 * 60 * 60 * 365 * 10;
            }
			setUserInfo("userId", user.getUserId() + "", null, cookieExistsTime, "/", response);
			setUserInfo("userName", user.getUserName() + "", null, cookieExistsTime, "/", response);
			ManagerRole role = managerRoleService.selectByUserId(user.getUserId());//用户角色信息
			String funcIds = managerFuncService.queryForRoleIdNot(role.getRoleId());
			//List<ManagerFunc> listMenu = managerRoleService.getFuncForLeftMenu(user.getUserId());//用户左侧菜单
			hsession.setAttribute("mrole", role);
			hsession.setAttribute("muser", user);
			hsession.setAttribute("funcIds", funcIds);
			//hsession.setAttribute("listMenu", listMenu);
		} catch (Exception e) {
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}


	
	
	/**
	 * url:http://127.0.0.1:8080/gome-manager-web/manager/ajax/user/uppasswd
	 * 参数:{"oldPasswd":"wefwevev","passwd":"123456","confirmPasswd":"123456"}
	 * 
	 * 
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upPasswd", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	public ResponsesDTO uppasswd(HttpServletRequest request, HttpServletResponse response, String content) {
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		logger.info("content:"+content);

		try {
			String userId = this.getUserInfo(request, "userId");
			if(StringUtils.isBlank(userId)){
				res.setReturnCode(ReturnCode.ERROR_NO_LOGIN);//用户未登录
				return res;
			}
			
			res = managerUserService.updatePasswd(content, Long.parseLong(userId));

		} catch (Exception e) {
			logger.info("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}
	
	
	
	/**
	 * 检查用户名称是否被占用
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkUserName", method = { RequestMethod.POST,RequestMethod.GET }, produces = "application/json;charset=utf-8")
	public ResponsesDTO checkUserName(HttpServletRequest request, HttpServletResponse response) {
		//public ResponsesDTO checkUserName(HttpServletRequest request, HttpServletResponse response,String userName) {
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		String userName = request.getParameter("userName");

		try {
			
			ManagerUser user = managerUserService.selectByUserName(userName);
			if(user!=null){
				res.setReturnCode(ReturnCode.ERROR_NAME_HAS_USED);
				return res;
			}
			
		} catch (Exception e) {
			logger.info("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}

	
	/**
	 * 修改个人账号信息
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/editMyAccount", method = { RequestMethod.POST}, produces = "application/json;charset=utf-8")
	public ResponsesDTO editMyAccount(HttpServletRequest request, HttpServletResponse response, String content) {
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		logger.info("content:"+content);

		try {
			
			String userId = this.getUserInfo(request, "userId");
			ManagerUser user = JSON.parseObject(content, ManagerUser.class);
			user.setUserId(Long.parseLong(userId));
			managerUserService.edit(user, getUserInfo(request, "userName"));
		} catch (Exception e) {
			logger.info("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}

	

}
