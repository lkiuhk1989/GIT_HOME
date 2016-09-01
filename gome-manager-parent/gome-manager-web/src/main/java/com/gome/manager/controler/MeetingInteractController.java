package com.gome.manager.controler;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.gome.manager.domain.MeetingInteract;
import com.gome.manager.service.MeetingInteractService;
import com.gome.manager.service.MeetingService;

@Controller
@RequestMapping(value = "/meetingInteract")
public class MeetingInteractController extends BaseController{

	@Resource
	private MeetingInteractService meetingInteractService;
	@Resource
	private MeetingService meetingService;

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
		model.setViewName("/meeting/interact");
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		List<MeetingInteract> interactList = meetingInteractService.queryInteract(id);
		Meeting meeting = new Meeting();
		meeting.setId(Long.parseLong(id));
		model.addObject("meeting", meeting);
		model.addObject("interactList", interactList);
		return model;
	}
	/**
	 * 跳转到互动页展示页面.
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
	@RequestMapping(value="/interactShow", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView interactShow(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/meeting/interactShow");
		Meeting meeting = meetingService.findMeetingById(Long.parseLong(id));
		model.addObject("meeting", meeting);
		return model;
	}

	/**
	 * 
	 * 新增互动.
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
	@RequestMapping(value="/addInteract", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addInteract(@Param(value="contentStr")String contentStr, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(contentStr)){
				MeetingInteract meetingInteract = JSON.parseObject(contentStr, MeetingInteract.class);
				String content = URLDecoder.decode(meetingInteract.getContent(),"UTF-8"); 
				String times = meetingInteract.getTimes();
				DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
				Date da = df.parse(times);
				String format = df.format(da);
				meetingInteract.setTimes(format);
				meetingInteract.setContent(content);
				if(meetingInteract != null){
					meetingInteract.setIswallrun("1");
					int record = meetingInteractService.addInteract(meetingInteract);
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
	 * 上墙.
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
	@RequestMapping(value="/onWall", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO onWall(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				Long InteractId = Long.parseLong(id);
				MeetingInteract meetingInteract = new MeetingInteract();
				meetingInteract.setId(InteractId);
				meetingInteract.setIswallrun("1");
				int record = meetingInteractService.updateInteract(meetingInteract);
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
	 * 上墙.
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
	@RequestMapping(value="/queryOnWall", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO queryOnWall(@Param(value="meetId")String meetId,@Param(value="maxInteractId")String maxInteractId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(meetId)){
				MeetingInteract meetingInteract = new MeetingInteract();
				meetingInteract.setMaxInteractId(Long.parseLong(maxInteractId));
				meetingInteract.setCode(meetId);
				List<MeetingInteract> interactList = meetingInteractService.queryOnWall(meetingInteract);
				if(interactList !=null &&  interactList.size()>0){
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					responses.setAttach(interactList);
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
	 * 查询自己互动的历史记录.
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
	@RequestMapping(value="/queryOwnHistory", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO queryOwnHistory(@Param(value="meetId")String meetId,@Param(value="perId")String perId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(meetId)){
				MeetingInteract meetingInteract = new MeetingInteract();
				meetingInteract.setPerId(perId);
				meetingInteract.setCode(meetId);
				List<MeetingInteract> interactList = meetingInteractService.queryOwnHistory(meetingInteract);
				responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				responses.setAttach(interactList);
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	
	/**
	 * 
	 * 清空上墙互动留言.
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
	@RequestMapping(value="/deleteWall", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO deleteWall(@Param(value="meetId")String meetId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(meetId)){
				MeetingInteract meetingInteract = new MeetingInteract();
				meetingInteract.setCode(meetId);
				int record = meetingInteractService.deleteWall(meetingInteract);
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
	 * 导出Excel
	 * @param meetId
	 * @param maxInteractId
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/exportExcel", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
    public void exportExcel(@Param(value="meetId")String meetId,HttpServletRequest request, HttpServletResponse response)throws Exception {    
		MeetingInteract meetingInteract = new MeetingInteract();
		meetingInteract.setCode(meetId);
		List<MeetingInteract> interactList = meetingInteractService.queryOnWall(meetingInteract);
        HSSFWorkbook wb = meetingInteractService.exportExcel(interactList);    
        response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=meetInteract.xls");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
   }    
}
