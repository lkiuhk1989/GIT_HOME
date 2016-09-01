package com.gome.manager.controler;

import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONArray;
import com.gome.manager.common.util.ResponsesDTO;
import com.gome.manager.constants.ReturnCode;
import com.gome.manager.controler.base.BaseController;
import com.gome.manager.domain.Answer;
import com.gome.manager.domain.ObjectBean;
import com.gome.manager.domain.Question;
import com.gome.manager.domain.Replay;
import com.gome.manager.service.MeetingSelectService;

@Controller
@RequestMapping(value = "/meetingSelect")
public class MeetingSelectController extends BaseController{

	@Resource
	private MeetingSelectService meetingSelectService;


	/**
	 * 
	 * 查询调查问卷内容.
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
	@RequestMapping(value="/querySelectResult", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO querySelectResult(@Param(value="meetId")String meetId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		ObjectBean objectBean = new ObjectBean();
		try{
			if(StringUtils.isNotEmpty(meetId)){
				//查询调查名字和描述
				objectBean = meetingSelectService.queryObjectBean(meetId);
				Question question = new Question();
				question.setOid(objectBean.getOid());
				List<Question> questionList = meetingSelectService.queryQuestion(question);
				objectBean.setQuestionList(questionList);
				responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				responses.setAttach(objectBean);
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
	 * 新增问卷结果.
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
	@RequestMapping(value="/addSelect", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addSelect(@Param(value="contentArr")String contentArr, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(contentArr)){
				List<Answer> parseArray = JSONArray.parseArray(contentArr,Answer.class);
				List<Answer> list=new ArrayList<Answer>(parseArray);
				if(list != null && list.size()>0){
					meetingSelectService.addQuestionSelection(list);
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					Replay replay = new Replay();
					replay.setoId(list.get(0).getOid());
					replay.setReplayCode(String.valueOf(list.get(0).getReplayId()));
					replay.setReplayId(list.get(0).getReplayId());
					replay.setReplayIp("127.0.0.1");
					replay.setReplayTime(new Timestamp(new Date().getTime()));
					meetingSelectService.addReplay(replay);
				}else{
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
	 * 查询用户是否已经参与调查.
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
	@RequestMapping(value="/queryPerSelectResult", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO queryPerSelectResult(@Param(value="replayId")String replayId,@Param(value="oid")String oid, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		Answer answer = new Answer();
		try{
			if(StringUtils.isNotEmpty(oid) && StringUtils.isNotEmpty(replayId)){
				answer.setOid(Integer.parseInt(oid));
				answer.setReplayId(Integer.parseInt(replayId));
				String statusV = meetingSelectService.queryPerSelectResult(answer);
				responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				responses.setAttach(statusV);
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
		ObjectBean objectBean = meetingSelectService.querySelectResult(meetId);
        HSSFWorkbook wb = meetingSelectService.exportExcel(objectBean);
        response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=meet_survey.xls");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
   }    
}
