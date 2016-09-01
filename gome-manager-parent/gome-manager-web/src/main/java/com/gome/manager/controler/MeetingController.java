package com.gome.manager.controler;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gome.manager.common.Constant;
import com.gome.manager.common.Page;
import com.gome.manager.common.util.ResponsesDTO;
import com.gome.manager.constants.ReturnCode;
import com.gome.manager.controler.base.BaseController;
import com.gome.manager.domain.Meeting;
import com.gome.manager.service.MeetingService;

@Controller
@RequestMapping(value = "/meeting")
public class MeetingController extends BaseController{

	@Resource
	private MeetingService meetingService;


	/**
	 * 跳转到新增会议页.
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
	@RequestMapping(value="/toAddMeetingView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toAddMeetingView(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/meeting/addMeeting");
		String userName = getUserInfo(request, "userName");
		String code = meetingService.queryMeetCode();
		model.addObject("operateUser", userName);
		model.addObject("code", code);
		return model;
	}

	/**
	 * 
	 * 新增会议.
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
	@RequestMapping(value="/addMeeting", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addMeeting(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(content)){
				Meeting meeting = JSON.parseObject(content, Meeting.class);
				if(meeting != null){
					DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
					String format = df.format(new Date());
					meeting.setCreateTime(format);
					meeting.setCreatePer(meeting.getOperateUser());
					int record = meetingService.addMeeting(meeting);
					if(record > 0){
						responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
						meetingService.updateMeetCode(meeting.getCode());
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
	 * 跳转到会议列表页.
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
	@RequestMapping(value="/queryMeetListView", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView queryMeetListView(@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws UnsupportedEncodingException{
		if(StringUtils.isEmpty(content)){
			model.setViewName("/meeting/meetingList");
		} else {
			model.setViewName("/meeting/meetingTable");
		}

		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}

		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<Meeting> p = new Page<Meeting>(pageNo, pageSize);

		Meeting meeting = null;
		if(StringUtils.isNotEmpty(content)){
			meeting = JSON.parseObject(content, Meeting.class);
		} else {
			meeting = new Meeting();
		}
		p.setConditions(meeting);
		Page<Meeting> pageMeeting = meetingService.findMeetingListByPage(p);
		model.addObject("page", pageMeeting);
		return model;
	}

	/**
	 * 
	 * 跳转到编辑会议页.
	 *
	 * @param id
	 * 			会议ID
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/toEditMeetingView", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toEditMeetingView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/meeting/editMeeting");
		if(StringUtils.isNotEmpty(id)){
			Meeting meeting = meetingService.findMeetingById(Long.parseLong(id));
			if(meeting != null){
				model.addObject("meeting", meeting);
				String userName = getUserInfo(request, "userName");
				model.addObject("operateUser", userName);
			}
		}
		return model;
	}

	/**
	 * 
	 * 编辑会议.
	 *
	 * @param content
	 * 			会议信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/editMeeting", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO editMeeting(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			Meeting meeting = JSON.parseObject(content, Meeting.class);
			if(meeting != null){
				int record = meetingService.updateMeeting(meeting);
				if(record > 0){
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
	 * 删除会议.
	 *
	 * @param id
	 * 			会议ID
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/deleteMeeting", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO deleteMeeting(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				Long MeetingId = Long.parseLong(id);
				int record = meetingService.deleteMeetingById(MeetingId);
				if(record > 0){
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
	 * 会议码校验.
	 *
	 * @param code
	 * 			会议code
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/validateMeetCode", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO validateMeetCode(@Param(value="code")String code, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(code)){
				Meeting meeting = meetingService.validateMeetCode(code);
				if(meeting != null){
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					responses.setAttach(meeting);
				} else {
					responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
				}
			}else{
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
	 * 会议码校验.
	 *
	 * @param code
	 * 			会议code
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/queryMeetInfo", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO queryMeetInfo(@Param(value="meetId")String meetId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(meetId)){
				Meeting meeting = meetingService.findMeetingById(Long.parseLong(meetId));
				if(meeting != null){
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					responses.setAttach(meeting);
				} else {
					responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
				}
			}else{
				responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
}
