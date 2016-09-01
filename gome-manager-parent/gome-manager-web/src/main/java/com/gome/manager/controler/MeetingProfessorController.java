package com.gome.manager.controler;

import java.io.UnsupportedEncodingException;
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
import com.gome.manager.common.Constant;
import com.gome.manager.common.Page;
import com.gome.manager.common.util.ResponsesDTO;
import com.gome.manager.constants.ReturnCode;
import com.gome.manager.controler.base.BaseController;
import com.gome.manager.domain.Meeting;
import com.gome.manager.domain.Professor;
import com.gome.manager.service.MeetingProfessorService;
import com.gome.manager.service.MeetingService;

@Controller
@RequestMapping(value = "/professor")
public class MeetingProfessorController extends BaseController{

		@Resource
		private MeetingService meetingService;
		@Resource
		private MeetingProfessorService MeetingProfessorService;
		
		
		/**
		 * 跳转到新增会议专家页.
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
		@RequestMapping(value="/toAddMeetingProfessorView", method={RequestMethod.GET, RequestMethod.POST})
		public ModelAndView toAddMeetingProfessorView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
			model.setViewName("/meeting/addProfessor");
			String userName = getUserInfo(request, "userName");
			Meeting meeting = meetingService.findMeetingById(Long.parseLong(id));
			meeting.setId(Long.parseLong(id));
			model.addObject("meeting", meeting);
			model.addObject("operateUser", userName);
			return model;
		}
		
		/**
		 * 
		 * 新增会议专家.
		 *
		 * @param content
		 * 				会议专家信息
		 * @param request
		 * @param response
		 * @return
		 *
		 * <pre>
		 * 修改日期        修改人    修改原因
		 * 2015年11月09日    liuhaikun    新建
		 * </pre>
		 */
		@RequestMapping(value="/addMeetingProfessor", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
		@ResponseBody
		public ResponsesDTO addMeetingProfessor(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
			ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
			try{
				if(StringUtils.isNotEmpty(content)){
					Professor professor = JSON.parseObject(content, Professor.class);
					if(professor != null){
						int record = MeetingProfessorService.addMeetingProfessor(professor);
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
		 * 新增会议专家履历.
		 *
		 * @param content
		 * 				会议专家信息
		 * @param request
		 * @param response
		 * @return
		 *
		 * <pre>
		 * 修改日期        修改人    修改原因
		 * 2015年11月09日    liuhaikun    新建
		 * </pre>
		 */
		@RequestMapping(value="/addMeetingProfessorRecode", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
		@ResponseBody
		public ResponsesDTO addMeetingProfessorRecode(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
			ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
			try{
				if(StringUtils.isNotEmpty(content)){
					Professor professor = JSON.parseObject(content, Professor.class);
					if(professor != null){
						int record = MeetingProfessorService.addMeetingProfessorRecode(professor);
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
		 * 跳转到会议专家列表页.
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
		@RequestMapping(value="/queryMeetProfessorListView", method={RequestMethod.GET,RequestMethod.POST})
		public ModelAndView queryMeetProfessorListView(@Param(value="id")String id,@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws UnsupportedEncodingException{
			if(StringUtils.isEmpty(content)){
				model.setViewName("/meeting/professorList");
			} else {
				model.setViewName("/meeting/professorTable");
			}
			
			int pageNo = 1;
			if(StringUtils.isNotEmpty(page)){
				pageNo = Integer.parseInt(page);
			}
			
			int pageSize = Constant.PAGE_SIZE;
			if(StringUtils.isNotEmpty(size)){
				pageSize = Integer.parseInt(size);
			}
			Page<Professor> p = new Page<Professor>(pageNo, pageSize);
			
			Professor professor = null;
			if(StringUtils.isNotEmpty(content)){
				professor = JSON.parseObject(content, Professor.class);
			} else {
				professor = new Professor();
			}
			p.setConditions(professor);
			professor.setCode(id);
			Page<Professor> pageMeetingProfessor = MeetingProfessorService.findMeetingProfessorListByPage(p);
			Meeting meeting = new Meeting();
			meeting.setId(Long.parseLong(id));
			model.addObject("meeting", meeting);
			model.addObject("page", pageMeetingProfessor);
			return model;
		}
		
		/**
		 * 
		 * 跳转到编辑会议专家页.
		 *
		 * @param id
		 * 			会议专家ID
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
		@RequestMapping(value="/toEditMeetingProfessorView", method={RequestMethod.GET,RequestMethod.POST})
		public ModelAndView toEditMeetingProfessorView(@Param(value="id")String id,@Param(value="meetId")String meetId, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
			model.setViewName("/meeting/editMeetingProfessor");
			if(StringUtils.isNotEmpty(id)){
				Professor professor = MeetingProfessorService.findMeetingProfessorById(Long.parseLong(id));
				List<Professor> professorResumeList = MeetingProfessorService.findMeetingProfessorResume(professor.getId());
				if(professor != null){
					model.addObject("professor", professor);
					model.addObject("professorResumeList", professorResumeList);
					String userName = getUserInfo(request, "userName");
					model.addObject("operateUser", userName);
				}
			}
			Meeting meeting = new Meeting();
			meeting.setId(Long.parseLong(meetId));
			model.addObject("meeting", meeting);
			return model;
		}
		
		/**
		 * 
		 * 编辑会议专家.
		 *
		 * @param content
		 * 			会议专家信息
		 * @param request
		 * @param response
		 * @return
		 *
		 * <pre>
		 * 修改日期        修改人    修改原因
		 * 2015年11月09日    liuhaikun    新建
		 * </pre>
		 */
		@RequestMapping(value="/editMeetingProfessor", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
		@ResponseBody
		public ResponsesDTO editMeetingProfessor(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
			ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
			try{
				Professor professor = JSON.parseObject(content, Professor.class);
				if(professor != null){
					int record = MeetingProfessorService.updateMeetingProfessor(professor);
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
		 * 删除会议专家.
		 *
		 * @param id
		 * 			会议专家ID
		 * @param request
		 * @param response
		 * @return
		 *
		 * <pre>
		 * 修改日期        修改人    修改原因
		 * 2015年11月09日    liuhaikun    新建
		 * </pre>
		 */
		@RequestMapping(value="/deleteMeetingProfessor", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
		@ResponseBody
		public ResponsesDTO deleteMeetingProfessor(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
			ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
			try{
				if(StringUtils.isNotEmpty(id)){
					Long MeetingProfessorId = Long.parseLong(id);
					int record = MeetingProfessorService.deleteMeetingProfessorById(MeetingProfessorId);
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
		 * 删除会议专家.
		 *
		 * @param id
		 * 			会议专家ID
		 * @param request
		 * @param response
		 * @return
		 *
		 * <pre>
		 * 修改日期        修改人    修改原因
		 * 2015年11月09日    liuhaikun    新建
		 * </pre>
		 */
		@RequestMapping(value="/delResume", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
		@ResponseBody
		public ResponsesDTO delResume(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
			ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
			try{
				if(StringUtils.isNotEmpty(id)){
					Long MeetingProfessorId = Long.parseLong(id);
					int record = MeetingProfessorService.delResume(MeetingProfessorId);
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
		 * 查询会议专家.
		 *
		 * @param id
		 * 			会议专家ID
		 * @param request
		 * @param response
		 * @return
		 *
		 * <pre>
		 * 修改日期        修改人    修改原因
		 * 2016年5月09日    liuhaikun    新建
		 * </pre>
		 */
		@RequestMapping(value="/queryMeetingProfessor", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
		@ResponseBody
		public ResponsesDTO queryMeetingProfessor(@Param(value="meetId")String meetId, HttpServletRequest request, HttpServletResponse response){
			ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
			try{
				if(StringUtils.isNotEmpty(meetId)){
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					List<Professor> professorList = MeetingProfessorService.findMeetingProfessorByMeetId(Long.parseLong(meetId));
					if(professorList !=null){
						for (int i = 0; i < professorList.size(); i++) {
							List<Professor> professorResumeList = MeetingProfessorService.findMeetingProfessorResume(professorList.get(i).getId());
							professorList.get(i).setProfessorResumeList(professorResumeList);
						}
						responses.setAttach(professorList);
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
