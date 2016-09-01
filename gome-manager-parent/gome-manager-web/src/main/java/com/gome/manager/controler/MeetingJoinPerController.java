package com.gome.manager.controler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gome.manager.common.Constant;
import com.gome.manager.common.Page;
import com.gome.manager.common.util.DateUtil;
import com.gome.manager.common.util.POIUtil;
import com.gome.manager.common.util.ResponsesDTO;
import com.gome.manager.constants.ReturnCode;
import com.gome.manager.controler.base.BaseController;
import com.gome.manager.domain.Meeting;
import com.gome.manager.domain.MeetingBack;
import com.gome.manager.domain.MeetingJoinPer;
import com.gome.manager.service.MeetingJoinPerService;
import com.gome.manager.service.MeetingService;

@Controller
@RequestMapping(value = "/meetManager")
public class MeetingJoinPerController extends BaseController{

	@Resource
	private MeetingService meetingService;
	@Resource
	private MeetingJoinPerService meetingJoinPerService;


	/**
	 * 跳转到欢迎信页.
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
	@RequestMapping(value="/toWelcomeView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toAddMeetingView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/meeting/welcome");
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		Meeting meeting = meetingService.findMeetingById(Long.parseLong(id));
		meeting.setId(Long.parseLong(id));
		if(meeting.getLetterPic()==null){
			meeting.setLetterPic("");
		}
		model.addObject("meeting", meeting);
		return model;
	}
	
	/**
	 * 跳转到背景页.
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
	@RequestMapping(value="/toBackView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toBackView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/meeting/background");
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		MeetingBack meetingBack = meetingService.findMeetingBackById(id);
		if(meetingBack != null){
			meetingBack.setCode(id);
		}else{
			meetingBack = new MeetingBack();
			meetingBack.setCode(id);
			meetingBack.setPicPath("");
		}
		Meeting meeting = new Meeting();
		meeting.setId(Long.parseLong(id));
		model.addObject("meeting", meeting);
		model.addObject("meetingBack", meetingBack);
		return model;
	}
	
	/**
	 * 
	 * 新增背景图.
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
	@RequestMapping(value="/addBackPic", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addBackPic(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			MeetingBack meetingBack = JSON.parseObject(content, MeetingBack.class);
			if(meetingBack != null){
				Meeting meeting = new Meeting();
				meeting.setId(meetingBack.getId());
				meeting.setPicPath(meetingBack.getPicPath());
				int record = meetingService.updateMeetingNew(meeting);
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
	 * 查询背景图.
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
	@RequestMapping(value="/queryBackPic", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO queryBackPic(@Param(value="meetId")String meetId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(meetId != null){
				MeetingBack meetingBack = meetingService.findMeetingBackById(meetId);
				if(meetingBack !=null){
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					responses.setAttach(meetingBack);
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
	 * 跳转到参会人员页.
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
	@RequestMapping(value="/toJoinPerView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toJoinPerView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/meeting/joinPer");
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		List<MeetingJoinPer> meetingJoinPerList = meetingJoinPerService.queryJoinPer(id);
		Meeting meeting = new Meeting();
		meeting.setId(Long.parseLong(id));
		model.addObject("meeting", meeting);
		model.addObject("meetingJoinPerList", meetingJoinPerList);
		return model;
	}

	/**
	 * 跳转到参会人员页.
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
	@RequestMapping(value="/queryPer", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView queryPer(@Param(value="code")String code,@Param(value="signTime")String signTime, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/meeting/joinPer");
		MeetingJoinPer joinPer = new MeetingJoinPer();
		joinPer.setCode(code);
		joinPer.setSignTime(signTime);
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		List<MeetingJoinPer> meetingJoinPerList = meetingJoinPerService.queryJoinPer(joinPer);
		Meeting meeting = new Meeting();
		meeting.setId(Long.parseLong(joinPer.getCode()));
		model.addObject("meeting", meeting);
		model.addObject("meetingJoinPerList", meetingJoinPerList);
		return model;
	}
	
	/**
	 * 跳转到参会人员页.
	 *
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 * @throws IOException 
	 */
	@RequestMapping(value="/exportPer", method={RequestMethod.GET, RequestMethod.POST},produces="application/json;charset=utf-8")
	public void exportPer(@Param(value="code")String code,@Param(value="signTime")String signTime, HttpServletRequest request, HttpServletResponse respons) throws IOException{
		MeetingJoinPer joinPer = new MeetingJoinPer();
		joinPer.setCode(code);
		joinPer.setSignTime(signTime);
		List<MeetingJoinPer> meetingJoinPerList = meetingJoinPerService.queryJoinPer(joinPer);
        HSSFWorkbook wb = meetingJoinPerService.exportExcel(meetingJoinPerList);    
        respons.setContentType("application/vnd.ms-excel");    
        respons.setHeader("Content-disposition", "attachment;filename=perJoinMeeting.xls");    
        OutputStream ouputStream = respons.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
	   
	}
	
	/**
	 * 跳转到参会人员页（批量导入页面）.
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
	@RequestMapping(value="/toJoinPerImportView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toJoinPerImportView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/meeting/joinPerImport");
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		Meeting meeting = new Meeting();
		meeting.setId(Long.parseLong(id));
		model.addObject("meeting", meeting);
		return model;
	}
	/**
	 * 
	 * 新增参会人员.
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
	@RequestMapping(value="/addJoinPer", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addJoinPer(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(content)){
				MeetingJoinPer meetingJoinPer = JSON.parseObject(content, MeetingJoinPer.class);
				if(meetingJoinPer != null){
					int record = meetingJoinPerService.addJoinPer(meetingJoinPer);
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
	 * 删除参会人员.
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
	@RequestMapping(value="/delJoinPer", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO deloinPer(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				Long joinPerId = Long.parseLong(id);
				MeetingJoinPer meetingJoinPer = new MeetingJoinPer();
				meetingJoinPer.setId(joinPerId);
				int record = meetingJoinPerService.delJoinPer(meetingJoinPer);
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
	 * 跳转到会议议程页.
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
		Meeting meeting = meetingService.findMeetingById(Long.parseLong(id));
		meeting.setId(Long.parseLong(id));
		model.addObject("meeting", meeting);
		return model;
	}


	/**
	 * 跳转到专家管理页.
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
	@RequestMapping(value="/toProsefforView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toProsefforView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/meeting/proseffor");
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		Meeting meeting = meetingService.findMeetingById(Long.parseLong(id));
		meeting.setId(Long.parseLong(id));
		model.addObject("meeting", meeting);
		return model;
	}

	/**
	 * 跳转到会议资料页.
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
	@RequestMapping(value="/toDataView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toDataView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/meeting/proseffor");
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		Meeting meeting = meetingService.findMeetingById(Long.parseLong(id));
		meeting.setId(Long.parseLong(id));
		model.addObject("meeting", meeting);
		return model;
	}

	/**
	 * 跳转到投票管理页.
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
	@RequestMapping(value="/toVoteView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toVoteView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/meeting/proseffor");
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		Meeting meeting = meetingService.findMeetingById(Long.parseLong(id));
		meeting.setId(Long.parseLong(id));
		model.addObject("meeting", meeting);
		return model;
	}
	/**
	 * 跳转到互动页.
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
	@RequestMapping(value="/toInteractView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toInteractView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/meeting/proseffor");
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		Meeting meeting = meetingService.findMeetingById(Long.parseLong(id));
		meeting.setId(Long.parseLong(id));
		model.addObject("meeting", meeting);
		return model;
	}
	/**
	 * 跳转到会后调查页.
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
	@RequestMapping(value="/toQuestionView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toQuestionView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/meeting/proseffor");
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		Meeting meeting = meetingService.findMeetingById(Long.parseLong(id));
		meeting.setId(Long.parseLong(id));
		model.addObject("meeting", meeting);
		return model;
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
		model.setViewName("/Meeting/editMeeting");
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
	 * 参会人员签到.
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
	@RequestMapping(value="/signMeetJionPerson", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO signMeetJionPerson(@Param(value="meetId")String meetId,@Param(value="perName")String perName, @Param(value="openId")String openId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(meetId) && StringUtils.isNotEmpty(perName) && StringUtils.isNotEmpty(openId)){
				MeetingJoinPer joinPerNew = new MeetingJoinPer();
				joinPerNew.setCode(meetId);
				perName = URLDecoder.decode(perName,"UTF-8"); 
				joinPerNew.setName(perName);
				MeetingJoinPer joinPer = meetingJoinPerService.signMeetJionPerson(joinPerNew);
				if(joinPer != null){
					//将openId以前参加的会议openFlag赋值0
					if(joinPer.getOpenFlag()!=null && joinPer.getOpenFlag().equals("0")){
						
					}else{
						MeetingJoinPer joinPerNewBefore = new MeetingJoinPer();
						joinPerNewBefore.setOpenId(openId);
						joinPerNewBefore.setOpenFlag("0");
						meetingJoinPerService.updateMeetUnOpenFlag(joinPerNewBefore);
						//将openId当前参加的会议openFlag赋值01
						joinPer.setStatus("1");
						joinPer.setOpenId(openId);
						joinPer.setOpenFlag("1");
						joinPer.setSignTime(DateUtil.DateToStr("yyyy-MM-dd hh:mm:ss"));
						meetingJoinPerService.updateSignMeetJionPerson(joinPer);
					}
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					responses.setAttach(joinPer);
				} else {
					responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
				}
			}else{
				MeetingJoinPer joinPerNew = new MeetingJoinPer();
				joinPerNew.setCode(meetId);
				perName = URLDecoder.decode(perName,"UTF-8"); 
				joinPerNew.setName(perName);
				MeetingJoinPer joinPer = meetingJoinPerService.signMeetJionPerson(joinPerNew);
				if(joinPer != null){
					//将openId当前参加的会议openFlag赋值01
					if(joinPer.getStatus()!=null){
						
					}else{
						joinPer.setStatus("1");
						joinPer.setSignTime(DateUtil.DateToStr("yyyy-MM-dd hh:mm:ss"));
						meetingJoinPerService.updateSignMeetJionPerson(joinPer);
					}
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					responses.setAttach(joinPer);
				}else
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
	 * 欢迎信图片.
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
	@RequestMapping(value="/queryWelcomeLetter", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO queryWelcomeLetter(@Param(value="meetId")String meetId, HttpServletRequest request, HttpServletResponse response){
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
	/**
	 *
	 * 查询参会人员是否已经签到.
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
	@RequestMapping(value="/queryMeetJionPerson", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO queryMeetJionPerson(@Param(value="openId")String openId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(openId)){
				MeetingJoinPer joinPerNew = new MeetingJoinPer();
				joinPerNew.setOpenFlag("1");
				joinPerNew.setOpenId(openId);
				MeetingJoinPer joinPer = meetingJoinPerService.signMeetJionPerson(joinPerNew);
				if(joinPer != null){
					//签到60分钟内不需要重新登录
					if(joinPer.getSignTime()!=null){
						SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date signTime = format.parse(joinPer.getSignTime());
						long cha = (new Date().getTime()-signTime.getTime())/60/1000;
						if(cha>60){
							responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
							return responses;
						}
					}
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					responses.setAttach(joinPer);
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
	 * 批量导入参会人员.
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
	@RequestMapping(value="/importMeetJionPerson", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO importMeetJionPerson(@RequestParam("fileUrl")MultipartFile fileUrl,@Param(value="meetId")String meetId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			List<MeetingJoinPer> meetingJoinPers = new ArrayList<MeetingJoinPer>();  
		    if(fileUrl != null){  
		        try {  
		        	CommonsMultipartFile cf= (CommonsMultipartFile)fileUrl; 
		        	DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
		        	File excel = fi.getStoreLocation();
		            //读取excel文件分析Excel文件中的数据  
		        	Workbook book = null;
		            try {
		            	book = new XSSFWorkbook(new FileInputStream(excel));  
		            } catch (Exception ex) {
		                book = new HSSFWorkbook(new FileInputStream(excel));  
		            }
		            //读取第一页的内容  
		            Sheet sheet = book.getSheetAt(0);  
		            //从数据行开始读取数据  
		            for(int i=3; i<=sheet.getLastRowNum(); i++){  
		                Row row = sheet.getRow(i);  
		                MeetingJoinPer meetingJoinPer =new MeetingJoinPer();  
		                //名称  
		                meetingJoinPer.setCode(meetId);
		                meetingJoinPer.setName(POIUtil.getStringValue(row, 1));  
		                meetingJoinPer.setUnit(POIUtil.getStringValue(row, 2));  
		                meetingJoinPer.setOffice(POIUtil.getStringValue(row, 3));  
		                meetingJoinPer.setJobs(POIUtil.getStringValue(row, 4));  
		                meetingJoinPer.setPosition(POIUtil.getStringValue(row, 5)); 
		                int cellType = row.getCell(6).getCellType();
		                if(cellType==0){
		                	meetingJoinPer.setPhone(String.valueOf(POIUtil.getIntCellValue(row, 6)));  
		                }else if(cellType==1){
		                	meetingJoinPer.setPhone(POIUtil.getStringValue(row, 6));  
		                }
		                meetingJoinPers.add(meetingJoinPer);
		                meetingJoinPerService.addJoinPer(meetingJoinPer);
		            }  
		        } catch (Exception e) {  
		        	e.printStackTrace();
		        }finally{  
		        }  
		    }  
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
}
