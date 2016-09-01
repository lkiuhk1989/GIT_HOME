package com.gome.manager.controler;

import java.util.List;

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
import com.gome.manager.common.util.ResponsesDTO;
import com.gome.manager.constants.ReturnCode;
import com.gome.manager.controler.base.BaseController;
import com.gome.manager.domain.Meeting;
import com.gome.manager.domain.MeetingSchedule;
import com.gome.manager.service.MeetingScheduleService;

@Controller
@RequestMapping(value = "/meetingSchedule")
public class MeetingScheduleController extends BaseController{

	@Resource
	private MeetingScheduleService meetingScheduleService;


	/**
	 * 跳转到会议日程页.
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
	@RequestMapping(value="/toScheduleView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toScheduleView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/meeting/schedule");
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		Meeting meeting = new Meeting();
		meeting.setId(Long.parseLong(id));
		List<MeetingSchedule> meetingScheduleList = meetingScheduleService.queryScheduleStage(meeting);
		List<MeetingSchedule> scheduleList = meetingScheduleService.querySchedule(id);
		model.addObject("meeting", meeting);
		model.addObject("meetingScheduleList", meetingScheduleList);
		model.addObject("scheduleList", scheduleList);
		return model;
	}
	
	/**
	 * 跳转到会议日程阶段页.
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
	@RequestMapping(value="/toScheduleStageView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toScheduleStageView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/meeting/scheduleStage");
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		Meeting meeting = new Meeting();
		meeting.setId(Long.parseLong(id));
		List<MeetingSchedule> meetingScheduleList = meetingScheduleService.queryScheduleStage(meeting);
		model.addObject("meeting", meeting);
		model.addObject("meetingScheduleList", meetingScheduleList);
		return model;
	}

	/**
	 * 
	 * 新增会议日程.
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
	@RequestMapping(value="/addSchedule", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addSchedule(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(content)){
				MeetingSchedule meetingSchedule = JSON.parseObject(content, MeetingSchedule.class);
				if(meetingSchedule != null){
					meetingSchedule.setTimes(meetingSchedule.getBeginTimes()+"-"+meetingSchedule.getEndTimes());
					int record = meetingScheduleService.addSchedule(meetingSchedule);
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
	 * 新增会议日程阶段.
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
	@RequestMapping(value="/addScheduleStage", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addScheduleStage(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(content)){
				MeetingSchedule meetingSchedule = JSON.parseObject(content, MeetingSchedule.class);
				if(meetingSchedule != null){
					int record = meetingScheduleService.addScheduleStage(meetingSchedule);
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
	 * 删除会议日程.
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
	@RequestMapping(value="/delSchedule", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO deloinPer(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				Long ScheduleId = Long.parseLong(id);
				MeetingSchedule meetingSchedule = new MeetingSchedule();
				meetingSchedule.setId(ScheduleId);
				int record = meetingScheduleService.delSchedule(meetingSchedule);
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
	 * 删除会议日程.
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
	@RequestMapping(value="/delStage", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO delStage(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				Long ScheduleId = Long.parseLong(id);
				MeetingSchedule meetingSchedule = new MeetingSchedule();
				meetingSchedule.setId(ScheduleId);
				int record = meetingScheduleService.delStage(meetingSchedule);
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
	 * 查询会议日程.
	 *
	 * @param content
	 * 				会议信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/querySchedule", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO querySchedule(@Param(value="meetId")String meetId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(meetId)){
				List<MeetingSchedule> meetingScheduleList = meetingScheduleService.queryScheduleById(meetId);
				if(meetingScheduleList != null){
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					responses.setAttach(meetingScheduleList);
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
}
