package com.gome.manager.controler;

import java.util.ArrayList;
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
import com.gome.manager.domain.MeetingVote;
import com.gome.manager.domain.MeetingVoteResult;
import com.gome.manager.service.MeetingVoteService;

@Controller
@RequestMapping(value = "/meetingVote")
public class MeetingVoteController extends BaseController{

	@Resource
	private MeetingVoteService meetingVoteService;


	/**
	 * 跳转到投票页.
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
		model.setViewName("/meeting/vote");
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		List<MeetingVote> VoteList = meetingVoteService.queryVote(id);
		MeetingVote meetingVote = new MeetingVote();
		meetingVote.setCode(id);
		String status = meetingVoteService.queryVoteStatus(meetingVote);
		Meeting meeting = new Meeting();
		meeting.setId(Long.parseLong(id));
		meeting.setStatus(status);
		model.addObject("meeting", meeting);
		model.addObject("VoteList", VoteList);
		return model;
	}
	/**
	 * 跳转到投票结果页.
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
	@RequestMapping(value="/toVoteResultView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toVoteResultView(@Param(value="id")String id,@Param(value="meetId")String meetId,@Param(value="wide")String wide,@Param(value="height")String height, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/meeting/voteResult");
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		MeetingVote meetingVote = new MeetingVote();
		meetingVote.setId(Long.parseLong(id));
		meetingVote.setCode(meetId);
		meetingVote.setWide(wide);
		meetingVote.setHeight(height);
		model.addObject("meetingVote", meetingVote);
		return model;
	}
	/**
	 * 
	 * 跳转到投票结果页.
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
	@RequestMapping(value="/showVoteResult", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO showVoteResult(@Param(value="id")String id,@Param(value="meetId")String meetId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		List<MeetingVoteResult> voteResultList = new ArrayList<MeetingVoteResult>();
		MeetingVote meetingVote = new MeetingVote();
		meetingVote.setQuestionCode(id);
		meetingVote.setCode(meetId);
		try{
			if(StringUtils.isNotEmpty(id)){
				voteResultList = meetingVoteService.queryVoteResult(meetingVote);
				meetingVote.setVoteResultList(voteResultList);
				responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
			}else{
				responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		responses.setAttach(meetingVote);
		return responses;
	}

	/**
	 * 
	 * 新增投票.
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
	@RequestMapping(value="/addVote", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addVote(@Param(value="contentStr")String contentStr, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(contentStr)){
				MeetingVote meetingVote = JSON.parseObject(contentStr, MeetingVote.class);
				if(meetingVote != null){
					int record = meetingVoteService.addVote(meetingVote);
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
	 * 删除投票.
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
	@RequestMapping(value="/delVote", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO deloinPer(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				Long VoteId = Long.parseLong(id);
				MeetingVote meetingVote = new MeetingVote();
				meetingVote.setId(VoteId);
				int record = meetingVoteService.delVote(meetingVote);
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
	 * 开启投票.
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
	@RequestMapping(value="/openVote", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO openVote(@Param(value="id")String id,@Param(value="meetId")String meetId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				MeetingVote meetingVote = new MeetingVote();
				meetingVote.setQuestionCode(id);
				meetingVote.setCode(meetId);
				meetingVote.setStatus("1");
				int record = meetingVoteService.updateVote(meetingVote);
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
	 * 关闭投票.
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
	@RequestMapping(value="/closeVote", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO closeVote(@Param(value="id")String id,@Param(value="meetId")String meetId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				MeetingVote meetingVote = new MeetingVote();
				meetingVote.setQuestionCode(id);
				meetingVote.setCode(meetId);
				meetingVote.setStatus("0");
				int record = meetingVoteService.updateVote(meetingVote);
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
	 * 清空本轮投票结果.
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
	@RequestMapping(value="/clearVote", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO clearVote(@Param(value="id")String id,@Param(value="meetId")String meetId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				MeetingVote meetingVote = new MeetingVote();
				meetingVote.setQuestionCode(id);
				meetingVote.setCode(meetId);
				meetingVote.setStatus("0");
				int record = meetingVoteService.clearVote(meetingVote);
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
	 * 查询投票题目状态.
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
	@RequestMapping(value="/queryVoteStatus", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO queryVoteStatus(@Param(value="id")String id,@Param(value="meetId")String meetId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				MeetingVote meetingVote = new MeetingVote();
				meetingVote.setQuestionCode(id);
				meetingVote.setCode(meetId);
				String status = meetingVoteService.queryVoteStatus(meetingVote);
				if(status !=null){
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					responses.setAttach(status);
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
	 * 查询投票题目状态.
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
	@RequestMapping(value="/queryPerVoteStatus", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO queryPerVoteStatus(@Param(value="id")String id,@Param(value="meetId")String meetId, @Param(value="perId")String perId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				MeetingVote meetingVote = new MeetingVote();
				meetingVote.setQuestionCode(id);
				meetingVote.setCode(meetId);
				meetingVote.setPerId(perId);
				String starusV = meetingVoteService.queryPerVoteStatus(meetingVote);
				if(starusV !=null){
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					responses.setAttach(starusV);
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
